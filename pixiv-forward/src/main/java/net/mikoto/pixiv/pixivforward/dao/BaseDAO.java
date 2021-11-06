package net.mikoto.pixiv.pixivforward.dao;

import java.sql.*;

/**
 * @author mikoto
 * Created at 15:23:00, 2021/9/30
 * Project: pixiv-forward
 */
abstract public class BaseDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    protected String url;
    protected String userName;
    protected String userPassword;

    /**
     * Get the connection.
     * If the connection is null, it will create one.
     *
     * @throws SQLException SQLException.
     */
    public void getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url,
                    userName,
                    userPassword);
        }
    }

    /**
     * Query data in database.
     *
     * @param sql SQL statement.
     * @param objs Others.
     * @return A result set.
     * @throws SQLException SQLException
     */
    public ResultSet executeQuery(String sql, Object... objs) throws SQLException {
        getConnection();
        preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < objs.length; i++) {
            preparedStatement.setObject(i + 1, objs[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    /**
     * Close all the resource.
     */
    public void closeResource() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
