package net.mikoto.pixiv.pixivforward.dao.impl;

import net.mikoto.pixiv.pixivforward.dao.BaseDAO;
import net.mikoto.pixiv.pixivforward.dao.UserDAO;
import net.mikoto.pixiv.pixivforward.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2021/11/20 5:29
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {
    public UserDAOImpl(String url, String userName, String userPassword) {
        super.userPassword = userPassword;
        super.url = url;
        super.userName = userName;
    }

    /**
     * Query user in database.
     *
     * @param sql SQL statement.
     * @return A device object
     * @throws SQLException SQLException
     */
    @Override
    public User getUser(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(sql);
        User outputUser = new User();

        while (resultSet.next()) {
            // 从数据库取出设备数据
            outputUser.setUserId(resultSet.getInt("pk_id"));
            outputUser.setUserName(resultSet.getString("user_name"));
            outputUser.setUserKey(resultSet.getString("user_key"));
        }

        closeResource();
        return outputUser;
    }
}
