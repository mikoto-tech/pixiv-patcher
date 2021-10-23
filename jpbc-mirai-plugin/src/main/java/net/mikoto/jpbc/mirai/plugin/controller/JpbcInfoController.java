package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2021/10/23 20:00
 */
public interface JpbcInfoController {
    /**
     * Jpbc info controller 入口方法
     *
     * @param httpExchange http响应
     * @throws IOException A error.
     */
    void entry(HttpExchange httpExchange) throws IOException;
}
