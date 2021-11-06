package net.mikoto.jpbc.mirai.plugin.httpserver;

import com.sun.net.httpserver.HttpServer;
import net.mikoto.jpbc.mirai.plugin.controller.ControllerEntry;

import java.io.*;
import java.net.InetSocketAddress;

import static net.mikoto.jpbc.mirai.plugin.JpbcMiraiPlugin.HTTP_API_PORT;
import static net.mikoto.jpbc.mirai.plugin.util.HttpUtil.formData2Dic;

/**
 * @author mikoto
 * @date 2021/11/6 21:29
 */
public class HttpserverManager {
    /**
     * 初始化单例
     */
    private static final HttpserverManager INSTANCE = new HttpserverManager();

    private static HttpServer server;

    /**
     * 创建服务器
     */
    private HttpserverManager() {
        try {
            System.out.println("Creating httpserver on port: " + HTTP_API_PORT);
            server = HttpServer.create(new InetSocketAddress(HTTP_API_PORT), 0);
            server.createContext("/", httpExchange -> {
                // 指定响应头
                httpExchange.getResponseHeaders().add("Content-Type", "text/html; charset=utf-8");
                // 发送响应头
                httpExchange.sendResponseHeaders(200, 0);
                // 输出
                OutputStream os = httpExchange.getResponseBody();
                InputStream path = this.getClass().getResourceAsStream("/index.html");
                assert path != null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(path));
                os.write(reader.readLine().getBytes());
                os.close();
            });
        } catch (IOException e) {
            System.err.println("ERROR::Server can't create at once.");
            e.printStackTrace();
        }
    }

    public static HttpserverManager getInstance() {
        return INSTANCE;
    }

    /**
     * 创建http上下文
     *
     * @param context         上下文
     * @param controllerEntry controller入口
     * @return 当前实例
     */
    public HttpserverManager createContext(String context, ControllerEntry controllerEntry) {
        server.createContext(context, httpExchange -> controllerEntry.entry(httpExchange, formData2Dic(httpExchange.getRequestURI().getQuery())));
        return this;
    }

    /**
     * 启动服务器
     */
    public void start() {
        server.start();
    }

    /**
     * 停止服务器
     */
    public void stop() {
        server.stop(0);
    }
}
