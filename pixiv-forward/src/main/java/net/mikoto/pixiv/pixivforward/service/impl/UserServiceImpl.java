package net.mikoto.pixiv.pixivforward.service.impl;

import net.mikoto.pixiv.pixivforward.dao.UserDAO;
import net.mikoto.pixiv.pixivforward.dao.impl.UserDAOImpl;
import net.mikoto.pixiv.pixivforward.model.User;
import net.mikoto.pixiv.pixivforward.service.UserService;

import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2021/11/20 13:33
 */
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private User user;

    public UserServiceImpl(String url, String userName, String userPassword) {
        userDAO = new UserDAOImpl(url, userName, userPassword);
    }

    /**
     * Confirm key
     *
     * @param key Input key.
     * @return Result.
     */
    @Override
    public boolean confirmKey(String key) {
        try {
            user = userDAO.getUser("SELECT * from pixiv_web_data" +
                    ".user_data WHERE user_key='" + key +
                    "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user.getUserKey().equals(key);
    }

    /**
     * Get userName.
     *
     * @return UserName
     */
    @Override
    public String getUserName() {
        return user.getUserName();
    }

    /**
     * Get userId.
     *
     * @return UserId
     */
    @Override
    public int getUserId() {
        return user.getUserId();
    }
}
