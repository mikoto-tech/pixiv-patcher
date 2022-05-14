package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.patcher.manager.ConfigManager;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author mikoto
 * @date 2022/4/15 17:26
 */
@RestController
@RequestMapping(
        "/api/config"
)
public class ConfigController {
    @Qualifier("artworkService")
    private final ArtworkService artworkService;
    @Qualifier("configManager")
    private final ConfigManager configManager;

    public ConfigController(ArtworkService artworkService, ConfigManager configManager) {
        this.artworkService = artworkService;
        this.configManager = configManager;
    }

    @RequestMapping(
            "/get"
    )
    public JSONObject getConfig(@NotNull HttpServletResponse response, String configName) {
        response.setContentType("application/json;charset=UTF-8");

        return (JSONObject) JSON.toJSON(configManager.getConfig(configName));
    }

    @RequestMapping(
            "/getAll"
    )
    public JSONArray getAllConfigs(@NotNull HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        JSONArray jsonArray = new JSONArray();

        for (String configName :
                configManager.getAllConfigNames()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.fluentPut("configName", configName);
            jsonObject.fluentPut("config", configManager.getConfig(configName));
            jsonArray.fluentAdd(jsonObject);
        }

        return jsonArray;
    }
}
