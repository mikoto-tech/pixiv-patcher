package net.mikoto.pixiv.pixivforward.service;

/**
 * @author mikoto
 * @date 2021/11/20 13:21
 */
public interface UserService {
    /**
     * Confirm key
     *
     * @param key Input key.
     * @return Result.
     */
    boolean confirmKey(String key);

    /**
     * Get userName.
     *
     * @return UserName
     */
    String getUserName();

    /**
     * Get userId.
     *
     * @return UserId
     */
    int getUserId();
}
