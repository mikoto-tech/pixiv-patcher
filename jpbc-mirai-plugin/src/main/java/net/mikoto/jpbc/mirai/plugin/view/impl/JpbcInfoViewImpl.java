package net.mikoto.jpbc.mirai.plugin.view.impl;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mikoto
 * @date 2021/10/16 23:48
 */
public class JpbcInfoViewImpl extends AbstractHttpExchangeView {
    /**
     * Get jpbc information.
     *
     * @return Jpbc information json object.
     */
    @Override
    public JSONObject getData(Object... objects) {
        // 初始化输出Json
        JSONObject outputJson = new JSONObject();

        if (objects[0] instanceof String && objects[1] instanceof String && objects[2] instanceof String && objects[3] instanceof String && objects[4] instanceof Integer) {
            // 信息处理
            outputJson.put("error", false);
            outputJson.put("msg", "The java pixiv bot connectivity for mirai is already bound to port " + objects[4]);
            JSONObject jpbcInfoBody = new JSONObject();

            jpbcInfoBody.put("jpbcVersion", objects[0]);
            jpbcInfoBody.put("description", objects[1]);
            jpbcInfoBody.put("author", objects[2]);
            jpbcInfoBody.put("package", objects[3]);
            jpbcInfoBody.put("httpApiPort", objects[4]);

            outputJson.put("body", jpbcInfoBody);
            outputJson.put("systemTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

        return outputJson;
    }
}
