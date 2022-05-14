package net.mikoto.pixiv.patcher.service.impl;

import com.alibaba.fastjson2.JSON;
import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
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

    /**
     * Patch an artwork.
     *
     * @param artworkId The id of this artwork.
     * @return An artwork object.
     */
    @Override
    public Artwork patchArtwork(int artworkId, @NotNull ForwardConnector forwardConnector, Properties properties) throws GetArtworkInformationException, IOException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, WrongSignException, NoSuchMethodException, IllegalAccessException, GetImageException, InvocationTargetException {
        Artwork artwork = forwardConnector.getArtworkInformation(artworkId);

        if (artwork != null && properties.getProperty(IS_SAVE_ARTWORK).equals(TRUE)) {
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
        }
        return artwork;
    }
}