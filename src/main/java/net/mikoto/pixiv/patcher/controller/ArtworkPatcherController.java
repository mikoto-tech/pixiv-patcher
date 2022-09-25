package net.mikoto.pixiv.patcher.controller;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.mikoto.pixiv.core.connector.CentralConnector;
import net.mikoto.pixiv.core.connector.DatabaseConnector;
import net.mikoto.pixiv.core.connector.DirectConnector;
import net.mikoto.pixiv.core.connector.ForwardConnector;
import net.mikoto.pixiv.patcher.dao.ArtworkRepository;
import net.mikoto.pixiv.patcher.model.*;
import net.mikoto.pixiv.patcher.service.ArtworkPatcherService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

@RequestMapping("/patcher")
public class ArtworkPatcherController {
    /**
     * Instances.
     */
    @Qualifier("artworkPatcherService")
    private final ArtworkPatcherService artworkPatcherService;
    @Qualifier("forwardConnector")
    private final ForwardConnector forwardConnector;
    @Qualifier("directConnector")
    private final DirectConnector directConnector;
    @Qualifier("databaseConnector")
    private final DatabaseConnector databaseConnector;
    @Qualifier("centralConnector")
    private final CentralConnector centralConnector;
    private final ArtworkRepository artworkRepository;
    private final PatcherConfig patcherConfig;
    private ThreadPoolExecutor threadPoolExecutor;
    private List<Queue<Integer>> artworkTasks;
    private boolean startFlag = false;
    private boolean initFlag = false;

    public ArtworkPatcherController(ArtworkPatcherService artworkPatcherService, ForwardConnector forwardConnector, DirectConnector directConnector, DatabaseConnector databaseConnector, CentralConnector centralConnector, ArtworkRepository artworkRepository, PatcherConfig patcherConfig) {
        this.artworkPatcherService = artworkPatcherService;
        this.forwardConnector = forwardConnector;
        this.directConnector = directConnector;
        this.databaseConnector = databaseConnector;
        this.centralConnector = centralConnector;
        this.artworkRepository = artworkRepository;
        this.patcherConfig = patcherConfig;
    }

    @RequestMapping(
            "/init"
    )
    public JSONObject initPatcher(String token) {
        JSONObject outputJsonObject = new JSONObject();

        if (!initFlag) {
            initFlag = true;
            threadPoolExecutor = new ThreadPoolExecutor(
                    patcherConfig.getCorePoolSize(),
                    patcherConfig.getMaximumPoolSize(),
                    patcherConfig.getKeepAliveTime(),
                    patcherConfig.getTimeUnit(),
                    new LinkedBlockingQueue<>(),
                    new ThreadFactoryBuilder().setNameFormat(patcherConfig.getNameFormat()).build(),
                    new ThreadPoolExecutor.AbortPolicy()
            );

            // init thread.
            // calc the most threads' load.
            int totalArtworkCount = patcherConfig.getTargetArtworkId() - patcherConfig.getBeginningArtworkId() + 1;
            /**
             * Variables.
             */
            int threadCount = patcherConfig.getThreadCount();
            int load = totalArtworkCount / threadCount;
            while (load == 0) {
                threadCount -= 1;
                load = totalArtworkCount / threadCount;
            }

            artworkTasks = new ArrayList<>();

            // add artwork id to artwork tasks.
            int currentArtwork = patcherConfig.getBeginningArtworkId();
            for (int i = 0; i < threadCount; i++) {
                Queue<Integer> queue = new LinkedList<>();

                for (int j = 0; j < load; j++) {
                    queue.offer(currentArtwork);
                    currentArtwork++;
                }
                artworkTasks.add(i, queue);
            }

            // check if there are still artworks in target
            if (patcherConfig.getTargetArtworkId() % load != 0) {
                Queue<Integer> queue = new LinkedList<>();

                for (; currentArtwork <= patcherConfig.getTargetArtworkId(); currentArtwork++) {
                    queue.offer(currentArtwork);
                }
                artworkTasks.add(threadCount, queue);
            }

            List<String> checkTokenResult = centralConnector.checkToken(token, "insert_artwork");
            outputJsonObject.put("success", true);
            outputJsonObject.put("msg", "Your patcher successfully initialize!");
            outputJsonObject.put("artworkTasks", artworkTasks);
            if (checkTokenResult.contains("insert_artwork")) {
                outputJsonObject.put("msg", "Warning! Your token is unauthorised.(scope: insert_artwork)");
            }
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("msg", "Patcher has already initialized.");
        }
        return outputJsonObject;
    }

    @RequestMapping(
            "/start"
    )
    public JSONObject startPatcher(String token) {
        JSONObject outputJsonObject = new JSONObject();
        // 是否初始化
        if (initFlag) {
            // 是否已启动
            if (!startFlag) {
                startFlag = true;
                // 遍历任务列表
                for (Queue<Integer> queue :
                        artworkTasks) {
                    threadPoolExecutor.execute(() -> {
                        ArtworkCache artworkCache = new ArtworkCache(patcherConfig.getCacheSize());
                        for (Integer artworkId :
                                queue) {
                            if (!startFlag) {
                                break;
                            }

                            // 遍历所有Storage
                            for (Storage storage :
                                    patcherConfig.getUsingStorage()) {
                                // 数据库
                                if (storage == Storage.database) {
                                    // 判断cache是否满 是则存储到数据库
                                    if (artworkCache.isFull()) {
                                        if (patcherConfig.getDatabaseType() == DatabaseType.pixiv) {
                                            databaseConnector.insertArtworks(token, artworkCache.getTargets());
                                        } else {
                                            artworkRepository.saveAllAndFlush(artworkCache.getTargets());
                                        }
                                    }

                                    try {
                                        if (patcherConfig.getUsingSource() == Source.forward) {
                                            artworkPatcherService.patch(artworkId, forwardConnector, artworkCache);
                                        } else {
                                            artworkPatcherService.patch(artworkId, directConnector, artworkCache);
                                        }
                                        Thread.sleep(500);
                                        break;
                                    } catch (InvocationTargetException | IOException | InterruptedException |
                                             NoSuchMethodException | IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                    // 本地
                                } else if (storage == Storage.local) {
                                    try {
                                        if (patcherConfig.getUsingSource() == Source.forward) {
                                            artworkPatcherService.patch(artworkId, forwardConnector, null);
                                        } else {
                                            artworkPatcherService.patch(artworkId, directConnector, null);
                                        }
                                        Thread.sleep(500);
                                        break;
                                    } catch (InvocationTargetException | IOException | InterruptedException |
                                             NoSuchMethodException | IllegalAccessException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }
                        }

                        // 结尾判定是否为空
                        if (!artworkCache.isEmpty()) {
                            try {
                                if (patcherConfig.getDatabaseType() == DatabaseType.pixiv) {
                                    databaseConnector.insertArtworks(token, artworkCache.getTargets());
                                } else {
                                    artworkRepository.saveAllAndFlush(artworkCache.getTargets());
                                }
                            } finally {
                                artworkCache.removeAll();
                            }
                        }
                    });
                }
            } else {
                outputJsonObject.put("success", false);
                outputJsonObject.put("msg", "Patcher has already started.");
            }
        } else {
            outputJsonObject.put("success", false);
            outputJsonObject.put("msg", "Patcher hasn't initialized.");
        }

        return outputJsonObject;
    }
}
