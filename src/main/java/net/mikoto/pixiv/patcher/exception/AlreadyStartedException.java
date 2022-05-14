package net.mikoto.pixiv.patcher.exception;

/**
 * @author mikoto
 * @date 2022/5/8 3:32
 */
public class AlreadyStartedException extends Exception {
    public AlreadyStartedException(String message) {
        super(message);
    }
}
