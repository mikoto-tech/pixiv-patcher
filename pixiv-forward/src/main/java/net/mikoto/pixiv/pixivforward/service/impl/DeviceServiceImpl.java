package net.mikoto.pixiv.pixivforward.service.impl;

import net.mikoto.pixiv.pixivforward.dao.DeviceDAO;
import net.mikoto.pixiv.pixivforward.dao.impl.DeviceDAOImpl;
import net.mikoto.pixiv.pixivforward.model.Device;
import net.mikoto.pixiv.pixivforward.service.DeviceService;
import net.mikoto.pixiv.pixivforward.util.Sha256Util;

/**
 * @author mikoto
 * Created at 20:48:45, 2021/9/30
 * Project: pixiv-forward
 */
public class DeviceServiceImpl implements DeviceService {
    private final DeviceDAO deviceDAO = new DeviceDAOImpl();
    private Device device;

    /**
     * Init object.
     *
     * @param device A device object.
     */
    public DeviceServiceImpl(Device device) {
        this.device = device;
    }

    /**
     * Init object.
     */
    public DeviceServiceImpl() {}

    /**
     * Verify the legitimacy of the equipment.
     *
     * @return Is it legal.
     */
    @Override
    public boolean verifyDevice() {
        if (device != null) {
            return isDeviceLegal(device);
        } else {
            return false;
        }
    }

    /**
     * Update device and verify the legitimacy of the equipment
     *
     * @param device A device object
     * @return Is it legal
     */
    @Override
    public boolean verifyDevice(Device device) {
        this.device = device;

        return isDeviceLegal(device);
    }

    /**
     * Check the device is legal.
     *
     * @param device The device object.
     * @return Is it legal.
     */
    private boolean isDeviceLegal(Device device) {
        if (device != null) {
            try {
                int timestampLong =
                        Integer.parseInt(device.getDeviceKey().split(
                                "000000")[1]);
                String[] rawKey = device.getDeviceKey().split(
                        "000000")[0].split("");

                StringBuilder key =
                        new StringBuilder(rawKey.length - timestampLong - 8);
                StringBuilder timestamp = new StringBuilder(timestampLong);

                for (int i = 0; i < rawKey.length; i++) {
                    if (i % 2 == 0) {
                        key.append(rawKey[i]);
                    } else {
                        if (i >= timestampLong * 2) {
                            key.append(rawKey[i]);
                        } else {
                            timestamp.append(rawKey[i]);
                        }
                    }
                }
                return Sha256Util.getSha256(
                        device.getDeviceName() + device.getDeviceIp() +
                                device.getDeviceSalt() + "114514:1919810" +
                                timestamp).equals(key.toString());
            } catch (NullPointerException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Get device by id.
     *
     * @param deviceId The device id.
     * @return A device object.
     */
    @Override
    public Device getDeviceById(int deviceId) {
        try {
            device = deviceDAO.getDevice("SELECT * from pixiv_data" +
                    ".pixiv_forward_devices WHERE pk_device_id='" + deviceId +
                    "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }

    /**
     * Get device by name.
     *
     * @param deviceName The device name.
     * @return A device object.
     */
    @Override
    public Device getDeviceByName(String deviceName) {
        try {
            device = deviceDAO.getDevice("SELECT * from pixiv_data" +
                    ".pixiv_forward_devices WHERE device_name='" + deviceName +
                    "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }

    /**
     * Get device by ip.
     *
     * @param deviceIp The device ip.
     * @return A device object.
     */
    @Override
    public Device getDeviceByIp(String deviceIp) {
        try {
            device = deviceDAO.getDevice("SELECT * from pixiv_data" +
                    ".pixiv_forward_devices WHERE device_ip='" + deviceIp +
                    "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }

    /**
     * Get device by key.
     *
     * @param deviceKey The device key.
     * @return A device object.
     */
    @Override
    public Device getDeviceByKey(String deviceKey) {
        try {
            device = deviceDAO.getDevice("SELECT * from pixiv_data" +
                    ".pixiv_forward_devices WHERE device_key='" + deviceKey +
                    "';");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return device;
    }
}
