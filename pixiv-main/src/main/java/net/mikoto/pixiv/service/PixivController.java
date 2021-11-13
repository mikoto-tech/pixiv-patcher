package net.mikoto.pixiv.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.Main;

import java.io.IOException;

import static net.mikoto.pixiv.util.HttpUtil.httpGet;

/**
 * @author mikoto
 * @date 2021/11/7 13:01
 */
public class PixivController {
    private static final PixivController INSTANCE = new PixivController();
    private final String key = null;

    public static PixivController getInstance() {
        return INSTANCE;
    }

    public void registerClient() {
        JSONObject result;
        try {
            result = JSON.parseObject(httpGet("http://127.0.0.1:2464/RegisterClient?callbackIp=127.0.0.1&callbackPort=" + Main.HTTP_API_PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getKey() {
        return key;
    }
}
