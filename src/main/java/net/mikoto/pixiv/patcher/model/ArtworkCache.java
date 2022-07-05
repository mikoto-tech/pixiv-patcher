package net.mikoto.pixiv.patcher.model;

import net.mikoto.pixiv.core.model.Artwork;

public class ArtworkCache extends Cache<Artwork> {
    public ArtworkCache(int maxTargetCount) {
        super(maxTargetCount);
    }
}