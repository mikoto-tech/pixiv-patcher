package net.mikoto.pixiv.patcher.service;

import net.mikoto.pixiv.core.connector.ArtworkConnector;
import net.mikoto.pixiv.core.connector.Connector;
import net.mikoto.pixiv.core.model.Artwork;

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
}
