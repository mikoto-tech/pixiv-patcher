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
public class JpbcInfoControllerImpl extends JpbcInfoController {
    private final AbstractHttpExchangeView httpExchangeView;

    public JpbcInfoControllerImpl(AbstractHttpExchangeView httpExchangeView) {
        this.httpExchangeView = httpExchangeView;
    }

    /**
     * 根据HttpExchange更新Jpbc数据
     *
     * @param httpExchange Http exchange object.
     */
    @Override
    protected void updateJpbcInfo(HttpExchange httpExchange) throws IOException {
        httpExchangeView.update(httpExchange, Plugin.VERSION, Plugin.DESCRIPTION, Plugin.AUTHOR, Plugin.PACKAGE, Plugin.HTTP_API_PORT);
    }
}
