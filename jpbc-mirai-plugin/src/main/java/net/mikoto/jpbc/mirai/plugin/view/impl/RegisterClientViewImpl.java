package net.mikoto.jpbc.mirai.plugin.view.impl;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.jpbc.mirai.plugin.client.Client;
import net.mikoto.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mikoto
 * @date 2021/10/17 0:25
 */
public class RegisterClientViewImpl extends AbstractHttpExchangeView {
    /**
     * Get view data.
     *
     * @param objects Object
     * @return A json object.
     */
    @Override
    public JSONObject getData(Object... objects) {
        // 初始化输出Json
        JSONObject outputJson = new JSONObject();
        JSONObject clientRegisterBody = new JSONObject();

        Boolean error = null;
        String msg = null;
        Client client = null;

        if (objects[0] instanceof Boolean && objects[1] instanceof String) {
            error = (Boolean) objects[0];
            msg = (String) objects[1];
        }

        if (objects[2] instanceof Client) {
            client = (Client) objects[2];
        }

        //处理数据
        outputJson.put("error", error);
        outputJson.put("msg", msg);

        if (client != null) {
            clientRegisterBody.put("key", client.getClientKey());
            clientRegisterBody.put("callbackIp", client.getClientCallbackIp());
            clientRegisterBody.put("callbackPort", client.getClientCallbackPort());
            clientRegisterBody.put("QQIds", client.getClientIds());
        }

        outputJson.put("body", clientRegisterBody);
        outputJson.put("systemTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return outputJson;
    }
}
