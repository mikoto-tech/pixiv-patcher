package net.mikoto.jpbc.mirai.plugin.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/16 16:07
 */
public class HttpUtil {
    public static final Integer TWO = 2;

    /**
     * Get data by from data.
     *
     * @param formData From data string.
     * @return A dic.
     */
    public static Map<String, String> formData2Dic(String formData) {
        Map<String, String> result = new HashMap<>(10);
        if (formData == null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = item.split("=");
            if (keyAndVal.length == TWO) {
                try {
                    final String key = URLDecoder.decode(keyAndVal[0], "utf8");
                    final String val = URLDecoder.decode(keyAndVal[1], "utf8");
                    result.put(key, val);
                } catch (UnsupportedEncodingException ignored) {
                }
            }
        });
        return result;
    }
}
