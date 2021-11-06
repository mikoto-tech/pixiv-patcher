package net.mikoto.jpbc.mirai.plugin.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/23 4:17
 */
public class ClientManager {
    /**
     * 初始化单例
     */
    private static final ClientManager INSTANCE = new ClientManager();

    /**
     * 初始化ArrayList
     */
    private final ArrayList<Client> clientArrayList = new ArrayList<>();

    /**
     * 客户端的状态:
     * 目前(v1.0.0版本)客户端有两种状态:
     * - Unverified 未验证
     * - Normal 正常运行
     * 不同状态的客户端对应的id会储存在ArrayList中,已经移除的客户端将会被移除
     */
    protected final Map<String, Integer> unverifiedClientMap = new HashMap<>();
    protected final Map<String, Integer> normalClientMap = new HashMap<>();
    private Date nextConfirmTime;

    public static ClientManager getInstance() {
        return INSTANCE;
    }

    /**
     * Get client form the client array list by key.
     *
     * @param key The key of this client.
     * @return A client object. If there is no client with corresponding ID, null is returned
     */
    public Client getClientByKey(String key) {
        return clientArrayList.get(normalClientMap.get(key));
    }

    /**
     * Add client into client array list.
     *
     * @param inputClient The client.
     * @return Client id.
     */
    public int updateClient(Client inputClient) {
        boolean flag = true;
        Client newClient = inputClient;
        for (Client client :
                clientArrayList) {
            if (client.getClientCallbackIp().equals(inputClient.getClientCallbackIp())) {
                flag = false;
                clientArrayList.set(
                        clientArrayList.indexOf(client),
                        newClient = client
                                .setClientCallbackIp(inputClient.getClientCallbackIp())
                                .setClientCallbackPort(inputClient.getClientCallbackPort()));
                break;
            }
        }

        if (flag) {
            clientArrayList.add(newClient);
        }

        return clientArrayList.indexOf(newClient);
    }

    /**
     * Register client and get the key of this client.
     * <p>
     * 客户端重要参数:
     * - callbackIp 回调IP
     * - callbackPort 回调端口
     * 回调IP和回调端口两项共同组成客户端的回调地址,是与客户端通信的重要凭证
     * <p>
     * - key 密钥
     * 密钥是客户端的通信凭证.客户端需通过/RegisterClient得到自身的key,且每秒只能生成一个key.若客户端丢失了key便无法再找回此key
     * 也就是一个key只能发行一次,且发行后便无法再次获取已经发行的key,客户端只能再次通过/RegisterClient获取到新的key
     * <p>
     * - id 客户端id
     * 新注册客户端时会生成客户端对应的客户端id(若客户端被清除有一定可能会分配到不同的id)
     * 客户端id是在服务端中客户端的唯一凭证(在服务端中并不会使用key作为凭证),客户端id在生成之后便无法再次更改,除了客户端被移除或其它特殊情况,
     * 一般id不会主动更改.
     * <p>
     * key的创建:
     * 为了保证key的唯一性,我们使用SHA256算法(算法来自:https://blog.csdn.net/wang864676212/article/details/81776261)计算出唯一
     * 的key.同时,为了确保key不被盗用,我们使用了当前的时间作为salt,这样,每次调用方法就会生成完全不同的key值,大大提高了安全性.
     * <p>
     * 冗余客户端处理:
     * 为了不浪费多余的服务器资源,每隔一个小时便会 向注册时提供的回调地址发送请求.若此客户端需要继续使用,应重新向:/GetKey发送请求进
     * 行注册.若此客户端1分钟内未重新注册,我们便会从内存中清除
     *
     * @param callbackIp   The client callback ip.
     * @param callbackPort The client callback port.
     * @return Client key(SHA256).
     */
    public String registerClient(String callbackIp, int callbackPort) {
        Integer clientCode = updateClient(
                new Client()
                        .setClientCallbackIp(callbackIp)
                        .setClientCallbackPort(callbackPort)
        );

        Client client = clientArrayList.get(clientCode);
        String key;

        if (normalClientMap.containsValue(clientCode)) {
            clientCode = normalClientMap.remove(client.getClientKey());
        }

        key = client.update();
        unverifiedClientMap.put(key, clientCode);

        return key;
    }

    /**
     * remove the client in memory
     *
     * @param key The key of this client.
     * @return Is success
     */
    protected boolean removeClient(String key) {
        Integer clientCode = null;
        if (normalClientMap.containsKey(key)) {
            clientCode = normalClientMap.remove(key);
        } else if (unverifiedClientMap.containsKey(key)) {
            clientCode = unverifiedClientMap.remove(key);
        }

        if (clientCode != null) {
            clientArrayList.remove(clientCode.intValue());
            return true;
        } else {
            return false;
        }
    }

    public Date getNextConfirmTime() {
        return nextConfirmTime;
    }

    protected void setNextConfirmTime(Date nextConfirmTime) {
        this.nextConfirmTime = nextConfirmTime;
    }

    public Map<String, Integer> getUnverifiedClientMap() {
        return unverifiedClientMap;
    }

    public void setNormalClient(String key) {
        normalClientMap.put(key, unverifiedClientMap.remove(key));
    }
}
