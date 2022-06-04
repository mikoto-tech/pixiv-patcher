package net.mikoto.pixiv.patcher.manager;

import net.mikoto.pixiv.patcher.Patcher;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;

import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * @author mikoto
 * @date 2022/5/8 3:08
 */
public interface PatcherManager {
    /**
     * Save the patcher.
     *
     * @param patcherName The patcher name.
     * @param patcher     The patcher.
     */
    void savePatcher(String patcherName, Patcher patcher);

    /**
     * Get the patcher
     *
     * @param patcherName The patcher name.
     * @return A patcher object.
     */
    Patcher getPatcher(String patcherName);

    /**
     * Start the patcher.
     *
     * @param patcherName The patcher name.
     */
    void startPatcher(String patcherName) throws AlreadyStartedException;

    /**
     * Stop the patcher.
     *
     * @param patcherName The patcher name.
     */
    void stopPatcher(String patcherName);

    /**
     * Start all the patcher.
     */
    void startAll() throws AlreadyStartedException;

    /**
     * Stop all the patcher.
     */
    void stopAll();

    /**
     * Get the thread pool
     *
     * @return The thread pool.
     */
    ExecutorService getThreadPool();

    /**
     * Get all the patcher name.
     *
     * @return The patcher name.
     */
    Set<String> getAllPatcherName();
}
