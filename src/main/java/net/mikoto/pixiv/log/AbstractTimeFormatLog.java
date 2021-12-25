package net.mikoto.pixiv.log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mikoto
 * @date 2021/12/11 21:52
 */
public abstract class AbstractTimeFormatLog implements Log {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * Log out.
     *
     * @param text Log data
     */
    @Override
    public void log(String text) {
        print("[" + SIMPLE_DATE_FORMAT.format(new Date()) + "] INFO :" + text);
    }

    /**
     * Print data.
     *
     * @param data Input data.
     */
    protected abstract void print(String data);
}
