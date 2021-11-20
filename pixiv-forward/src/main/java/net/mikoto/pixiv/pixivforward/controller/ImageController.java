package net.mikoto.pixiv.pixivforward.controller;

import net.mikoto.pixiv.pixivforward.PixivForwardApplication;
import net.mikoto.pixiv.pixivforward.service.UserService;
import net.mikoto.pixiv.pixivforward.service.impl.UserServiceImpl;
import net.mikoto.pixiv.pixivforward.util.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author mikoto
 * Created at 22:35:40, 2021/9/20
 * Project: pixiv-forward
 */
@RestController
public class ImageController {
    public static final String IMAGE_EXCEPTION_LINK = "http://file.mikoto.net.cn/Error.jpg";
    public static final String DEVICE_EXCEPTION_LINK = "http://file.mikoto.net.cn/Error2.jpg";

    /**
     * Get image.
     *
     * @param request The httpServlet request.
     * @param url The url of this image.
     * @return Image byte.
     */
    @RequestMapping(value = "/getImage", method =
            RequestMethod.GET, produces = {
            "image/jpeg"
    })
    public byte[] getImage(HttpServletRequest request,
                           @RequestParam String url,
                           @RequestParam String key) {
        // 初始化对象实例
        UserService userService = new UserServiceImpl(PixivForwardApplication.URL, PixivForwardApplication.USER_NAME, PixivForwardApplication.USER_PASSWORD);

        // 判定设备合法性
        if (userService.confirmKey(key)) {
            // 获取图片信息
            byte[] imageData;
            try {
                imageData = HttpUtil.httpGetWithReferer(url);
            } catch (IOException e) {
                // 返回错误信息
                return HttpUtil.httpGetWithRefererUnsafe(IMAGE_EXCEPTION_LINK);
            }
            return imageData;
        } else {
            // 返回错误信息
            return HttpUtil.httpGetWithRefererUnsafe(DEVICE_EXCEPTION_LINK);
        }
    }
}
