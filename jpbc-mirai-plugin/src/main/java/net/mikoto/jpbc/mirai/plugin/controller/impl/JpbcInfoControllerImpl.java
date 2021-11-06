package net.mikoto.jpbc.mirai.plugin.controller.impl;

import com.sun.net.httpserver.HttpExchange;
import net.mikoto.jpbc.mirai.plugin.JpbcMiraiPlugin;
import net.mikoto.jpbc.mirai.plugin.controller.JpbcInfoController;
import net.mikoto.jpbc.mirai.plugin.view.AbstractHttpExchangeView;

import java.io.IOException;
import java.util.Map;

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
    protected void updateJpbcInfo(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        httpExchangeView.update(httpExchange, JpbcMiraiPlugin.VERSION, JpbcMiraiPlugin.DESCRIPTION, JpbcMiraiPlugin.AUTHOR, JpbcMiraiPlugin.PACKAGE, JpbcMiraiPlugin.HTTP_API_PORT);
    }
}
