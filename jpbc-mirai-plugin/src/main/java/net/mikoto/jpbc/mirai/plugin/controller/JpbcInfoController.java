package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/10/23 20:00
 */
abstract public class JpbcInfoController implements ControllerEntry{
    /**
     * 根据HttpExchange更新JpbcInfo数据
     *
     * @param httpExchange Http exchange object.
     * @param fromData     Data
     * @throws IOException A error.
     */
    protected abstract void updateJpbcInfo(HttpExchange httpExchange, Map<String, String> fromData) throws IOException;

    /**
     * Jpbc info controller 入口方法
     *
     * @param httpExchange http响应
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        updateJpbcInfo(httpExchange, fromData);
    }
}
