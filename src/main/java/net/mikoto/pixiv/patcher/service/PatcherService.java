package net.mikoto.pixiv.patcher.service;

import net.mikoto.pixiv.core.connector.ArtworkConnector;
import net.mikoto.pixiv.core.connector.Connector;
import net.mikoto.pixiv.patcher.model.Cache;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 * @date 2022/4/4 3:22
 */
public interface PatcherService<T> {
    /**
     * Source the data.
     *
     * @param sourceId  The source id.
     * @param connector The connector.
     * @return The source.
     */
    T source(int sourceId, Connector connector);

    /**
     * Store the data.
     *
     * @param source    The source.
     * @param cache     The cache the artwork need to store.
     * @param connector The connector in order to get image.
     */
    void store(T source, Cache<T> cache, ArtworkConnector connector) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * Patch the data(Source and store).
     *
     * @param sourceId  The source id.
     * @param connector The connector.
     * @param cache     The cache the artwork need to store.
     * @return The source.
     */
    default T patch(int sourceId, ArtworkConnector connector, Cache<T> cache) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        T source = source(sourceId, connector);
        store(source, cache, connector);
        return source;
    }
}
