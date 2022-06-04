package net.mikoto.pixiv.patcher.service;

import net.mikoto.pixiv.api.model.Artwork;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.patcher.model.ArtworkCache;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author mikoto
 * @date 2022/4/4 3:22
 */
public interface ArtworkService {
    /**
     * Patch an artwork.
     *
     * @param artworkId        The id of this artwork.
     * @param artworkCache     The artwork cache object.
     * @param forwardConnector The forward connector.
     * @return An artwork object.
     * @throws GetArtworkInformationException An exception.
     * @throws IOException                    An exception.
     * @throws InvalidKeyException            An exception.
     * @throws GetImageException              An exception.
     * @throws IllegalAccessException         An exception.
     * @throws InvalidKeyException            An exception.
     * @throws InvalidKeySpecException        An exception.
     * @throws NoSuchAlgorithmException       An exception.
     * @throws NoSuchMethodException          An exception.
     * @throws SignatureException             An exception.
     */
    Artwork patchArtwork(int artworkId, ArtworkCache artworkCache, ForwardConnector forwardConnector) throws Exception;
}
