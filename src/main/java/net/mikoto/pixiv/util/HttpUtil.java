package net.mikoto.pixiv.util;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
                final String key = URLDecoder.decode(keyAndVal[0], StandardCharsets.UTF_8);
                final String val = URLDecoder.decode(keyAndVal[1], StandardCharsets.UTF_8);
                result.put(key, val);
            }
        });
        return result;
    }


    /**
     * Send a get request.
     * It will return a string.
     *
     * @param url The link of this request.
     * @return The string this request return.
     * @throws IOException IOException.
     */
    public static String httpGet(String url) throws IOException {
        String result;
        OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        result = Objects.requireNonNull(response.body()).string();
        return result;
    }
}
