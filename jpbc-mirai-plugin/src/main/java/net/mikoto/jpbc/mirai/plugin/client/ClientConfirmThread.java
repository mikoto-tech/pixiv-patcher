package net.mikoto.jpbc.mirai.plugin.client;


import java.io.IOException;
import java.util.Date;

import static net.mikoto.jpbc.mirai.plugin.util.HttpUtil.httpGet;

/**
 * @author mikoto
 * @date 2021/11/7 1:52
 */
public class ClientConfirmThread extends Thread {
    public ClientConfirmThread() {
        System.out.println("Creating client confirm thread.");
    }

    /**
     * Start thread.
     */
    @Override
    public void run() {
        System.out.println("Running client confirm thread.");
        ClientManager.getInstance().setNextConfirmTime(new Date(System.currentTimeMillis() + 10000));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String key :
                ClientManager.getInstance().getUnverifiedClientMap().keySet()) {
            Client client = ClientManager.getInstance().getClientByKey(key);
            try {
                if (httpGet("http://" + client.getClientCallbackIp() + ":" + client.getClientCallbackPort() + "/ConfirmClient").equals(key)) {
                    ClientManager.getInstance().setNormalClient(key);
                } else {
                    ClientManager.getInstance().removeClient(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
