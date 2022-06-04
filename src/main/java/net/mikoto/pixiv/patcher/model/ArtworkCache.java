package net.mikoto.pixiv.patcher.model;

import net.mikoto.pixiv.api.model.Artwork;

import java.util.HashSet;
import java.util.Set;

/**
 * @author mikoto
 * @date 2022/5/28 17:20
 */
public class ArtworkCache {
    private final int maxArtworkCount;
    private final Set<Artwork> artworks = new HashSet<>();

    public ArtworkCache(int maxArtworkCount) {
        this.maxArtworkCount = maxArtworkCount;
    }

    public boolean isFull() {
        return artworks.size() >= maxArtworkCount;
    }

    public void addArtwork(Artwork artwork) {
        artworks.add(artwork);
    }

    public Artwork[] getArtworks() {
        return artworks.toArray(new Artwork[0]);
    }

    public void removeAll() {
        artworks.clear();
    }

    public boolean isEmpty() {
        return artworks.isEmpty();
    }
}
