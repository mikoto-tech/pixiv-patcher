package net.mikoto.pixiv.patcher.service.impl;

import com.alibaba.fastjson2.JSON;
import net.mikoto.pixiv.api.model.Artwork;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.patcher.model.ArtworkCache;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import static net.mikoto.pixiv.api.util.FileUtil.createDir;
import static net.mikoto.pixiv.patcher.manager.ConfigManager.*;

/**
 * @author mikoto
 * @date 2022/4/4 3:24
 */
@Service("artworkService")
public class ArtworkServiceImpl implements ArtworkService {
    private static final String MINI = "mini";
    private static final String THUMB = "thumb";
    private static final String LOCAL = "local";
    private static final String DATABASE = "database";

    /**
     * Patch an artwork.
     *
     * @param artworkId    The id of this artwork.
     * @param artworkCache The artwork cache.
     * @return An artwork object.
     */
    @Override
    public Artwork patchArtwork(int artworkId, ArtworkCache artworkCache, @NotNull ForwardConnector forwardConnector, Properties properties) throws Exception {
        Artwork artwork = forwardConnector.getArtworkById(artworkId);

        if (artwork != null && properties.getProperty(IS_SAVE_ARTWORK).equals(TRUE)) {
            if (properties.getProperty(SAVE_TO).contains(LOCAL)) {
                String dir = properties.getProperty(LOCAL_PATH) + artwork.getAuthorId() + " - " + artwork.getArtworkTitle() + " - " + artwork.getArtworkId();
                createDir(dir);
                FileOutputStream fileOutputStream1 = new FileOutputStream(dir + "/artworkData.json");
                fileOutputStream1.write(JSON.toJSONString(artwork).getBytes(StandardCharsets.UTF_8));
                fileOutputStream1.close();

                String[] modes = properties.getProperty(SAVE_ARTWORK_TYPE).split(",");

                for (String mode :
                        modes) {
                    int times = artwork.getPageCount();
                    if (mode.equals(MINI) || mode.equals(THUMB)) {
                        times = 1;
                    }
                    for (int i = 0; i < times; i++) {
                        File file = new File(dir + "/" + artwork.getArtworkId() + "_p" + i + "_" + mode + ".jpg");
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        char[] resultModeChars = mode.toCharArray();
                        resultModeChars[0] -= 32;
                        String imageUrl = (String) Artwork.class.getMethod("getIllustUrl" + String.valueOf(resultModeChars)).invoke(artwork);
                        imageUrl = imageUrl.replace(artwork.getArtworkId() + "_p0", artwork.getArtworkId() + "_p" + i);
                        fileOutputStream2.write(forwardConnector.getImage(imageUrl));
                        fileOutputStream2.close();
                    }
                }
            } else if (properties.getProperty(SAVE_TO).contains(DATABASE)) {
                artworkCache.addArtwork(artwork);
            }
        }
        return artwork;
    }
}
