package momentum.lib.log.Handler;

import java.util.Formatter;

import momentum.lib.log.Logger;

/**
 * The log message handler interface
 */
public interface Handler {

    /**
     * Checks if the specified log level is enabled or not for this handler.
     *
     * @param level the level.
     * @return Are messages with this level allowed to be logged or not.
     */
    public boolean isEnabled(Logger.Level level);

    /**
     * Prints a log message.
     *
     * This method should automatically check using {@link #isEnabled(Logger.Level)} method
     * if the message is allowed to be logged or not.
     *
     * @param loggerName a name of a logger that user used to log message.
     * @param level      a level of the log message
     * @param throwable  a throwable object or {@code null}.
     * @param message    a log message. Can be {@code null}.
     * @throws IllegalArgumentException if no format string is specified but arguments are presented.
     */
    public void print(String loggerName,
                      Logger.Level level,
                      Object object,
                      Throwable throwable,
                      String message) throws IllegalArgumentException;

    /**
     * Prints a log message.
     *
     * This method should automatically check using {@link #isEnabled(Logger.Level)} method
     * if the message is allowed to be logged or not.
     *
     * The format string of the log message should be formatted
     * according to rules of the standard format string described
     * in JavaDoc of {@link Formatter}.
     * Implementations can use {@link Formatter#format(String, Object...)}
     * to prepare the log message from format string and array of arguments.
     *
     * @param loggerName    a name of a logger that user used to log message.
     * @param level         a level of the log message
     * @param throwable     a throwable object or {@code null}.
     * @param messageFormat a format string of the log message. Can be {@code null}.
     * @param args          an array of arguments. Can be {@code null}
     *                      which is considered as an empty array.
     * @throws IllegalArgumentException if no format string is specified but arguments are presented.
     */
    public void print(String loggerName,
                      Logger.Level level,
                      Object object,
                      Throwable throwable,
                      String messageFormat,
                      Object... args) throws IllegalArgumentException;
}
