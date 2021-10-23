package net.mikoto.pixiv.pixivforward.util;

/**
 * @author mikoto
 * Created at 15:17:32, 2021/10/2
 * Project: pixiv-forward
 */
public class UserData {
    // 数据暂存类

    private static String url;
    private static String userName;
    private static String userPassword;

    public static String getUserPassword() {
        return userPassword;
    }

    public static void setUserPassword(String userPassword) {
        UserData.userPassword = userPassword;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserData.userName = userName;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        UserData.url = url;
    }
}
