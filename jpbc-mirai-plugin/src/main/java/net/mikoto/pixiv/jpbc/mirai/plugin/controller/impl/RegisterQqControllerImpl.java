package net.mikoto.pixiv.jpbc.mirai.plugin.controller.impl;

import com.sun.net.httpserver.HttpExchange;
import net.mikoto.pixiv.jpbc.mirai.plugin.controller.RegisterQqController;
import net.mikoto.pixiv.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/11/6 22:45
 */
public class RegisterQqControllerImpl extends RegisterQqController {
    private static final String GET_KEY = "key";
    AbstractHttpExchangeView httpExchangeView;

    public RegisterQqControllerImpl(AbstractHttpExchangeView httpExchangeView) {
        this.httpExchangeView = httpExchangeView;
    }

    /**
     * 注册qq号,并根据http更新数据
     *
     * @param httpExchange HttpExchange
     * @param fromData     Data
     * @throws IOException Error
     */
    @Override
    protected void registerQq(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        String key = fromData.get(GET_KEY);
    }
}
