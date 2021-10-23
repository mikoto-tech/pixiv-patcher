package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/23 18:45
 */
public interface RegisterClientController {
    /**
     * Register client controller 入口方法
     *
     * @param httpExchange http响应
     * @param fromData     传入的数据
     * @throws IOException A error.
     */
    void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException;
}
