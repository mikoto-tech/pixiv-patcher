package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.database.connector.SimpleDatabaseConnector;
import net.mikoto.pixiv.patcher.Patcher;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author mikoto
 * @date 2022/5/8 5:11
 */
@RestController
@RequestMapping("/api/patcher")
public class PatcherController {
    @Qualifier("artworkService")
    private final ArtworkService artworkService;
    @Qualifier("patcher")
    private final Patcher patcher;
    @Value("${mikoto.pixiv.patcher.key}")
    private String key;

    public PatcherController(ArtworkService artworkService, Patcher patcher) {
        this.artworkService = artworkService;
        this.patcher = patcher;
        new SimpleDatabaseConnector();
    }

    @RequestMapping(
            "/start"
    )
    public JSONObject startPatcher(@NotNull HttpServletResponse response, String key) {
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();
        if (this.key.equals(key)) {
            try {
                patcher.start();
                jsonObject.fluentPut("success", true);
                jsonObject.fluentPut("msg", "Start patcher");
            } catch (AlreadyStartedException e) {
                jsonObject.fluentPut("success", false);
                jsonObject.fluentPut("msg", e.getMessage());
            }
        } else {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("msg", "Wrong key!");
        }
        return jsonObject;
    }

    @RequestMapping(
            "/stop"
    )
    public JSONObject stopPatcher(@NotNull HttpServletResponse response, String key) {
        response.setContentType("application/json;charset=UTF-8");

        JSONObject jsonObject = new JSONObject();
        if (this.key.equals(key)) {
            patcher.stop();
            jsonObject.fluentPut("success", true);
            jsonObject.fluentPut("msg", "Stop patcher");
        } else {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("msg", "Wrong key!");
        }
        return jsonObject;
    }

    @RequestMapping(
            "/init"
    )
    public JSONObject initPatcher(@NotNull HttpServletResponse response, String key) {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        if (this.key.equals(key)) {
            patcher.init();
            jsonObject.fluentPut("success", true);
            jsonObject.fluentPut("msg", "Init patcher");
        } else {
            jsonObject.fluentPut("success", false);
            jsonObject.fluentPut("msg", "Wrong key!");
        }
        return jsonObject;
    }

    @RequestMapping(
            "/get"
    )
    public JSONObject getPatcher(@NotNull HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        return (JSONObject) JSON.toJSON(patcher);
    }
}
