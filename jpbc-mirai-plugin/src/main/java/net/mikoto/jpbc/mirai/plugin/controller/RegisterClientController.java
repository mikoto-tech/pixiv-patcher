package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/23 18:45
 */
abstract public class RegisterClientController implements ControllerEntry {
    /**
     * 根据HttpExchange更新RegisterClient数据
     *
     * @param httpExchange Http exchange object.
     * @param fromData     Data input.
     * @throws IOException A error.
     */
    protected abstract void registerClient(HttpExchange httpExchange, Map<String, String> fromData) throws IOException;

    /**
     * Register client controller 入口方法
     *
     * @param httpExchange http响应
     * @param fromData     Data input.
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        registerClient(httpExchange, fromData);
    }
}
