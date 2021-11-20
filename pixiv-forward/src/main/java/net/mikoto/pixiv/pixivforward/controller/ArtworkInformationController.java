package net.mikoto.pixiv.pixivforward.controller;

import com.alibaba.fastjson.JSONObject;
import net.mikoto.pixiv.pixivforward.PixivForwardApplication;
import net.mikoto.pixiv.pixivforward.service.PixivDataService;
import net.mikoto.pixiv.pixivforward.service.UserService;
import net.mikoto.pixiv.pixivforward.service.impl.PixivDataServiceImpl;
import net.mikoto.pixiv.pixivforward.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static net.mikoto.pixiv.pixivforward.util.HttpUtil.encode;

/**
 * @author mikoto
 * Created at 17:15:44, 2021/9/20
 * Project: pixiv-forward
 */
@RestController
public class ArtworkInformationController {
    /**
     * Get artwork information.
     *
     * @param request The httpServlet request.
     * @param artworkId The id of this artwork.
     * @return Json string.
     */
    @RequestMapping(value = "/getArtworkInformation", method =
            RequestMethod.GET, produces = {
            "application/JSON"
    })
    public String artworkInformation(HttpServletRequest request,
                                     @RequestParam String artworkId,
                                     @RequestParam String key) {
        // 初始化对象实例
        JSONObject outputJson = new JSONObject();
        UserService userService = new UserServiceImpl(PixivForwardApplication.URL, PixivForwardApplication.USER_NAME, PixivForwardApplication.USER_PASSWORD);
        PixivDataService pixivDataService = new PixivDataServiceImpl();

        // 判定设备合法性
        if (userService.confirmKey(key)) {
            try {
                // 通过作品Id获取作品详细信息
                outputJson.put("body", pixivDataService.getPixivDataById(Integer.parseInt(artworkId)).toJsonObject());
                outputJson.put("mikotoAccountId", userService.getUserId());
                outputJson.put("mikotoAccountName", userService.getUserName());
                outputJson.put("error", false);
                outputJson.put("message", "");
            } catch (IOException e) {
                // 输出错误信息
                outputJson.put("error", true);
                outputJson.put("message", e.getMessage());
            }
        } else {
            // 不合法则输出错误信息
            outputJson.put("error", true);
            outputJson.put("message", "Wrong key!");
        }

        return encode(outputJson.toJSONString());
    }
}
