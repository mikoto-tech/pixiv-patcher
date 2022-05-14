package net.mikoto.pixiv.patcher.manager.impl;

import net.mikoto.pixiv.patcher.manager.ConfigManager;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static net.mikoto.pixiv.api.util.FileUtil.*;
import static net.mikoto.pixiv.patcher.constant.Constants.DEFAULT_PROPERTIES;

/**
 * @author mikoto
 * @date 2022/5/8 3:07
 */
@Component("configManager")
public class ConfigManagerImpl implements ConfigManager {
    private static final Map<String, Properties> PROPERTIES_MAP = new ConcurrentHashMap<>();

    public ConfigManagerImpl() throws IOException {
        addConfig(DEFAULT_CONFIG, DEFAULT_PROPERTIES);

        String patcherConfigs = getConfig(DEFAULT_CONFIG).getProperty(PATCHER_CONFIGS);
        if (patcherConfigs != null) {
            for (String patcherConfigName :
                    patcherConfigs.split(",")) {
                createDir("config/patcher/");
                createFile(new File("config/patcher/" + patcherConfigName + ".properties"), IOUtils.toString(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("template_patcher_config.properties")), StandardCharsets.UTF_8));
                Properties properties = new Properties();
                properties.load(new FileReader("config/patcher/" + patcherConfigName + ".properties"));
                addConfig(patcherConfigName, properties);
            }
        }
    }

    /**
     * Save the config.
     *
     * @param configName The config name.
     * @param properties The properties.
     */
    @Override
    public void addConfig(String configName, @NotNull Properties properties) {
        if (properties.getProperty(IS_SAVE_ARTWORK) != null && properties.getProperty(IS_SAVE_ARTWORK).equals(TRUE)) {
            createDir(properties.getProperty(LOCAL_PATH));
        }

        if (properties.getProperty(SAVE_ARTWORK_TYPE) != null && properties.getProperty(SAVE_ARTWORK_TYPE).equals(ALL)) {
            properties.setProperty(SAVE_ARTWORK_TYPE, "small,original,mini,thumb,regular");
        }

        PROPERTIES_MAP.put(configName, properties);
    }

    /**
     * Save the config.
     *
     * @param configName The config name.
     */
    @Override
    public void saveConfig(String configName) throws IOException {
        Properties properties = getConfig(configName);
        if (properties != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<Object, Object> entry :
                    properties.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
            }

            String path;

            if (configName.equals(DEFAULT_CONFIG)) {
                path = "config/default_config.properties";
            } else {
                path = "config/patcher/" + configName + ".properties";
            }

            writeFile(new File(path), stringBuilder.toString());
        }
    }

    /**
     * Save all the configs.
     */
    @Override
    public void saveAllConfigs() throws IOException {
        for (String configName :
                getAllConfigNames()) {
            saveConfig(configName);
        }
    }

    /**
     * Get the config by config name.
     *
     * @param configName The config name.
     * @return The config.
     */
    @Override
    public Properties getConfig(String configName) {
        return PROPERTIES_MAP.get(configName);
    }

    /**
     * Get all the configs.
     *
     * @return The configs.
     */
    @Override
    public Collection<Properties> getAllConfigs() {
        return PROPERTIES_MAP.values();
    }

    /**
     * Get all the config names.
     *
     * @return The config names.
     */
    @Override
    public Set<String> getAllConfigNames() {
        return PROPERTIES_MAP.keySet();
    }
}
