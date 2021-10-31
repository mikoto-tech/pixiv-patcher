package net.mikoto.jpbc.mirai.plugin.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import net.mikoto.jpbc.mirai.plugin.controller.RegisterQqController;
import net.mikoto.jpbc.mirai.plugin.controller.impl.RegisterQqControllerImpl;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2021/10/30 16:34
 */
public class RegisterQqHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        RegisterQqController registerQqController = new RegisterQqControllerImpl();
    }
}
