package net.mikoto.pixiv.pixivforward.service;

import net.mikoto.pixiv.pixivforward.model.Device;

/**
 * @author mikoto
 * Created at 20:48:22, 2021/9/30
 * Project: pixiv-forward
 */
public interface DeviceService {
    /**
     * Get device by id.
     *
     * @param deviceId The device id.
     * @return A device object.
     */
    Device getDeviceById(int deviceId);


    /**
     * Get device by name.
     *
     * @param deviceName The device name.
     * @return A device object.
     */
    Device getDeviceByName(String deviceName);


    /**
     * Get device by ip.
     *
     * @param deviceIp The device ip.
     * @return A device object.
     */
    Device getDeviceByIp(String deviceIp);

    /**
     * Get device by key.
     *
     * @param deviceKey The device key.
     * @return A device object.
     */
    Device getDeviceByKey(String deviceKey);

    /**
     * Verify the legitimacy of the equipment.
     *
     * @return Is it legal.
     */
    boolean verifyDevice();

    /**
     * Update device and verify the legitimacy of the equipment
     *
     * @param device A device object
     * @return Is it legal
     */
    boolean verifyDevice(Device device);
}
