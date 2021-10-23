package net.mikoto.jpbc.mirai.plugin.controller.impl;

import com.sun.net.httpserver.HttpExchange;
import net.mikoto.jpbc.mirai.plugin.Plugin;
import net.mikoto.jpbc.mirai.plugin.controller.JpbcInfoController;
import net.mikoto.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2021/10/23 20:01
 */
public class JpbcInfoControllerImpl implements JpbcInfoController {
    private final AbstractHttpExchangeView httpExchangeView;

    public JpbcInfoControllerImpl(AbstractHttpExchangeView httpExchangeView) {
        this.httpExchangeView = httpExchangeView;
    }

    /**
     * Jpbc info controller 入口方法
     *
     * @param httpExchange http响应
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange) throws IOException {
        httpExchangeView.update(httpExchange, Plugin.VERSION, Plugin.DESCRIPTION, Plugin.AUTHOR, Plugin.PACKAGE, Plugin.HTTP_API_PORT);
    }
}
