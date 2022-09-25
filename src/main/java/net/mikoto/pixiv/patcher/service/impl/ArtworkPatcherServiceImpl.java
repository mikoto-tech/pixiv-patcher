package net.mikoto.pixiv.patcher.service.impl;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.core.connector.ArtworkConnector;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.patcher.model.Cache;
import net.mikoto.pixiv.patcher.model.ImageType;
import net.mikoto.pixiv.patcher.model.PatcherConfig;
import net.mikoto.pixiv.patcher.service.ArtworkPatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static net.mikoto.pixiv.core.util.FileUtil.createDir;
import static net.mikoto.pixiv.core.util.FileUtil.createFile;

/**
 * @author mikoto
 * @date 2022/4/4 3:24
 */
@Service("artworkPatcherService")
public class ArtworkPatcherServiceImpl implements ArtworkPatcherService {
    private final PatcherConfig patcherConfig;

    @Autowired
    public ArtworkPatcherServiceImpl(PatcherConfig patcherConfig) {
        this.patcherConfig = patcherConfig;
    }

    /**
     * Store the data.
     *
     * @param source    The source.
     * @param cache     The cache the artwork need to store.
     * @param connector The connector in order to get image.
     */
    @Override
    public void store(Artwork source, Cache<Artwork> cache, ArtworkConnector connector) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (source != null) {
            if (cache != null) {
                cache.addTarget(source);
            } else {
                String artworkPath = patcherConfig.getLocal().getPath() + source.getAuthorName() + "-" + source.getArtworkTitle() + "/";
                createDir(artworkPath);
                createFile(new File(artworkPath + "artwork.json"), JSONObject.toJSONString(source));
                if (patcherConfig.getLocal().isSaveImage()) {
                    for (ImageType imageType : patcherConfig.getLocal().getImageTypes()) {
                        int page = source.getPageCount();
                        if (imageType == ImageType.mini || imageType == ImageType.thumb) {
                            page = 1;
                        }

                        for (int i = 0; i < page; i++) {
                            File file = new File(artworkPath + source.getArtworkId() + "_p" + i + "_" + imageType + ".jpg");
                            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                            char[] resultTypeChars = imageType.toString().toCharArray();
                            resultTypeChars[0] -= 32;

                            String imageUrl = (String) Artwork.class.getMethod("getIllustUrl" + String.valueOf(resultTypeChars)).invoke(source);
                            imageUrl = imageUrl.replace(source.getArtworkId() + "_p0", source.getArtworkId() + "_p" + i);
                            fileOutputStream2.write(connector.getImage(imageUrl));
                            fileOutputStream2.close();
                        }
                    }
                }
            }
        }
    }
}
