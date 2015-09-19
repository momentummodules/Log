package momentum.lib.log;

import android.util.Log;

/**
 * Helper for sending log messages using the standard {@link Log}.
 */
public interface Logger {

    /**
     * Case insensitive String constant used to retrieve the name of the root logger.
     */
    public static final String ROOT_LOGGER_NAME = "ROOT";//org.slf4j.Logger.ROOT_LOGGER_NAME;

    /**
     * Enumeration of priorities of log messages.
     */
    public static enum Level
    {
        VERBOSE(Log.VERBOSE),
        DEBUG(Log.DEBUG),
        INFO(Log.INFO),
        WARN(Log.WARN),
        ERROR(Log.ERROR),
        ASSERT(Log.ASSERT);

        private final int intValue;

        private Level(int intValue) {
            this.intValue = intValue;
        }
        public int intValue() {
            return this.intValue;
        }
        public boolean includes(Level level) {
            return level != null && this.intValue() <= level.intValue();
        }

    }

    /**
     * Get name of the logger
     */
    public String getName();

    /**
     * Is log level enabled?
     */
    public boolean isEnabled(Level level);

    /**
     * Is specific log level enabled?
     */
    public boolean isVerboseEnabled();
    public boolean isDebugEnabled();
    public boolean isInfoEnabled();
    public boolean isWarnEnabled();
    public boolean isErrorEnabled();
    public boolean isAssertEnabled();

    /**
     * Main printing methods
     */
    public void print(Level level, Object caller, Throwable throwable, String message);
    public void print(Level level, Object caller, Throwable throwable, String messageFormat, Object... args);

    /**
     * Logging with caller, message and throwable
     */
    public void v(Object caller, String message, Throwable throwable);
    public void d(Object caller, String message, Throwable throwable);
    public void i(Object caller, String message, Throwable throwable);
    public void w(Object caller, String message, Throwable throwable);
    public void e(Object caller, String message, Throwable throwable);
    public void a(Object caller, String message, Throwable throwable);

    /**
     * Logging with caller and throwable
     */
    public void v(Object caller, Throwable throwable);
    public void d(Object caller, Throwable throwable);
    public void i(Object caller, Throwable throwable);
    public void w(Object caller, Throwable throwable);
    public void e(Object caller, Throwable throwable);
    public void a(Object caller, Throwable throwable);

    /**
     * Logging with caller, throwable and format strig with arguments
     */
    public void v(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void d(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void i(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void w(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void e(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void a(Object caller, Throwable throwable, String messageFormat, Object... args);

    /**
     * Logging with caller, throwable and message
     */
    public void v(Object caller, Throwable throwable, String message);
    public void d(Object caller, Throwable throwable, String message);
    public void i(Object caller, Throwable throwable, String message);
    public void w(Object caller, Throwable throwable, String message);
    public void e(Object caller, Throwable throwable, String message);
    public void a(Object caller, Throwable throwable, String message);

    /**
     * Logging with caller, format string and argumets
     */
    public void v(Object caller, String messageFormat, Object... args);
    public void d(Object caller, String messageFormat, Object... args);
    public void i(Object caller, String messageFormat, Object... args);
    public void w(Object caller, String messageFormat, Object... args);
    public void e(Object caller, String messageFormat, Object... args);
    public void a(Object caller, String messageFormat, Object... args);

    /**
     * Logging with caller and message
     *
     */
    public void v(Object caller, String message);
    public void d(Object caller, String message);
    public void i(Object caller, String message);
    public void w(Object caller, String message);
    public void e(Object caller, String message);
    public void a(Object caller, String message);

}
