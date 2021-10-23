package net.mikoto.pixiv.pixivforward.dao;

import net.mikoto.pixiv.pixivforward.model.Device;

import java.sql.SQLException;

/**
 * @author mikoto
 * Created at 21:31:20, 2021/9/19
 * Project: PixivRelay
 */
public interface DeviceDAO {
    /**
     * Query device in database.
     *
     * @param sql SQL statement.
     * @return A device object
     * @throws SQLException SQLException
     */
    Device getDevice(String sql) throws SQLException;
}
