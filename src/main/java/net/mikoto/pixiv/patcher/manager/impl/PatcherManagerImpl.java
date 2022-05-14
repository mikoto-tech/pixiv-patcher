package net.mikoto.pixiv.patcher.manager.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;
import net.mikoto.pixiv.patcher.manager.ConfigManager;
import net.mikoto.pixiv.patcher.manager.PatcherManager;
import net.mikoto.pixiv.patcher.model.Patcher;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author mikoto
 * @date 2022/5/8 3:09
 */
@Component("patcherManager")
public class PatcherManagerImpl implements PatcherManager {
    private static final Map<String, Patcher> PATCHER_MAP = new ConcurrentHashMap<>();
    private final ExecutorService EXECUTOR_SERVICE;

    public PatcherManagerImpl(@NotNull ConfigManager configManager) {

        Properties properties = configManager.getConfig(ConfigManager.DEFAULT_CONFIG);

        EXECUTOR_SERVICE = new ThreadPoolExecutor(
                Integer.parseInt(properties.getProperty(ConfigManager.CORE_POOL_SIZE)),
                Integer.parseInt(properties.getProperty(ConfigManager.MAXIMUM_POOL_SIZE)),
                Long.parseLong(properties.getProperty(ConfigManager.KEEP_ALIVE_TIME)),
                TimeUnit.valueOf(properties.getProperty(ConfigManager.TIMEUNIT)),
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat(properties.getProperty(ConfigManager.NAME_FORMAT)).build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    /**
     * Save the patcher.
     *
     * @param patcherName The patcher name.
     * @param patcher     The patcher.
     */
    @Override
    public void savePatcher(String patcherName, @NotNull Patcher patcher) {
        patcher.setExecutorService(EXECUTOR_SERVICE);
        PATCHER_MAP.put(patcherName, patcher);
    }

    /**
     * Get the patcher
     *
     * @param patcherName The patcher name.
     * @return A patcher object.
     */
    @Override
    public Patcher getPatcher(String patcherName) {
        return PATCHER_MAP.get(patcherName);
    }

    /**
     * Start the patcher.
     *
     * @param patcherName The patcher name.
     */
    @Override
    public void startPatcher(String patcherName) throws AlreadyStartedException {
        PATCHER_MAP.get(patcherName).start();
    }

    /**
     * Stop the patcher.
     *
     * @param patcherName The patcher name.
     */
    @Override
    public void stopPatcher(String patcherName) {
        PATCHER_MAP.get(patcherName).stop();
    }

    /**
     * Start all the patcher.
     */
    @Override
    public void startAll() throws AlreadyStartedException {
        for (Patcher patcher :
                PATCHER_MAP.values()) {
            patcher.start();
        }
    }

    /**
     * Stop all the patcher.
     */
    @Override
    public void stopAll() {
        for (Patcher patcher :
                PATCHER_MAP.values()) {
            patcher.stop();
        }
    }

    /**
     * Get the thread pool
     *
     * @return The thread pool.
     */
    @Override
    public ExecutorService getThreadPool() {
        return EXECUTOR_SERVICE;
    }

    /**
     * Get all the patcher name.
     *
     * @return The patcher name.
     */
    @Override
    public Set<String> getAllPatcherName() {
        return PATCHER_MAP.keySet();
    }
}
