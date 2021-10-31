package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * @author mikoto
 * @date 2021/10/23 20:00
 */
abstract public class JpbcInfoController implements ControllerEntry{
    /**
     * 根据HttpExchange更新JpbcInfo数据
     *
     * @param httpExchange Http exchange object.
     * @throws IOException A error.
     */
    protected abstract void updateJpbcInfo(HttpExchange httpExchange) throws IOException;

    /**
     * Jpbc info controller 入口方法
     *
     * @param httpExchange http响应
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange, Object... objects) throws IOException {
        updateJpbcInfo(httpExchange);
    };
}
