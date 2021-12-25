package net.mikoto.pixiv.crawler;

import net.mikoto.pixiv.Main;
import net.mikoto.pixiv.controller.Observer;
import net.mikoto.pixiv.dao.PixivDataDao;
import net.mikoto.pixiv.log.Log;
import net.mikoto.pixiv.pojo.PixivData;
import net.mikoto.pixiv.pojo.Worker;
import net.mikoto.pixiv.service.PixivDataService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/12/19 4:36
 */
public class WorkerManager {
    /**
     * 单例
     */
    private static final WorkerManager INSTANCE = new WorkerManager(Integer.parseInt(Main.PROPERTIES.getProperty("START")), Integer.parseInt(Main.PROPERTIES.getProperty("STOP")), Integer.parseInt(Main.PROPERTIES.getProperty("WORKER_COUNT")));

    /**
     * Worker info
     */
    private final Integer start;
    private final Integer stop;
    private final Integer workerCount;
    private final Map<Integer, Worker> workerMap = new HashMap<>();
    private Log logger;
    private Observer observer;

    /**
     * 构建方法
     *
     * @param start       开始数
     * @param stop        停止数
     * @param workerCount 工人数
     */
    private WorkerManager(Integer start, Integer stop, Integer workerCount) {
        this.start = start;
        this.stop = stop;
        this.workerCount = workerCount;
    }

    /**
     * 获取单例
     *
     * @return WorkerManager对象
     */
    public static WorkerManager getInstance() {
        return INSTANCE;
    }

    /**
     * Init worker
     *
     * @return A map of worker
     */
    public Map<Integer, Worker> initWorker() {
        int workerLoad = (stop - start + 1) / workerCount;
        for (int i = 1; i < workerCount + 1; i++) {
            Worker worker = new Worker();
            worker.setWorkerId(i);
            worker.setStart((workerLoad * (i - 1)) + start);
            worker.setStop(workerLoad * i + (start - 1));
            worker.setNow(worker.getStart());
            observer.update(worker);
            worker.setRunnable(() -> {
                do {
                    PixivData pixivData = PixivDataService.getInstance().crawlPixivDataById(PixivDataService.getInstance().getPixivDataForwardServer(), worker.getNow());
                    if (pixivData != null) {
                        try {
                            PixivDataDao.getInstance().insertPixivData(pixivData);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        logger.log("Failed to get data(id:" + worker.getNow() + ")");
                    }
                    worker.setNow(worker.getNow() + 1);
                    observer.update(worker);
                } while (worker.getNow() <= worker.getStop());
            });
            workerMap.put(i, worker);
        }

        return workerMap;
    }

    public Map<Integer, Worker> getWorkerMap() {
        return workerMap;
    }

    public void setLogger(Log logger) {
        this.logger = logger;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}
