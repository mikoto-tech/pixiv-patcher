package net.mikoto.pixiv.patcher;

import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import net.mikoto.pixiv.patcher.service.impl.ArtworkServiceImpl;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

import static net.mikoto.pixiv.patcher.constant.Constant.*;
import static net.mikoto.pixiv.patcher.util.FileUtil.createDir;
import static net.mikoto.pixiv.patcher.util.FileUtil.createFile;


@SpringBootTest
class PixivPatcherApplicationTests {

    @Test
    void forwardServerTest() throws NoSuchMethodException, IOException, GetImageException, GetArtworkInformationException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, WrongSignException, IllegalAccessException {
        createDir("config");
        createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivPatcherApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
        MAIN_PROPERTIES.load(new FileReader("config/config.properties"));

        if (MAIN_PROPERTIES.getProperty(IS_SAVE_ARTWORK).equals(TRUE)) {
            createDir(MAIN_PROPERTIES.getProperty(LOCAL_PATH));
        }

        if (MAIN_PROPERTIES.getProperty(SAVE_ARTWORK_TYPE).equals(ALL)) {
            MAIN_PROPERTIES.setProperty(SAVE_ARTWORK_TYPE, "small,original,mini,thumb,regular");
        }

        ArtworkService patcherService = new ArtworkServiceImpl();

        patcherService.patchArtwork(97478425);
    }

}
