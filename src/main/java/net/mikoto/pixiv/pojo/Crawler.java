package net.mikoto.pixiv.pojo;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.mikoto.log.Logger;
import net.mikoto.pixiv.Main;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mikoto
 * @date 2022/1/1 20:02
 */
public class Crawler {
    private static final String CRAWLER = "crawler";
    private static final String TYPE = "type";
    private static final String CRAWLER_PROPERTIES = "crawlerProperties";
    private final ThreadPoolExecutor threadPoolExecutor;
    private final Logger logger;
    private final Map<Integer, Worker> workerMap = new HashMap<>();
    private final String crawlerName;
    private boolean crawlerFlag;

    public Crawler(@NotNull Properties properties, @NotNull String crawlerName, @NotNull Logger logger) {
        threadPoolExecutor = new ThreadPoolExecutor(Integer.parseInt(properties.getProperty("WORKER_COUNT")), Integer.parseInt(properties.getProperty("WORKER_COUNT")), 1, TimeUnit.HOURS, new LinkedBlockingDeque<>(), new ThreadFactoryBuilder().setNameFormat("mikoto-pixiv-" + crawlerName + "-worker-%d").build());
        this.logger = logger;
        this.crawlerName = crawlerName;
        if (CRAWLER.equals(properties.getProperty(TYPE))) {
            loadPropertiesByCrawler(properties);
        } else if (CRAWLER_PROPERTIES.equals(properties.getProperty(TYPE))) {
            loadPropertiesByCrawlerProperties(Integer.parseInt(properties.getProperty("START")), Integer.parseInt(properties.getProperty("STOP")), Integer.parseInt(properties.getProperty("WORKER_COUNT")));
        }
    }

    private void loadPropertiesByCrawlerProperties(int start, int stop, int workerCount) {
        int workerLoad = (stop - start + 1) / workerCount;
        for (int i = 1; i < workerCount + 1; i++) {
            Worker worker = new Worker();
            worker.setWorkerId(i);
            worker.setStart((workerLoad * (i - 1)) + start);
            worker.setStop(workerLoad * i + (start - 1));
            worker.setNow(worker.getStart());
            worker.setServer(Main.PIXIV_ENGINE.getPixivDataService().getPixivDataForwardServer());
            createWorkerRunnable(i, worker);
        }
    }

    private void loadPropertiesByCrawler(@NotNull Properties properties) {
        for (int i = 1; i < Integer.parseInt(properties.getProperty("WORKER_COUNT")) + 1; i++) {
            Worker worker = new Worker();
            worker.setWorkerId(i);
            worker.setStart(Integer.parseInt(properties.getProperty("WORKER_" + i + "_START")));
            worker.setStop(Integer.parseInt(properties.getProperty("WORKER_" + i + "_STOP")));
            worker.setNow(Integer.parseInt(properties.getProperty("WORKER_" + i + "_NOW")));
            worker.setServer(properties.getProperty("WORKER_" + i + "_SERVER"));
            createWorkerRunnable(i, worker);
        }
    }

    private void createWorkerRunnable(int i, @NotNull Worker worker) {
        worker.setRunnable(() -> {
            do {
                try {
                    Main.PIXIV_ENGINE.getPixivDataService().getPixivDataById(worker.getServer(), worker.getNow(), Main.PIXIV_ENGINE.getPixivDataDao());
                } catch (Exception e) {
                    logger.log("An error happens to artwork:" + worker.getNow() + ".");
                }
                worker.setNow(worker.getNow() + 1);
            } while (worker.getNow() <= worker.getStop());
        });
        workerMap.put(i, worker);
    }

    public void start() {
        if (!crawlerFlag) {
            logger.log("===================================================================================================");
            logger.log(",--.   ,--.,--.,--. ,--. ,-----. ,--------. ,-----.        ,------. ,--.,--.   ,--.,--.,--.   ,--. ");
            logger.log("|   `.'   ||  ||  .'   /'  .-.  ''--.  .--''  .-.  ',-----.|  .--. '|  | \\  `.'  / |  | \\  `.'  /  ");
            logger.log("|  |'.'|  ||  ||  .   ' |  | |  |   |  |   |  | |  |'-----'|  '--' ||  |  .'    \\  |  |  \\     /   ");
            logger.log("|  |   |  ||  ||  |\\   \\'  '-'  '   |  |   '  '-'  '       |  | --' |  | /  .'.  \\ |  |   \\   /    ");
            logger.log("`--'   `--'`--'`--' '--' `-----'    `--'    `-----'        `--'     `--''--'   '--'`--'    `-'");
            logger.log("===================================================================================================");
            crawlerFlag = true;
            for (Map.Entry<Integer, Worker> entry :
                    workerMap.entrySet()) {
                threadPoolExecutor.execute(entry.getValue().getRunnable());
            }
        }
    }

    public Map<Integer, Worker> getWorkerMap() {
        return workerMap;
    }

    public String getCrawlerName() {
        return crawlerName;
    }
}
