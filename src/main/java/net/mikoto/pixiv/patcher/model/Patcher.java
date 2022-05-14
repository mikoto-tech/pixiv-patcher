package net.mikoto.pixiv.patcher.model;

import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.WrongSignException;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;
import net.mikoto.pixiv.patcher.manager.ConfigManager;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @author mikoto
 * @date 2022/5/8 3:01
 */
public class Patcher {
    private final ArtworkService artworkService;
    private Properties properties;
    private ForwardConnector forwardConnector;
    private boolean start = false;
    private int beginningArtworkId;
    private int targetArtworkId;
    private ExecutorService executorService;
    private int threadCount;
    private List<Queue<Integer>> artworkTasks;

    public Patcher(@NotNull Properties properties, ArtworkService artworkService, ForwardConnector forwardConnector) {
        this.properties = properties;
        this.artworkService = artworkService;
        this.forwardConnector = forwardConnector;

        this.beginningArtworkId = Integer.parseInt(properties.getProperty(ConfigManager.BEGINNING_ARTWORK_ID));
        this.targetArtworkId = Integer.parseInt(properties.getProperty(ConfigManager.TARGET_ARTWORK_ID));
        this.threadCount = Integer.parseInt(properties.getProperty(ConfigManager.THREAD_COUNT));
    }

    public void stop() {
        start = false;
    }

    public void start() throws AlreadyStartedException {
        if (!start) {
            start = true;

            // init thread.
            int totalArtworkCount = targetArtworkId - beginningArtworkId + 1;
            int load = totalArtworkCount / threadCount;
            while (load == 0) {
                threadCount -= 1;
                load = totalArtworkCount / threadCount;
            }

            artworkTasks = new ArrayList<>();

            int currentArtwork = beginningArtworkId;
            for (int i = 0; i < threadCount; i++) {
                Queue<Integer> queue = new LinkedList<>();

                for (int j = 0; j < load; j++) {
                    queue.offer(currentArtwork);
                    currentArtwork++;
                }
                artworkTasks.add(i, queue);
            }

            if (targetArtworkId % load != 0) {
                Queue<Integer> queue = new LinkedList<>();

                for (; currentArtwork <= targetArtworkId; currentArtwork++) {
                    queue.offer(currentArtwork);
                }
                artworkTasks.add(threadCount, queue);
            }

            // Start thread
            for (Queue<Integer> queue :
                    artworkTasks) {
                executorService.execute(() -> {
                    for (Integer artworkId :
                            queue) {
                        if (!start) {
                            break;
                        }

                        while (true) {
                            try {
                                artworkService.patchArtwork(artworkId, forwardConnector, properties);
                                break;
                            } catch (GetArtworkInformationException e) {
                                if ("Get artwork failed: 該当作品は削除されたか、存在しない作品IDです。".equals(e.getMessage())) {
                                    break;
                                }
                            } catch (IOException | InvalidKeySpecException | NoSuchAlgorithmException | SignatureException | InvalidKeyException | WrongSignException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | GetImageException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

        } else {
            throw new AlreadyStartedException("Patcher has already started.");
        }
    }

    public boolean isStart() {
        return start;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) throws AlreadyStartedException {
        if (!start) {
            this.properties = properties;
        } else {
            throw new AlreadyStartedException("Patcher has already started.");
        }
    }

    public int getBeginningArtworkId() {
        return beginningArtworkId;
    }

    public void setBeginningArtworkId(int beginningArtworkId) throws AlreadyStartedException {
        if (!start) {
            this.beginningArtworkId = beginningArtworkId;
        } else {
            throw new AlreadyStartedException("Patcher has already started.");
        }
    }

    public int getTargetArtworkId() {
        return targetArtworkId;
    }

    public void setTargetArtworkId(int targetArtworkId) throws AlreadyStartedException {
        if (!start) {
            this.targetArtworkId = targetArtworkId;
        } else {
            throw new AlreadyStartedException("Patcher has already started.");
        }
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) throws AlreadyStartedException {
        if (!start) {
            this.threadCount = threadCount;
        } else {
            throw new AlreadyStartedException("Patcher has already started.");
        }
    }

    public List<Queue<Integer>> getArtworkTasks() {
        return artworkTasks;
    }

    public ForwardConnector getForwardConnector() {
        return forwardConnector;
    }

    public void setForwardConnector(ForwardConnector forwardConnector) throws AlreadyStartedException {
        if (!start) {
            this.forwardConnector = forwardConnector;
        } else {
            throw new AlreadyStartedException("Patcher has already started.");
        }
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}
