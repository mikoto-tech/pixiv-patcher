package net.mikoto.jpbc.mirai.plugin.client;

import net.mikoto.jpbc.mirai.plugin.util.Sha256Util;

/**
 * @author mikoto
 * @date 2021/10/16 17:57
 */
public class Client {
    private String clientCallbackIp;
    private Integer clientCallbackPort;
    private String clientKey;
    private Integer[] clientIds;

    public String update() {
        clientKey = Sha256Util.getSha256(clientCallbackIp + ":MIKOTO_JAVA_PIXIV_BOT_CONNECTIVITY_KEY:114514:1919810:" + System.currentTimeMillis());
        return clientKey;
    }

    public String getClientCallbackIp() {
        return clientCallbackIp;
    }

    public Client setClientCallbackIp(String clientCallbackIp) {
        this.clientCallbackIp = clientCallbackIp;
        return this;
    }

    public Integer getClientCallbackPort() {
        return clientCallbackPort;
    }

    public Client setClientCallbackPort(Integer clientCallbackPort) {
        this.clientCallbackPort = clientCallbackPort;
        return this;
    }

    public Integer[] getClientIds() {
        return clientIds;
    }

    public Client setClientIds(Integer[] clientIds) {
        this.clientIds = clientIds;
        return this;
    }

    public String getClientKey() {
        return clientKey;
    }
}
