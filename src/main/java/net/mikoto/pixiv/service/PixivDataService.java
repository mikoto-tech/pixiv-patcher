package net.mikoto.pixiv.service;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.Main;
import net.mikoto.pixiv.log.Log;
import net.mikoto.pixiv.pojo.PixivData;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static net.mikoto.pixiv.util.HttpUtil.httpGet;

/**
 * @author mikoto
 * @date 2021/12/11 22:26
 */
public class PixivDataService {
    /**
     * 单例
     */
    private static final PixivDataService INSTANCE = new PixivDataService();

    private static final String ERROR_CODE = "error";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final ArrayList<String> pixivDataForwardServer = new ArrayList<>();
    private final String KEY;
    private Log logger;
    private Integer lastServer = 0;

    /**
     * 构建方法
     */
    private PixivDataService() {
        KEY = Main.PROPERTIES.getProperty("KEY");
        pixivDataForwardServer.addAll(List.of(Main.PROPERTIES.getProperty("DATA_FORWARD_SERVER").split(";")));
    }

    /**
     * 获取单例
     *
     * @return 当前实例
     */
    public static PixivDataService getInstance() {
        return INSTANCE;
    }

    /**
     * 设置logger
     *
     * @param logger logger对象
     */
    public void setLogger(Log logger) {
        this.logger = logger;
    }

    /**
     * Crawl pixiv data by id
     *
     * @param server    Pixiv forward server
     * @param artworkId Artwork id
     * @return pixiv data
     */
    public PixivData crawlPixivDataById(String server, int artworkId) {
        PixivData pixivData = null;
        try {
            JSONObject rawJson = JSONObject.parseObject(httpGet("https://" + server + "/getArtworkInformation?key=" + KEY + "&artworkId=" + artworkId));
            try {
                if (!rawJson.getBoolean(ERROR_CODE)) {
                    rawJson.put("crawlDate", SIMPLE_DATE_FORMAT.format(new Date()));
                    pixivData = new PixivData();
                    pixivData.loadJson(rawJson);
                } else {
                    return null;
                }
            } catch (JSONException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                logger.log("Failed to get data(id:" + artworkId + ") [Network error]");
                crawlPixivDataById(server, artworkId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pixivData;
    }

    /**
     * Get forward server.
     *
     * @return server address.
     */
    public synchronized String getPixivDataForwardServer() {
        if (lastServer >= pixivDataForwardServer.size()) {
            lastServer = 0;
        }
        String server = pixivDataForwardServer.get(lastServer);
        lastServer++;
        return server;
    }
}
