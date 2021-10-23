package net.mikoto.pixiv.pixivforward;

import net.mikoto.pixiv.pixivforward.util.UserData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mikoto
 */
@SpringBootApplication
public class PixivForwardApplication {
    // Springboot 项目主类

    public static void main(String[] args) {
        SpringApplication.run(PixivForwardApplication.class, args);

        // 初始化数据
        UserData.setUserPassword(args[3]);
        UserData.setUrl("jdbc:mysql://" + args[0] + ":" + args[1] +
                "/pixiv_data" +
                "?useSSL" +
                "=false" +
                "&allowPublicKeyRetrieval=true&serverTimezone=UTC");
        UserData.setUserName(args[2]);

        try {
            //加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
