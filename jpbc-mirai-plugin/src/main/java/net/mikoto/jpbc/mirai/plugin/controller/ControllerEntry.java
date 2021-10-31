package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2021/10/30 17:39
 */
public interface ControllerEntry {
    /**
     * Controller 入口方法
     *
     * @param httpExchange http响应
     * @param objects Others object.
     * @throws IOException A error.
     */
    void entry(HttpExchange httpExchange, Object... objects) throws IOException;
}
