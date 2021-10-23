package net.mikoto.jpbc.mirai.plugin.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mikoto.jpbc.mirai.plugin.controller.JpbcInfoController;
import net.mikoto.jpbc.mirai.plugin.controller.impl.JpbcInfoControllerImpl;
import net.mikoto.jpbc.mirai.plugin.view.impl.JpbcInfoViewImpl;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2021/10/16 15:15
 */
public class JpbcInfoHandler implements HttpHandler {
    /**
     * Get JPBC information http handler.
     *
     * @param httpExchange The http exchange object.
     * @throws IOException Error.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        JpbcInfoController jpbcInfoController = new JpbcInfoControllerImpl(new JpbcInfoViewImpl());
        jpbcInfoController.entry(httpExchange);
    }
}