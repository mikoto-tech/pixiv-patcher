package net.mikoto.pixiv.view.impl;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.view.AbstractHttpExchangeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mikoto
 * @date 2021/11/14 2:35
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
        String key = null;
        JSONObject outputJson = new JSONObject();

        if (objects[0] instanceof String) {
            outputJson.put("error", false);
            outputJson.put("msg", "confirm page");
            JSONObject confirmBody = new JSONObject();

            confirmBody.put("key", objects[0]);

            outputJson.put("body", confirmBody);
            outputJson.put("systemTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

        return outputJson;
    }
}
