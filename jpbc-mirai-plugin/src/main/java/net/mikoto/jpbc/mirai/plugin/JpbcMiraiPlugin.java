package net.mikoto.jpbc.mirai.plugin;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mikoto.jpbc.mirai.plugin.client.ClientConfirmThread;
import net.mikoto.jpbc.mirai.plugin.controller.impl.JpbcInfoControllerImpl;
import net.mikoto.jpbc.mirai.plugin.controller.impl.RegisterClientControllerImpl;
import net.mikoto.jpbc.mirai.plugin.controller.impl.RegisterQqControllerImpl;
import net.mikoto.jpbc.mirai.plugin.httpserver.HttpserverManager;
import net.mikoto.jpbc.mirai.plugin.view.impl.JpbcInfoViewImpl;
import net.mikoto.jpbc.mirai.plugin.view.impl.RegisterClientViewImpl;
import net.mikoto.jpbc.mirai.plugin.view.impl.RegisterQqViewImpl;

/**
 * @author mikoto
 */
public class JpbcMiraiPlugin extends JavaPlugin {
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "A Java Pixiv Bot Connectivity";
    public static final String AUTHOR = "mikoto";
    public static final String PACKAGE = "net.mikoto.jpbc.mirai.plugin";
    public static final Integer HTTP_API_PORT = 2464;

    public static final JpbcMiraiPlugin INSTANCE = new JpbcMiraiPlugin();

    /**
     * 插件构造类
     */
    private JpbcMiraiPlugin() {
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
        HttpserverManager
                .getInstance()
                .createContext("/JpbcInfo", new JpbcInfoControllerImpl(new JpbcInfoViewImpl()))
                .createContext("/RegisterClient", new RegisterClientControllerImpl(new RegisterClientViewImpl()))
                .createContext("/RegisterQQ", new RegisterQqControllerImpl(new RegisterQqViewImpl()))
                .start();
        ClientConfirmThread clientConfirmThread = new ClientConfirmThread();
        clientConfirmThread.start();
    }

    /**
     * 禁用时所执行的代码
     */
    @Override
    public void onDisable() {
        HttpserverManager.getInstance().stop();
    }
}
