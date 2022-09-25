package net.mikoto.pixiv.patcher;

import com.dtflys.forest.springboot.annotation.ForestScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mikoto
 */
@SpringBootApplication
@ComponentScan("net.mikoto.pixiv")
@EntityScan("net.mikoto.pixiv.core.model")
@ForestScan(basePackages = "net.mikoto.pixiv.core.client")
public class PixivPatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixivPatcherApplication.class, args);
    }

}
