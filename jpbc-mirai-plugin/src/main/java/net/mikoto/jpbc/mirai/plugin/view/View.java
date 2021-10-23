package net.mikoto.jpbc.mirai.plugin.view;

import com.alibaba.fastjson.JSONObject;

/**
 * @author mikoto
 * @date 2021/10/17 0:09
 */
public interface View {
    /**
     * Get view data.
     *
     * @param objects Object
     * @return A json object.
     */
    JSONObject getData(Object... objects);
}
