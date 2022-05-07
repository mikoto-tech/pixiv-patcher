package net.mikoto.pixiv.patcher;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static net.mikoto.pixiv.api.util.FileUtil.createDir;
import static net.mikoto.pixiv.api.util.FileUtil.createFile;
import static net.mikoto.pixiv.patcher.constant.Constant.*;

/**
 * @author mikoto
 */
@SpringBootApplication
public class PixivPatcherApplication {

    public static void main(String[] args) throws IOException {
        createDir("config");
        createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(PixivPatcherApplication.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
        MAIN_PROPERTIES.load(new FileReader("config/config.properties"));

        if (MAIN_PROPERTIES.getProperty(IS_SAVE_ARTWORK).equals(TRUE)) {
            createDir(MAIN_PROPERTIES.getProperty(LOCAL_PATH));
        }

        if (MAIN_PROPERTIES.getProperty(SAVE_ARTWORK_TYPE).equals(ALL)) {
            MAIN_PROPERTIES.setProperty(SAVE_ARTWORK_TYPE, "small,original,mini,thumb,regular");
        }

        SpringApplication.run(PixivPatcherApplication.class, args);
    }

}
