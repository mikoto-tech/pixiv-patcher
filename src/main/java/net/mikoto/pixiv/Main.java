package net.mikoto.pixiv;

import net.mikoto.log.Logger;
import net.mikoto.pixiv.crawler.CrawlerManager;
import net.mikoto.pixiv.engine.PixivEngine;
import net.mikoto.pixiv.engine.logger.impl.ConsoleTimeFormatLogger;
import net.mikoto.pixiv.engine.pojo.Config;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static net.mikoto.pixiv.util.FileUtil.createDir;
import static net.mikoto.pixiv.util.FileUtil.createFile;

/**
 * @author mikoto
 * @date 2021/11/7 12:41
 */
public class Main {
    /**
     * 常量
     */
    public static final Properties PROPERTIES = new Properties();
    private static final Logger LOGGER = new ConsoleTimeFormatLogger();
    public static PixivEngine PIXIV_ENGINE;


    public static void main(String @NotNull [] args) {
        try {
            // 创建目录
            createDir("config");
            createDir("crawler");

            // 配置文件
            createFile(new File("config/config.properties"), IOUtils.toString(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("config.properties")), StandardCharsets.UTF_8));
            PROPERTIES.load(new FileReader("config/config.properties"));

            // 读取crawler
            Map<String, Properties> crawlers = new HashMap<>(args.length / 2);
            for (int i = 0; i < args.length; i++) {
                if ("-l".equals(args[i])) {
                    i++;
                    Properties crawlerProperties = new Properties();
                    crawlerProperties.load(new FileInputStream("crawler/" + args[i] + ".crawler"));
                    crawlerProperties.put("type", "crawler");
                    crawlers.put(args[i], crawlerProperties);
                } else if ("-c".equals(args[i])) {
                    i++;
                    Properties crawlerProperties = new Properties();
                    crawlerProperties.load(new FileInputStream("config/" + args[i] + ".crawler.properties"));
                    crawlerProperties.put("type", "crawlerProperties");
                    crawlers.put(args[i], crawlerProperties);
                } else if ("-t".equals(args[i])) {
                    createFile(new File("config/template.crawler.properties"), IOUtils.toString(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("template.crawler.properties")), StandardCharsets.UTF_8));
                }
            }

            // 配置config
            Config config = new Config();
            config.setLogger(LOGGER);
            config.setKey(PROPERTIES.getProperty("KEY"));
            config.setUserPassword(PROPERTIES.getProperty("PASSWORD"));
            config.setUserName(PROPERTIES.getProperty("USERNAME"));
            config.setJpbcUrl(PROPERTIES.getProperty("URL"));
            config.setPixivDataForwardServer(new ArrayList<>(Arrays.asList(PROPERTIES.getProperty("DATA_FORWARD_SERVER").split(";"))));

            // 配置PixivEngine
            PIXIV_ENGINE = new PixivEngine(config);
            LOGGER.log("Worker manager load successful");

            // 加载crawler
            CrawlerManager.getInstance().loadCrawlers(crawlers, LOGGER);

            // 启动Crawler
            CrawlerManager.getInstance().startAll();

            while (true) {
                Thread.sleep(5000);
                CrawlerManager.getInstance().saveAll();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
