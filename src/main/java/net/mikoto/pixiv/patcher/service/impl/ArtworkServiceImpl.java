package net.mikoto.pixiv.patcher.service.impl;

import com.alibaba.fastjson2.JSON;
import net.mikoto.pixiv.api.model.Artwork;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.patcher.model.ArtworkCache;
import net.mikoto.pixiv.patcher.model.Source;
import net.mikoto.pixiv.patcher.model.Storage;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static net.mikoto.pixiv.api.util.FileUtil.createDir;

/**
 * @author mikoto
 * @date 2022/4/4 3:24
 */
@Service("artworkService")
public class ArtworkServiceImpl implements ArtworkService {
    private static final String MINI = "mini";
    private static final String THUMB = "thumb";
    private static final String DATABASE = "database";
    @Value("#{'${mikoto.pixiv.patcher.usingStorage}'.split(',')}")
    private List<Storage> storage;
    @Value("${mikoto.pixiv.patcher.usingSource}")
    private Source source;
    @Value("${mikoto.pixiv.patcher.local.path}")
    private String localPath;
    @Value("#{'${mikoto.pixiv.patcher.local.imageType}'.split(',')}")
    private String[] imageType;

    /**
     * Patch an artwork.
     *
     * @param artworkId    The id of this artwork.
     * @param artworkCache The artwork cache.
     * @return An artwork object.
     */
    @Override
    public Artwork patchArtwork(int artworkId, ArtworkCache artworkCache, @NotNull ForwardConnector forwardConnector) throws GetArtworkInformationException, IOException, NoSuchMethodException, GetImageException, InvocationTargetException, IllegalAccessException {
        Artwork artwork = forwardConnector.getArtworkInformation(artworkId);

        if (artwork != null) {
            if (storage.contains(Storage.local)) {
                String dir = localPath + artwork.getAuthorId() + " - " + artwork.getArtworkTitle() + " - " + artwork.getArtworkId();
                createDir(dir);
                FileOutputStream fileOutputStream1 = new FileOutputStream(dir + "/artworkData.json");
                fileOutputStream1.write(JSON.toJSONString(artwork).getBytes(StandardCharsets.UTF_8));
                fileOutputStream1.close();

                for (String imageType :
                        imageType) {
                    int times = artwork.getPageCount();
                    if (imageType.equals(MINI) || imageType.equals(THUMB)) {
                        times = 1;
                    }
                    for (int i = 0; i < times; i++) {
                        File file = new File(dir + "/" + artwork.getArtworkId() + "_p" + i + "_" + imageType + ".jpg");
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        char[] resultTypeChars = imageType.toCharArray();
                        resultTypeChars[0] -= 32;
                        String imageUrl = (String) Artwork.class.getMethod("getIllustUrl" + String.valueOf(resultTypeChars)).invoke(artwork);
                        imageUrl = imageUrl.replace(artwork.getArtworkId() + "_p0", artwork.getArtworkId() + "_p" + i);
                        fileOutputStream2.write(forwardConnector.getImage(imageUrl));
                        fileOutputStream2.close();
                    }
                }
            } else if (storage.contains(Storage.database)) {
                artworkCache.addArtwork(artwork);
            }
        }
        return artwork;
    }
}
