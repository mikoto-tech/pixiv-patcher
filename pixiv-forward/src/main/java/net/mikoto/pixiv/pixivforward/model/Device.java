package net.mikoto.pixiv.pixivforward.model;

/**
 * @author mikoto
 * Created at 21:33:45, 2021/9/19
 * Project: PixivRelay
 */
public class Device {
    private int deviceId;
    private String deviceName;
    private String deviceIp;
    private String deviceKey;
    private String deviceSalt;
    private String createTime;
    private String updateTime;

    public String getDeviceSalt() {
        return deviceSalt;
    }

    public void setDeviceSalt(String deviceSalt) {
        this.deviceSalt = deviceSalt;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
