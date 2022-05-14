package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;
import net.mikoto.pixiv.patcher.manager.ConfigManager;
import net.mikoto.pixiv.patcher.manager.ForwardConnectorManager;
import net.mikoto.pixiv.patcher.manager.PatcherManager;
import net.mikoto.pixiv.patcher.model.Patcher;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * @author mikoto
 * @date 2022/5/8 5:11
 */
@RestController
@RequestMapping(
        "/api/patcher"
)
public class PatcherController {
    @Qualifier("artworkService")
    private final ArtworkService artworkService;
    @Qualifier("configManager")
    private final ConfigManager configManager;
    @Qualifier("patcherManager")
    private final PatcherManager patcherManager;
    @Qualifier("forwardConnectorManager")
    private final ForwardConnectorManager forwardConnectorManager;

    public PatcherController(ArtworkService artworkService, ConfigManager configManager, PatcherManager patcherManager, ForwardConnectorManager forwardConnectorManager) {
        this.artworkService = artworkService;
        this.configManager = configManager;
        this.patcherManager = patcherManager;
        this.forwardConnectorManager = forwardConnectorManager;
    }

    @RequestMapping(
            "/start"
    )
    public JSONObject startPatcher(@NotNull HttpServletResponse response,
                                   String patcherName) {
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();
        try {
            patcherManager.startPatcher(patcherName);
            jsonObject.fluentPut("success", true);
            jsonObject.fluentPut("msg", "Start patcher: " + patcherName);
        } catch (AlreadyStartedException e) {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("msg", e.getMessage());
        }
        return jsonObject;
    }

    @RequestMapping(
            "/stop"
    )
    public JSONObject stopPatcher(@NotNull HttpServletResponse response,
                                  String patcherName) {
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();

        patcherManager.stopPatcher(patcherName);
        jsonObject.fluentPut("success", true);
        jsonObject.fluentPut("msg", "Stop patcher: " + patcherName);
        return jsonObject;
    }

    @RequestMapping(
            "/create"
    )
    public JSONObject createPatcher(@NotNull HttpServletResponse response, String patcherName, String configName) throws IOException, NoSuchMethodException {
        response.setContentType("application/json;charset=UTF-8");
        Properties properties = configManager.getConfig(configName);
        ForwardConnector forwardConnector = forwardConnectorManager.createForwardConnector(properties);
        patcherManager.savePatcher(patcherName, new Patcher(
                properties,
                artworkService,
                forwardConnector
        ));
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("success", true);
        jsonObject.fluentPut("msg", "Create patcher: " + patcherName);
        return jsonObject;
    }

    @RequestMapping(
            "/get"
    )
    public JSONObject getPatcher(@NotNull HttpServletResponse response, String patcherName) {
        response.setContentType("application/json;charset=UTF-8");

        return (JSONObject) JSON.toJSON(patcherManager.getPatcher(patcherName));
    }

    @RequestMapping(
            "/getAll"
    )
    public JSONArray getAllPatchers(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        JSONArray jsonArray = new JSONArray();

        for (String patcherName :
                patcherManager.getAllPatcherName()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.fluentPut("patcherName", patcherName);
            jsonObject.fluentPut("patcher", patcherManager.getPatcher(patcherName));
            jsonArray.fluentAdd(jsonObject);
        }

        return jsonArray;
    }

    @RequestMapping(
            "/getThreadPool"
    )
    public JSONObject getThreadPool(@NotNull HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        return (JSONObject) JSON.toJSON(patcherManager.getThreadPool());
    }
}
