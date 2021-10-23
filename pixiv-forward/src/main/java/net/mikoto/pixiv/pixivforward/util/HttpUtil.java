package net.mikoto.pixiv.pixivforward.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * Use okhttp client to make a http request.
 *
 * @author cp
 * @modify mikoto
 */
public class HttpUtil {
    private static final int SUCCESS_CODE = 200;
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
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        result = Objects.requireNonNull(response.body()).string();
        return result;
    }

    /**
     * Send a get request.
     * It will return a string.
     * This method will not report error.
     *
     * @param url The link of this request.
     * @return The string this request return.
     */
    public static String httpGetUnsafe(String url) {
        String result = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = Objects.requireNonNull(response.body()).string();
        } catch (IOException ignored) {
        }
        return result;
    }

    /**
     * Send a get request.
     * It will return bytes.
     *
     * @param url The link of this request.
     * @return The bytes it return.
     * @throws IOException IOException.
     */
    public static byte[] httpGetWithReferer(String url) throws IOException {
        byte[] result;
        OkHttpClient client = new OkHttpClient();
        Request request =
                new Request.Builder().url(url).addHeader("Referer",
                        "https://www.pixiv.net").build();

        Response response = client.newCall(request).execute();

        if (response.code() == SUCCESS_CODE) {
            result = Objects.requireNonNull(response.body()).bytes();
            return result;
        } else {
            return httpGetWithRefererUnsafe("http://file.mikoto.net.cn/Error.jpg");
        }
    }

    /**
     * Send a get request.
     * It will return bytes.
     * This method will not report error.
     *
     * @param url The link of this request.
     * @return The bytes it return.
     */
    public static byte[] httpGetWithRefererUnsafe(String url) {
        byte[] result = null;
        OkHttpClient client = new OkHttpClient();
        Request request =
                new Request.Builder().url(url).addHeader("Referer",
                        "https://www.pixiv.net").build();

        try {
            Response response = client.newCall(request).execute();
            result = Objects.requireNonNull(response.body()).bytes();
        } catch (IOException ignored) {
        }
        return result;
    }

    /**
     * Encode normal string to unicode string.
     *
     * @param str The string you need to encode.
     * @return Unicode string.
     */
    public static String encode(String str) {
        char[] chars = str.toCharArray();
        StringBuilder unicodeStr = new StringBuilder();
        for (char aChar : chars) {
            if (String.valueOf(aChar).matches("[\u4e00-\u9fa5]")) {
                String hexB = Integer.toHexString(aChar);
                if (hexB.length() <= 2) {
                    hexB = "00" + hexB;
                }
                unicodeStr.append("\\u");
                unicodeStr.append(hexB);
            } else {
                unicodeStr.append(aChar);
            }
        }
        return unicodeStr.toString();
    }

}