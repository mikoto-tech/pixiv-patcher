package net.mikoto.pixiv.pixivforward.dao.impl;

import net.mikoto.pixiv.pixivforward.dao.BaseDAO;
import net.mikoto.pixiv.pixivforward.dao.DeviceDAO;
import net.mikoto.pixiv.pixivforward.model.Device;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * @author mikoto
 * Created at 14:02:55, 2021/9/20
 * Project: pixiv-forward
 */
public class DeviceDAOImpl extends BaseDAO implements DeviceDAO {
    /**
     * Query device in database.
     *
     * @param sql SQL statement.
     * @return A device object
     * @throws SQLException SQLException
     */
    @Override
    public Device getDevice(String sql) throws SQLException {
        ResultSet resultSet = executeQuery(sql);
        Device outputDevice = new Device();

        while (resultSet.next()) {
            // 从数据库取出设备数据
            outputDevice.setDeviceId(resultSet.getInt("pk_device_id"));
            outputDevice.setDeviceName(resultSet.getString("device_name"));
            outputDevice.setDeviceIp(resultSet.getString("device_ip"));
            outputDevice.setDeviceKey(resultSet.getString("device_key"));
            outputDevice.setDeviceSalt(resultSet.getString("device_salt"));
            outputDevice.setCreateTime(new SimpleDateFormat("yyyy-MM-dd " +
                    "HH:mm:ss").format(resultSet.getDate("create_time")));
            outputDevice.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd " +
                    "HH:mm:ss").format(resultSet.getDate("update_time")));
        }

        closeResource();
        return outputDevice;
    }
}
