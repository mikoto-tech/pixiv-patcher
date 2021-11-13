package net.mikoto.pixiv.controller.impl;

import com.sun.net.httpserver.HttpExchange;
import net.mikoto.pixiv.controller.ConfirmClientController;
import net.mikoto.pixiv.service.PixivController;
import net.mikoto.pixiv.view.AbstractHttpExchangeView;

import java.io.IOException;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/11/7 12:54
 */
public class ConfirmClientControllerImpl extends ConfirmClientController {
    private final AbstractHttpExchangeView httpExchangeView;

    public ConfirmClientControllerImpl(AbstractHttpExchangeView httpExchangeView) {
        this.httpExchangeView = httpExchangeView;
    }

    /**
     * Confirm client.
     *
     * @param httpExchange A http exchange object.
     * @param formData     data
     * @throws IOException error
     */
    @Override
    protected void confirmClient(HttpExchange httpExchange, Map<String, String> formData) throws IOException {
        httpExchangeView.update(httpExchange, PixivController.getInstance().getKey());
    }
}
