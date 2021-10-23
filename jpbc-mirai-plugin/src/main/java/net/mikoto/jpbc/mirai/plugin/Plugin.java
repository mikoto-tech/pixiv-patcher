package net.mikoto.jpbc.mirai.plugin;

import com.sun.net.httpserver.HttpServer;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mikoto.jpbc.mirai.plugin.httphandler.JpbcInfoHandler;
import net.mikoto.jpbc.mirai.plugin.httphandler.RegisterClientHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author mikoto
 */
public class Plugin extends JavaPlugin {
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "A Java Pixiv Bot Connectivity";
    public static final String AUTHOR = "mikoto";
    public static final String PACKAGE = "net.mikoto.jpbc.mirai.plugin";
    public static final Integer HTTP_API_PORT = 2464;

    public static final Plugin INSTANCE = new Plugin();
    private static HttpServer server;

    /**
     * 插件构造类
     */
    private Plugin() {
        super(new JvmPluginDescriptionBuilder(
                        PACKAGE,
                        VERSION
                ).author(AUTHOR).info(DESCRIPTION).build()
        );
    }

    /**
     * 启动时所执行的代码
     */
    @Override
    public void onEnable() {
        try {
            server = HttpServer.create(new InetSocketAddress(HTTP_API_PORT), 0);
            server.createContext("/JpbcInfo", new JpbcInfoHandler());
            server.createContext("/RegisterClient", new RegisterClientHandler());
            server.start();
        } catch (IOException e) {
            System.err.println("[Java Pixiv Bot Connectivity] Error: Can't create http server.");
            System.err.println("[Java Pixiv Bot Connectivity] Error: 8957 Port may in used.");
        }
    }

    /**
     * 禁用时所执行的代码
     */
    @Override
    public void onDisable() {
        server.stop(0);
    }
}
