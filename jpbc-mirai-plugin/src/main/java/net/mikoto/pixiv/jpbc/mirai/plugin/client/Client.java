package net.mikoto.pixiv.jpbc.mirai.plugin.client;

import net.mikoto.pixiv.jpbc.mirai.plugin.util.Sha256Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mikoto
 * @date 2021/10/16 17:57
 */
public class Client {
    private String clientCallbackIp;
    private Integer clientCallbackPort;
    private String clientKey;
    private final List<Integer> clientQqIds = new ArrayList<>();

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

    public List<Integer> getClientQqIds() {
        return clientQqIds;
    }

    public List<Integer> addQqId(Integer qqId) {
        clientQqIds.add(qqId);
        return clientQqIds;
    }

    public List<Integer> removeQqId(Integer qqId) {
        clientQqIds.remove(qqId);
        return clientQqIds;
    }

    public String getClientKey() {
        return clientKey;
    }
}
