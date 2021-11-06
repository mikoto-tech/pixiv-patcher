package net.mikoto.pixiv.pixivforward;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mikoto
 */
@SpringBootApplication
public class PixivForwardApplication {
    // Springboot 项目主类

    public static String USER_PASSWORD;
    public static String USER_NAME;
    public static String URL;

    public static void main(String[] args) {
        SpringApplication.run(PixivForwardApplication.class, args);

        // 初始化数据
        USER_PASSWORD = args[3];
        URL = "jdbc:mysql://" + args[0] + ":" + args[1] +
                "/pixiv_data" +
                "?useSSL" +
                "=false" +
                "&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        USER_NAME = args[2];
    }
}
