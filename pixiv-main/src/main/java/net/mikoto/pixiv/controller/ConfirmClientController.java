package net.mikoto.pixiv.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/11/7 12:55
 */
abstract public class ConfirmClientController implements ControllerEntry {
    /**
     * Confirm client.
     *
     * @param httpExchange A http exchange object.
     * @param formData     data
     * @throws IOException error
     */
    protected abstract void confirmClient(HttpExchange httpExchange, Map<String, String> formData) throws IOException;

    /**
     * Register client controller 入口方法
     *
     * @param httpExchange http响应
     * @param fromData     Data input.
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        confirmClient(httpExchange, fromData);
    }
}
