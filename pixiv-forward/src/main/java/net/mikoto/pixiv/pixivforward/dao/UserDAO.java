package net.mikoto.pixiv.pixivforward.dao;

import net.mikoto.pixiv.pixivforward.model.User;

import java.sql.SQLException;

/**
 * @author mikoto
 * @date 2021/11/20 5:28
 */
public interface UserDAO {
    /**
     * Query user in database.
     *
     * @param sql SQL statement.
     * @return A device object
     * @throws SQLException SQLException
     */
    User getUser(String sql) throws SQLException;
}
