package net.mikoto.pixiv.patcher;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.mikoto.pixiv.api.model.ForwardServer;
import net.mikoto.pixiv.database.connector.DatabaseConnector;
import net.mikoto.pixiv.forward.connector.ForwardConnector;
import net.mikoto.pixiv.forward.connector.exception.GetArtworkInformationException;
import net.mikoto.pixiv.forward.connector.exception.GetImageException;
import net.mikoto.pixiv.forward.connector.exception.NoSuchArtworkException;
import net.mikoto.pixiv.patcher.exception.AlreadyStartedException;
import net.mikoto.pixiv.patcher.model.ArtworkCache;
import net.mikoto.pixiv.patcher.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mikoto
 * @date 2022/5/8 3:01
 */
@Component("patcher")
public class Patcher {
    @Qualifier("artworkService")
    private final ArtworkService artworkService;
    @Qualifier("databaseConnector")
    private final DatabaseConnector databaseConnector;
    @Qualifier("forwardConnector")
    private ForwardConnector forwardConnector;
    private boolean start = false;
    private boolean init = false;
    private ThreadPoolExecutor threadPoolExecutor;
    private List<Queue<Integer>> artworkTasks;

    /**
     * Properties
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
    @Value("${mikoto.pixiv.patcher.forwardServers}")
    private String forwardServers;
    @Value("${mikoto.pixiv.patcher.database.address}")
    private String databaseAddress;
    @Value("${mikoto.pixiv.patcher.database.key}")
    private String databaseKey;

    @Autowired
    public Patcher(ArtworkService artworkService, ForwardConnector forwardConnector, DatabaseConnector databaseConnector) {
        this.artworkService = artworkService;
        this.forwardConnector = forwardConnector;
        this.databaseConnector = databaseConnector;
    }

    public void stop() {
        start = false;
    }

    public void init() {
        if (!init) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    timeUnit,
                    new LinkedBlockingQueue<>(),
                    new ThreadFactoryBuilder().setNameFormat(nameFormat).build(),
                    new ThreadPoolExecutor.AbortPolicy()
            );

            for (String forwardServer :
                    forwardServers.split(";")) {
                String[] forwardServerConfig = forwardServer.split(",");
                forwardConnector.addForwardServer(new ForwardServer(forwardServerConfig[0], Integer.parseInt(forwardServerConfig[1]), forwardServerConfig[2]));
            }

            databaseConnector.setDefaultDatabaseAddress(databaseAddress);
            databaseConnector.setDefaultDatabaseKey(databaseKey);

            init = true;
        }
    }

    public void start() throws AlreadyStartedException {
        if (!start && init) {
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
                threadPoolExecutor.execute(() -> {
                    ArtworkCache artworkCache = new ArtworkCache(cacheSize);
                    for (Integer artworkId :
                            queue) {
                        if (!start) {
                            break;
                        }

                        if (artworkCache.isFull()) {
                            try {
                                databaseConnector.insertArtworks(null, null, artworkCache.getArtworks());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } finally {
                                artworkCache.removeAll();
                            }
                        }

                        while (true) {
                            try {
                                artworkService.patchArtwork(artworkId, artworkCache, forwardConnector);
                                Thread.sleep(500);
                                break;
                            } catch (NoSuchArtworkException e) {
                                break;
                            } catch (GetImageException | GetArtworkInformationException | IOException |
                                     InterruptedException | NoSuchMethodException | InvocationTargetException |
                                     IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    if (!artworkCache.isEmpty()) {
                        try {
                            databaseConnector.insertArtworks(null, null, artworkCache.getArtworks());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            artworkCache.removeAll();
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
}
