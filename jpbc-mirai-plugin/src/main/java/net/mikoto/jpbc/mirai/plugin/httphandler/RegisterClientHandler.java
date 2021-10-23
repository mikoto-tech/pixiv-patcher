package net.mikoto.jpbc.mirai.plugin.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mikoto.jpbc.mirai.plugin.controller.RegisterClientController;
import net.mikoto.jpbc.mirai.plugin.controller.impl.RegisterClientControllerImpl;
import net.mikoto.jpbc.mirai.plugin.view.impl.RegisterClientViewImpl;

import java.io.IOException;

import static net.mikoto.jpbc.mirai.plugin.util.HttpUtil.formData2Dic;

/**
 * @author mikoto
 * @date 2021/10/16 16:23
 */
public class RegisterClientHandler implements HttpHandler {
    /**
     * Register client http handler.
     *
     * @param httpExchange The http exchange object.
     * @throws IOException Error.
     */
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        RegisterClientController registerClientController = new RegisterClientControllerImpl(new RegisterClientViewImpl());
        registerClientController.entry(httpExchange, formData2Dic(httpExchange.getRequestURI().getQuery()));
    }
}
