package momentum.log;

import android.util.Log;

/**
 * Helper for sending log messages using the standard {@link Log}.
 */
public interface Logger {

    /**
     * Case insensitive String constant used to retrieve the name of the root logger.
     */
    public static final String ROOT_LOGGER_NAME = org.slf4j.Logger.ROOT_LOGGER_NAME;

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
            return intValue;
        }
        public boolean includes(Level level) {
            return level != null && this.intValue() <= level.intValue();
        }

    }

    public String getName();

    public boolean isEnabled(Level level);

    public boolean isVerboseEnabled();
    public boolean isDebugEnabled();
    public boolean isInfoEnabled();
    public boolean isWarnEnabled();
    public boolean isErrorEnabled();
    public boolean isAssertEnabled();

    public void print(Level level, Object caller, Throwable throwable, String message);
    public void print(Level level, Object caller, Throwable throwable, String messageFormat, Object... args);

    public void v(Object caller, String message, Throwable throwable);
    public void d(Object caller, String message, Throwable throwable);
    public void i(Object caller, String message, Throwable throwable);
    public void w(Object caller, String message, Throwable throwable);
    public void e(Object caller, String message, Throwable throwable);
    public void a(Object caller, String message, Throwable throwable);

    public void v(Object caller, Throwable throwable);
    public void d(Object caller, Throwable throwable);
    public void i(Object caller, Throwable throwable);
    public void w(Object caller, Throwable throwable);
    public void e(Object caller, Throwable throwable);
    public void a(Object caller, Throwable throwable);

    public void v(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void d(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void i(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void w(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void e(Object caller, Throwable throwable, String messageFormat, Object... args);
    public void a(Object caller, Throwable throwable, String messageFormat, Object... args);

    public void v(Object caller, Throwable throwable, String message);
    public void d(Object caller, Throwable throwable, String message);
    public void i(Object caller, Throwable throwable, String message);
    public void w(Object caller, Throwable throwable, String message);
    public void e(Object caller, Throwable throwable, String message);
    public void a(Object caller, Throwable throwable, String message);

    public void v(Object caller, String messageFormat, Object... args);
    public void d(Object caller, String messageFormat, Object... args);
    public void i(Object caller, String messageFormat, Object... args);
    public void w(Object caller, String messageFormat, Object... args);
    public void e(Object caller, String messageFormat, Object... args);
    public void a(Object caller, String messageFormat, Object... args);

    public void v(Object caller, String message);
    public void d(Object caller, String message);
    public void i(Object caller, String message);
    public void w(Object caller, String message);
    public void e(Object caller, String message);
    public void a(Object caller, String message);

}
