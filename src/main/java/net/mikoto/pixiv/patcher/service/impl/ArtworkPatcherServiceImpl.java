package net.mikoto.pixiv.patcher.service.impl;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.core.connector.Connector;
import net.mikoto.pixiv.core.connector.DirectConnector;
import net.mikoto.pixiv.core.connector.ForwardConnector;
import net.mikoto.pixiv.core.model.Artwork;
import net.mikoto.pixiv.patcher.model.Cache;
import net.mikoto.pixiv.patcher.service.ArtworkPatcherService;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${mikoto.pixiv.patcher.local.path}")
    private String localPath;
    @Value("#{'${mikoto.pixiv.patcher.local.imageType}'.split(',')}")
    private String[] imageTypes;
    @Value("${mikoto.pixiv.patcher.local.isSaveImage}")
    private boolean isSaveImage;
    @Value("#{'${mikoto.pixiv.patcher.usingStorage}'.split(',')}")
    private String[] usingStores;

    /**
     * Store the data.
     *
     * @param source    The source.
     * @param cache     The cache the artwork need to store.
     * @param token     The token of the database
     * @param connector The connector in order to get image.
     */
    @Override
    public void store(Artwork source, Cache<Artwork> cache, String token, Connector connector) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (cache != null) {
            cache.addTarget(source);
        } else {
            String artworkPath = localPath + source.getAuthorName() + "-" + source.getArtworkTitle() + "/";
            createDir(artworkPath);
            createFile(new File(artworkPath + "artwork.json"), JSONObject.toJSONString(source));
            if (isSaveImage) {
                for (String imageType : imageTypes) {
                    int page = source.getPageCount();
                    if ("mini".equals(imageType) || "thumb".equals(imageType)) {
                        page = 1;
                    }

                    for (int i = 0; i < page; i++) {
                        File file = new File(artworkPath + source.getArtworkId() + "_p" + i + "_" + imageType + ".jpg");
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        char[] resultTypeChars = imageType.toCharArray();
                        resultTypeChars[0] -= 32;

                        String imageUrl = (String) Artwork.class.getMethod("getIllustUrl" + String.valueOf(resultTypeChars)).invoke(source);
                        imageUrl = imageUrl.replace(source.getArtworkId() + "_p0", source.getArtworkId() + "_p" + i);
                        if (connector instanceof ForwardConnector) {
                            fileOutputStream2.write(((ForwardConnector) connector).getImage(imageUrl));
                        } else if (connector instanceof DirectConnector) {
                            fileOutputStream2.write(((DirectConnector) connector).getImage(imageUrl));
                        } else {
                            throw new RuntimeException("Unknown connector type");
                        }
                        fileOutputStream2.close();
                    }
                }
            }
        }
    }
}
