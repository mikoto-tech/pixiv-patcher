package net.mikoto.pixiv.util;

/**
 * @author mikoto2464
 */

public class CharUtil {
    /**
     * 字符串转unicode
     *
     * @param str String
     * @return Unicode
     */
    public static String stringToUnicode(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] c = str.toCharArray();
        for (char value : c) {
            stringBuilder.append("\\u").append(Integer.toHexString(value));
        }
        return stringBuilder.toString();
    }

    /**
     * Unicode转String
     *
     * @param unicode Unicode
     * @return String
     */
    public static String unicodeToString(String unicode) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            stringBuilder.append((char) index);
        }
        return stringBuilder.toString();
    }
}