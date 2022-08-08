package net.mikoto.pixiv.patcher.service;

import net.mikoto.pixiv.core.connector.ArtworkConnector;
import net.mikoto.pixiv.core.connector.Connector;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.patcher.model.Cache;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author mikoto
 */
public interface ArtworkPatcherService extends PatcherService<Artwork> {
    /**
     * Source the data.
     *
     * @param sourceId  The source id.
     * @param connector The connector.
     * @return The source.
     */
    @Override
    default Artwork source(int sourceId, Connector connector) {
        if (connector instanceof ArtworkConnector) {
            return ((ArtworkConnector) connector).getArtworkById(sourceId);
        } else {
            throw new RuntimeException("Unknown connector type");
        }
    }

    @Override
    void store(Artwork source, Cache<Artwork> cache, String token, Connector connector) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
