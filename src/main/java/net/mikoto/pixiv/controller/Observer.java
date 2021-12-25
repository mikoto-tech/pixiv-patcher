package net.mikoto.pixiv.controller;

import net.mikoto.pixiv.pojo.Worker;

/**
 * @author mikoto
 * @date 2021/12/19 2:47
 */
public interface Observer {
    /**
     * Update worker.
     *
     * @param worker Worker object.
     */
    void update(Worker worker);
}
