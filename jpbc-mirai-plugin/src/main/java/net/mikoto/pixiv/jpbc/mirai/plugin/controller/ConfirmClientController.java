package net.mikoto.pixiv.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/11/14 4:04
 */
abstract public class ConfirmClientController implements ControllerEntry {
    /**
     * 根据HttpExchange更新ConfirmClient数据
     *
     * @param httpExchange Http exchange object.
     * @param fromData     Data
     * @throws IOException A error.
     */
    protected abstract void updateConfirmClient(HttpExchange httpExchange, Map<String, String> fromData) throws IOException;

    /**
     * Confirm client controller 入口方法
     *
     * @param httpExchange http响应
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {
        updateConfirmClient(httpExchange, fromData);
    }
}
