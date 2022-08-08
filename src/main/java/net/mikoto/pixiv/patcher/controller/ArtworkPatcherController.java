package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.mikoto.pixiv.patcher.service.ArtworkPatcherService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RequestMapping("/patcher")
public class ArtworkPatcherController {
    /**
     * Instances.
     */
    @Qualifier("artworkPatcherService")
    private final ArtworkPatcherService artworkPatcherService;
    private ThreadPoolExecutor threadPoolExecutor;
    /**
     * Variables.
     */
    @Value("${mikoto.pixiv.patcher.threadCount}")
    private int threadCount;
    @Value("${mikoto.pixiv.patcher.beginningArtworkId}")
    private int beginningArtworkId;
    @Value("${mikoto.pixiv.patcher.targetArtworkId}")
    private int targetArtworkId;
    @Value("${mikoto.pixiv.patcher.cacheSize}")
    private int cacheSize;
    @Value("${mikoto.pixiv.patcher.corePoolSize}")
    private int corePoolSize;
    @Value("${mikoto.pixiv.patcher.maximumPoolSize}")
    private int maximumPoolSize;
    @Value("${mikoto.pixiv.patcher.keepAliveTime}")
    private int keepAliveTime;
    @Value("${mikoto.pixiv.patcher.timeUnit}")
    private TimeUnit timeUnit;
    @Value("${mikoto.pixiv.patcher.nameFormat}")
    private String nameFormat;
    private boolean startFlag = false;

    public ArtworkPatcherController(ArtworkPatcherService artworkPatcherService) {
        this.artworkPatcherService = artworkPatcherService;
    }

    @RequestMapping(
            "/start"
    )
    public JSONObject startPatcher(String token) {
        JSONObject outputJsonObject = new JSONObject();
        if (threadPoolExecutor != null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    timeUnit,
                    new LinkedBlockingQueue<>(),
                    new ThreadFactoryBuilder().setNameFormat(nameFormat).build(),
                    new ThreadPoolExecutor.AbortPolicy()
            );
        }
        if (!startFlag) {
            startFlag = true;
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("msg", "Patcher has already started.");
        }

        return outputJsonObject;
    }
}
