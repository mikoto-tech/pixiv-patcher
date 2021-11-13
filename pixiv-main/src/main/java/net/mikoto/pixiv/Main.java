package net.mikoto.pixiv;

import net.mikoto.pixiv.controller.impl.ConfirmClientControllerImpl;
import net.mikoto.pixiv.httpserver.HttpserverManager;
import net.mikoto.pixiv.service.PixivController;
import net.mikoto.pixiv.view.impl.ConfirmClientViewImpl;

/**
 * @author mikoto
 * @date 2021/11/7 12:41
 */
public class Main {
    public static final String VERSION = "1.0.0";
    public static final String DESCRIPTION = "Pixiv main project.";
    public static final String AUTHOR = "mikoto";
    public static final String PACKAGE = "net.mikoto.pixiv";
    public static final Integer HTTP_API_PORT = 2465;

    public static void main(String[] args) {
        HttpserverManager
                .getInstance()
                .createContext("/ConfirmClient", new ConfirmClientControllerImpl(new ConfirmClientViewImpl()))
                .start();
        PixivController.getInstance().registerClient();
    }
}
