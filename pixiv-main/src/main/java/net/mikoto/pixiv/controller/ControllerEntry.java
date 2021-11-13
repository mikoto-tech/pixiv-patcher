package net.mikoto.pixiv.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/30 17:39
 */
public interface ControllerEntry {
    /**
     * Controller 入口方法
     *
     * @param httpExchange http响应
     * @param fromData     Data input.
     * @throws IOException A error.
     */
    void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException;
}
