package net.mikoto.pixiv.patcher.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/5/8 3:07
 */
public interface ConfigManager {
    String DEFAULT_CONFIG = "defaultConfig";
    String ALL = "all";
    String TRUE = "true";
    String IS_SAVE_ARTWORK = "IS_SAVE_ARTWORK";
    String LOCAL_PATH = "LOCAL_PATH";
    String SAVE_ARTWORK_TYPE = "SAVE_ARTWORK_TYPE";
    String FORWARD_SERVER = "FORWARD_SERVER";
    String PATCHER_CONFIGS = "PATCHER_CONFIGS";
    String BEGINNING_ARTWORK_ID = "BEGINNING_ARTWORK_ID";
    String TARGET_ARTWORK_ID = "TARGET_ARTWORK_ID";
    String CORE_POOL_SIZE = "CORE_POOL_SIZE";
    String MAXIMUM_POOL_SIZE = "MAXIMUM_POOL_SIZE";
    String NAME_FORMAT = "NAME_FORMAT";
    String KEEP_ALIVE_TIME = "KEEP_ALIVE_TIME";
    String TIMEUNIT = "TIMEUNIT";
    String THREAD_COUNT = "THREAD_COUNT";
    String SAVE_TO = "SAVE_TO";
    String CACHE_SIZE = "CACHE_SIZE";
    String DATABASE_ADDRESS = "DATABASE_ADDRESS";
    String DATABASE_KEY = "DATABASE_KEY";

    /**
     * Add the config.
     *
     * @param configName The config name.
     * @param properties The properties.
     */
    void addConfig(String configName, Properties properties);

    /**
     * Save the config.
     *
     * @param configName The config name.
     */
    void saveConfig(String configName) throws IOException;

    /**
     * Save all the configs.
     */
    void saveAllConfigs() throws IOException;

    /**
     * Get the config by config name.
     *
     * @param configName The config name.
     * @return The config.
     */
    Properties getConfig(String configName);

    /**
     * Get all the configs.
     *
     * @return The configs.
     */
    Collection<Properties> getAllConfigs();

    /**
     * Get all the config names.
     *
     * @return The config names.
     */
    Set<String> getAllConfigNames();
}
