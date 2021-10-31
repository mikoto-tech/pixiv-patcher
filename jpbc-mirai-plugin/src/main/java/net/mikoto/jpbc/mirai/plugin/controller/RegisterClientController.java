package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/23 18:45
 */
abstract public class RegisterClientController {
    /**
     * 根据HttpExchange更新RegisterClient数据
     *
     * @param httpExchange Http exchange object.
     * @throws IOException A error.
     */
    protected abstract void updateRegisterClient(HttpExchange httpExchange) throws IOException;

    /**
     * Register client controller 入口方法
     *
     * @param httpExchange http响应
     * @param objects Others objects.
     * @throws IOException A error.
     */
    public void entry(HttpExchange httpExchange, Object... objects) throws IOException {
        if (objects[0] instanceof Map<?, ?>) {

        }
    };
}
