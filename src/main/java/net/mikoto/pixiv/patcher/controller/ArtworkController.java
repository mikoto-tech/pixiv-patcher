package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.api.pojo.Artwork;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    @Qualifier("ArtworkService")
    private final ArtworkService artworkService;

    public ArtworkController(ArtworkService artworkService) {
        this.artworkService = artworkService;
    }

    @RequestMapping(
            value = "/patch",
            method = RequestMethod.GET
    )
    public JSONObject patch(@NotNull HttpServletResponse response,
                            @RequestParam int artworkId
    ) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException, NoSuchMethodException, IllegalAccessException {
        response.setContentType("application/json;charset=UTF-8");
        JSONObject outputJsonObject = new JSONObject();
        Artwork artwork = null;
        try {
            artwork = artworkService.patchArtwork(artworkId);
            outputJsonObject.put("success", true);
        } catch (GetArtworkInformationException e) {
            outputJsonObject.put("success", false);
            outputJsonObject.put("message", "There are some wrong in getting artwork information.");
            outputJsonObject.put("rawMessage", e.getMessage());
        } catch (GetImageException e) {
            outputJsonObject.put("success", false);
            outputJsonObject.put("message", "There are some wrong in getting image.");
            outputJsonObject.put("rawMessage", e.getMessage());
        } catch (WrongSignException e) {
            outputJsonObject.put("success", false);
            outputJsonObject.put("message", "A wrong sign! Please verify your forward.");
            outputJsonObject.put("rawMessage", e.getMessage());
        }

        outputJsonObject.put("body", artwork);
        return outputJsonObject;
    }
}
