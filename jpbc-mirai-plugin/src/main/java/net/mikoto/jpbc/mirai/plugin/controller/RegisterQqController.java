package net.mikoto.jpbc.mirai.plugin.controller;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/11/6 22:38
 */
abstract public class RegisterQqController implements ControllerEntry {
    /**
     * 注册qq号,并根据http更新数据
     *
     * @param httpExchange HttpExchange
     * @param fromData     Data
     * @throws IOException Error
     */
    protected abstract void registerQq(HttpExchange httpExchange, Map<String, String> fromData) throws IOException;

    /**
     * Controller 入口方法
     *
     * @param httpExchange http响应
     * @param fromData     Data input.
     * @throws IOException A error.
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> fromData) throws IOException {

    }
}
