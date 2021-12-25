package net.mikoto.pixiv.log.impl;

import net.mikoto.pixiv.log.AbstractTimeFormatLog;

/**
 * @author mikoto
 * @date 2021/12/11 21:56
 */
public class ConsoleTimeFormatLog extends AbstractTimeFormatLog {
    /**
     * Print data.
     *
     * @param data Input data.
     */
    @Override
    protected void print(String data) {
        System.out.println(data);
    }
}
