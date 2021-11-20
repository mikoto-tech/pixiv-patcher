package net.mikoto.pixiv.jpbc.mirai.plugin.controller.impl;

import com.sun.net.httpserver.HttpExchange;
import net.mikoto.pixiv.jpbc.mirai.plugin.client.ClientManager;
import net.mikoto.pixiv.jpbc.mirai.plugin.controller.ConfirmClientController;
import net.mikoto.pixiv.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/11/14 4:05
 */
public class ConfirmClientControllerImpl extends ConfirmClientController {
    private final AbstractHttpExchangeView httpExchangeView;

    public ConfirmClientControllerImpl(AbstractHttpExchangeView httpExchangeView) {
        this.httpExchangeView = httpExchangeView;
    }

    /**
     * 根据HttpExchange更新ConfirmClient数据
     *
     * @param httpExchange Http exchange object.
     * @param fromData     Data
     * @throws IOException A error.
     */
    @Override
    protected void updateConfirmClient(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        String key = fromData.get("key");

        if (key != null) {
            Boolean result = ClientManager.getInstance().confirmClient(key);

            httpExchangeView.update(httpExchange, result, "Success");
        } else {
            httpExchangeView.update(httpExchange, Boolean.FALSE, "Failed");
        }
    }
}
