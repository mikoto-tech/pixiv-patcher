package net.mikoto.pixiv.patcher.service;

import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.api.pojo.ForwardServer;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;

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
     * @param artworkId The id of this artwork.
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
     * @throws WrongSignException             An exception.
     */
    Artwork patchArtwork(int artworkId) throws GetArtworkInformationException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, WrongSignException, NoSuchMethodException, IllegalAccessException, GetImageException;

    /**
     * Add a forward server.
     *
     * @param forwardServer The forward server.
     * @throws IOException           An exception.
     * @throws NoSuchMethodException An exception.
     */
    void addForwardServer(ForwardServer forwardServer) throws IOException, NoSuchMethodException;
}
