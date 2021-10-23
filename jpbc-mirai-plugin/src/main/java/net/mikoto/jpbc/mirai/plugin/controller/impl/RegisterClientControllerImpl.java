package net.mikoto.jpbc.mirai.plugin.controller.impl;

import com.sun.net.httpserver.HttpExchange;
import net.mikoto.jpbc.mirai.plugin.client.Client;
import net.mikoto.jpbc.mirai.plugin.client.ClientManager;
import net.mikoto.jpbc.mirai.plugin.controller.RegisterClientController;
import net.mikoto.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/23 18:46
 */
public class RegisterClientControllerImpl implements RegisterClientController {
    private static final String GET_CALLBACK_PORT = "callbackPort";
    private static final String GET_CALLBACK_IP = "callbackIp";
    private final AbstractHttpExchangeView httpExchangeView;

    public RegisterClientControllerImpl(AbstractHttpExchangeView httpExchangeView) {
        this.httpExchangeView = httpExchangeView;
    }

    /**
     * Register client controller 入口方法
     *
     * @param httpExchange http响应
     * @param fromData     传入的数据
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        String callbackIp = fromData.get(GET_CALLBACK_IP);
        int callbackPort = Integer.parseInt(fromData.get(GET_CALLBACK_PORT));

        //处理数据
        if (callbackIp != null) {
            // 通过key获取ArrayList中的Client对象
            Client client = ClientManager.getInstance().getClient(ClientManager.getInstance().registerClient(callbackIp, callbackPort));

            httpExchangeView.update(httpExchange, Boolean.FALSE, "Register device successful", client);
        } else {
            httpExchangeView.update(httpExchange, Boolean.TRUE, "Please pass in the java pixiv bot connectivity client callback port and callback ip");
        }
    }
}
