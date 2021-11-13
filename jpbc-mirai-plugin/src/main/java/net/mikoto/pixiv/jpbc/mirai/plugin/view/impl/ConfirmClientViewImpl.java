package net.mikoto.pixiv.jpbc.mirai.plugin.view.impl;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mikoto
 * @date 2021/11/14 4:14
 */
public class ConfirmClientViewImpl extends AbstractHttpExchangeView {
    /**
     * Get view data.
     *
     * @param objects Object
     * @return A json object.
     */
    @Override
    public JSONObject getData(Object... objects) {
        JSONObject outputJson = new JSONObject();

        if (objects[0] instanceof Boolean && objects[1] instanceof String) {
            // 信息处理
            if (objects[0] == Boolean.TRUE) {
                outputJson.put("error", false);
            } else {
                outputJson.put("error", true);
            }
            outputJson.put("msg", objects[1]);
            JSONObject jpbcInfoBody = new JSONObject();

            outputJson.put("body", jpbcInfoBody);
            outputJson.put("systemTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

        return outputJson;
    }
}
