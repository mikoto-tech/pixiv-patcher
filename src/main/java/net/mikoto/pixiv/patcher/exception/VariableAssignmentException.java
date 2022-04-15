package net.mikoto.pixiv.patcher.exception;

/**
 * @author mikoto
 * @date 2022/4/15 16:27
 */
public class VariableAssignmentException extends Exception {
    /**
     * Throw an exception if the variable assignment have something wrong.
     *
     * @param message The message
     */
    public VariableAssignmentException(String message) {
        super(message);
    }
}
