package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson2.JSONObject;
import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;
import net.mikoto.pixiv.patcher.manager.ConfigManager;
import net.mikoto.pixiv.patcher.manager.ForwardConnectorManager;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author mikoto
 * @date 2022/4/15 16:45
 */
@RestController
@RequestMapping(
        "/api/artwork"
)
public class ArtworkController {
    @Qualifier("artworkService")
    private final ArtworkService artworkService;
    @Qualifier("configManager")
    private final ConfigManager configManager;
    @Qualifier("forwardConnectorManager")
    private final ForwardConnectorManager forwardConnectorManager;

    public ArtworkController(ArtworkService artworkService, ConfigManager configManager, ForwardConnectorManager forwardConnectorManager) {
        this.artworkService = artworkService;
        this.configManager = configManager;
        this.forwardConnectorManager = forwardConnectorManager;
    }

    @RequestMapping(
            value = "/patch",
            method = RequestMethod.GET
    )
    public JSONObject patch(@NotNull HttpServletResponse response,
                            @RequestParam int artworkId,
                            @RequestParam String forwardConnectorName,
                            @RequestParam String configName
    ) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject outputJsonObject = new JSONObject();
        Artwork artwork = null;
        try {
            artwork = artworkService.patchArtwork(artworkId, forwardConnectorManager.getForwardConnector(forwardConnectorName), configManager.getConfig(configName));
            outputJsonObject.fluentPut("success", true);
        } catch (GetArtworkInformationException e) {
            outputJsonObject.fluentPut("success", false);
            outputJsonObject.fluentPut("message", "There are some wrong in getting artwork information.");
            outputJsonObject.fluentPut("rawMessage", e.getMessage());
        } catch (GetImageException e) {
            outputJsonObject.fluentPut("success", false);
            outputJsonObject.fluentPut("message", "There are some wrong in getting image.");
            outputJsonObject.fluentPut("rawMessage", e.getMessage());
        } catch (WrongSignException e) {
            outputJsonObject.fluentPut("success", false);
            outputJsonObject.fluentPut("message", "A wrong sign! Please verify your forward.");
            outputJsonObject.fluentPut("rawMessage", e.getMessage());
        } catch (InvocationTargetException e) {
            outputJsonObject.fluentPut("success", false);
            outputJsonObject.fluentPut("rawMessage", e.getMessage());
        }

        outputJsonObject.fluentPut("body", artwork);
        return outputJsonObject;
    }
}
