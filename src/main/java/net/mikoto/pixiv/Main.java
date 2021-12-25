package net.mikoto.pixiv;

import net.mikoto.httpserver.Httpserver;
import net.mikoto.pixiv.controller.CrawlCountControllerObserver;
import net.mikoto.pixiv.crawler.MainCrawler;
import net.mikoto.pixiv.crawler.WorkerManager;
import net.mikoto.pixiv.dao.PixivDataDao;
import net.mikoto.pixiv.log.Log;
import net.mikoto.pixiv.log.impl.ConsoleTimeFormatLog;
import net.mikoto.pixiv.service.PixivDataService;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.Objects;
import java.util.Properties;

/**
 * @author mikoto
 * @date 2021/11/7 12:41
 */
public class Main {
    /**
     * 常量
     */
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "Pixiv main project.";
    public static final String AUTHOR = "mikoto";
    public static final String PACKAGE = "net.mikoto.pixiv";
    public static final Integer HTTP_API_PORT = 2465;
    public static final Properties PROPERTIES = new Properties();
    private static final Log LOGGER = new ConsoleTimeFormatLog();


    public static void main(String[] args) {
        // 配置config
        InputStream in = null;
        try {
            in = new FileInputStream("config.properties");
        } catch (FileNotFoundException e) {
            File file = new File("config.properties");
            try {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(IOUtils.toString(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("config.properties"))));
                fileWriter.close();
                in = new FileInputStream("config.properties");
            } catch (IOException ignored) {
            }
        }
        try {
            PROPERTIES.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 创建crawler
        CrawlCountControllerObserver crawlCountControllerObserver = new CrawlCountControllerObserver();

        // 启动http服务器
        Httpserver.setPort(HTTP_API_PORT)
                .createContext("/crawlCount", crawlCountControllerObserver)
                .start();
        LOGGER.log("Httpserver service start on localhost:" + HTTP_API_PORT + " successful");

        // 配置Dao
        PixivDataDao.getInstance().setLogger(LOGGER);
        LOGGER.log("Database connectivity load successful");

        // 配置Log
        PixivDataService.getInstance().setLogger(LOGGER);
        MainCrawler.getInstance().setLogger(LOGGER);
        WorkerManager.getInstance().setLogger(LOGGER);
        WorkerManager.getInstance().setObserver(crawlCountControllerObserver);
        LOGGER.log("Worker manager load successful");

        // 配置Crawler
        MainCrawler.getInstance().startCrawler(WorkerManager.getInstance().initWorker());
        LOGGER.log("START CRAWLER");
    }
}
