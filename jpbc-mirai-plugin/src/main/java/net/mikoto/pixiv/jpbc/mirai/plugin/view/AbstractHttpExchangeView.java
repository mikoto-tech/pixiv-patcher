package net.mikoto.pixiv.jpbc.mirai.plugin.view;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author mikoto
 * @date 2021/10/16 23:46
 */
public abstract class AbstractHttpExchangeView implements View {
    /**
     * Update view.
     *
     * @param httpExchange A http exchange object.
     * @param objects      Other objects.
     * @throws IOException An exception
     */
    public final void update(HttpExchange httpExchange, Object... objects) throws IOException {
        // 指定响应头
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=UTF-8");
        // 发送响应头
        httpExchange.sendResponseHeaders(200, 0);
        // 输出
        OutputStream os = httpExchange.getResponseBody();
        os.write(getData(objects).toString().getBytes());
        os.close();
    }
}
