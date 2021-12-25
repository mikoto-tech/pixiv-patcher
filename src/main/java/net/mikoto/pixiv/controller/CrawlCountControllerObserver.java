package net.mikoto.pixiv.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.HttpExchange;
import net.mikoto.httpserver.controller.ControllerEntry;
import net.mikoto.pixiv.pojo.Worker;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/12/19 2:47
 */
public class CrawlCountControllerObserver implements ControllerEntry, Observer {
    private final Map<Integer, Worker> workerMap = new HashMap<>();
    private Integer total = 0;

    /**
     * Update worker.
     *
     * @param worker Worker object.
     */
    @Override
    public synchronized void update(@NotNull Worker worker) {
        if (workerMap.containsKey(worker.getWorkerId())) {
            workerMap.replace(worker.getWorkerId(), worker);
        } else {
            workerMap.put(worker.getWorkerId(), worker);
        }
        total += 1;
    }

    /**
     * Httpserver entry.
     *
     * @param httpExchange Http exchange
     * @param map          Params
     * @throws IOException Error
     */
    @Override
    public void entry(HttpExchange httpExchange, Map<String, String> map) throws IOException {
        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<Integer, Worker> entry :
                workerMap.entrySet()) {
            JSONObject workerJson = new JSONObject();
            workerJson.put("workerId", entry.getValue().getWorkerId());
            workerJson.put("start", entry.getValue().getStart());
            workerJson.put("stop", entry.getValue().getStop());
            workerJson.put("now", entry.getValue().getNow());
            jsonArray.add(workerJson);
        }

        // 指定响应头
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=UTF-8");
        // 发送响应头
        httpExchange.sendResponseHeaders(200, 0);
        // 输出
        OutputStream os = httpExchange.getResponseBody();
        JSONObject outputJson = new JSONObject();
        outputJson.put("total", total);
        outputJson.put("workers", jsonArray);
        os.write(outputJson.toString().getBytes());
        os.close();
    }
}
