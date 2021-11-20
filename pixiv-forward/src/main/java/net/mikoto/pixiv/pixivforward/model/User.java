package net.mikoto.pixiv.pixivforward.model;

/**
 * @author mikoto
 * @date 2021/11/20 5:43
 */
public class User {
    private int userId;
    private String userName;
    private String userKey;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }
}
