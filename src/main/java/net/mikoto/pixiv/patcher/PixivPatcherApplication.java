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
import static net.mikoto.pixiv.patcher.constant.Constants.DEFAULT_PROPERTIES;

/**
 * @author mikoto
 */
@SpringBootApplication
public class PixivPatcherApplication {
    public static void main(String[] args) throws IOException {
        createDir("config");
        createFile(new File("config/default_config.properties"), IOUtils.toString(Objects.requireNonNull(PixivPatcherApplication.class.getClassLoader().getResourceAsStream("default_config.properties")), StandardCharsets.UTF_8));
        DEFAULT_PROPERTIES.load(new FileReader("config/default_config.properties"));

        SpringApplication.run(PixivPatcherApplication.class, args);
    }

}
