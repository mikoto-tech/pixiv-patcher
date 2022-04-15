package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.pojo.ForwardServer;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static net.mikoto.pixiv.patcher.constant.Properties.FORWARD_SERVER;
import static net.mikoto.pixiv.patcher.constant.Properties.MAIN_PROPERTIES;

/**
 * @author mikoto
 * @date 2022/4/15 17:26
 */
@RestController
@RequestMapping(
        "/api/properties"
)
public class PropertiesController {
    @Qualifier("ArtworkService")
    private final ArtworkService artworkService;

    public PropertiesController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(
            "/addForwardServer"
    )
    public JSONObject addForwardServer(@NotNull HttpServletResponse response,
                                       @RequestParam String address,
                                       @RequestParam int weight,
                                       @RequestParam String key) {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject outputJsonObject = new JSONObject();

        MAIN_PROPERTIES.setProperty(FORWARD_SERVER, MAIN_PROPERTIES.getProperty(FORWARD_SERVER) + ";" + address + "," + weight + "," + key);

        try {
            artworkService.addForwardServer(new ForwardServer(address, weight, key));
            outputJsonObject.put("success", true);
            outputJsonObject.put("message", "");
        } catch (IOException | NoSuchMethodException e) {
            outputJsonObject.put("success", false);
            outputJsonObject.put("message", "There are some wrong in adding forward server.");
            outputJsonObject.put("rawMessage", e.getMessage());
        }

        return outputJsonObject;
    }
}
