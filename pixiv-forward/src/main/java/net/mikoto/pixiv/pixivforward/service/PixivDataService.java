package net.mikoto.pixiv.pixivforward.service;

import net.mikoto.pixiv.pixivforward.model.PixivData;

import java.io.IOException;

/**
 * @author mikoto
 * Created at 2:45:13, 2021/10/3
 * Project: pixiv-forward
 */
public interface PixivDataService {
    /**
     * Get a pixiv data by the artwork id.
     *
     * @param artworkId The id of this artwork.
     * @return An artwork object.
     * @throws IOException IOException.
     */
    PixivData getPixivDataById(int artworkId) throws IOException;
}
