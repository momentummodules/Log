package momentum.lib.log;

/**
 * This class simplifies usage of Android Logger for debugging purposes.
 *
 * This class gets logger using {@link LoggerManager#getLogger()} and
 * delegates calls to it.
 */
@SuppressWarnings("unused")
public final class Log {

    private Log() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEnabled(Object caller, Logger.Level level) {
        return LoggerManager.getLogger(caller).isEnabled(level);
    }

    public static boolean isVerboseEnabled(Object caller) {
        return LoggerManager.getLogger(caller).isVerboseEnabled();
    }
    public static boolean isDebugEnabled(Object caller) {
        return LoggerManager.getLogger(caller).isDebugEnabled();
    }
    public static boolean isInfoEnabled(Object caller) {
        return LoggerManager.getLogger(caller).isInfoEnabled();
    }
    public static boolean isWarnEnabled(Object caller) {
        return LoggerManager.getLogger(caller).isWarnEnabled();
    }
    public static boolean isErrorEnabled(Object caller) {
        return LoggerManager.getLogger(caller).isErrorEnabled();
    }
    public static boolean isAssertEnabled(Object caller) {
        return LoggerManager.getLogger(caller).isAssertEnabled();
    }

    public static void print(Logger.Level level, Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).print(level, caller, throwable, message);
    }
    public static void print(Logger.Level level, Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).print(level, caller, throwable, messageFormat, args);
    }

    public static void v(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger(caller).v(caller, message, throwable);
    }
    public static void d(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger(caller).d(caller, message, throwable);
    }
    public static void i(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger(caller).i(caller, message, throwable);
    }
    public static void w(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger(caller).w(caller, message, throwable);
    }
    public static void e(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger(caller).e(caller, message, throwable);
    }
    public static void a(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger(caller).a(caller, message, throwable);
    }

    public static void v(Object caller, Throwable throwable) {
        LoggerManager.getLogger(caller).v(caller, throwable);
    }
    public static void d(Object caller, Throwable throwable) {
        LoggerManager.getLogger(caller).d(caller, throwable);
    }
    public static void i(Object caller, Throwable throwable) {
        LoggerManager.getLogger(caller).i(caller, throwable);
    }
    public static void w(Object caller, Throwable throwable) {
        LoggerManager.getLogger(caller).w(caller, throwable);
    }
    public static void e(Object caller, Throwable throwable) {
        LoggerManager.getLogger(caller).e(caller, throwable);
    }
    public static void a(Object caller, Throwable throwable) {
        LoggerManager.getLogger(caller).a(caller, throwable);
    }

    public static void v(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).v(caller, throwable, messageFormat, args);
    }
    public static void d(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).d(caller, throwable, messageFormat, args);
    }
    public static void i(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).i(caller, throwable, messageFormat, args);
    }
    public static void w(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).w(caller, throwable, messageFormat, args);
    }
    public static void e(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).e(caller, throwable, messageFormat, args);
    }
    public static void a(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).a(caller, throwable, messageFormat, args);
    }

    public static void v(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).v(caller, throwable, message);
    }
    public static void d(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).d(caller, throwable, message);
    }
    public static void i(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).i(caller, throwable, message);
    }
    public static void w(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).w(caller, throwable, message);
    }
    public static void e(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).e(caller, throwable, message);
    }
    public static void a(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger(caller).a(caller, throwable, message);
    }

    public static void v(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).v(caller, messageFormat, args);
    }
    public static void d(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).d(caller, messageFormat, args);
    }
    public static void i(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).i(caller, messageFormat, args);
    }
    public static void w(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).w(caller, messageFormat, args);
    }
    public static void e(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).e(caller, messageFormat, args);
    }
    public static void a(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger(caller).a(caller, messageFormat, args);
    }

    public static void v(Object caller, String message) {
        LoggerManager.getLogger(caller).v(caller, message);
    }
    public static void d(Object caller, String message) {
        LoggerManager.getLogger(caller).d(caller, message);
    }
    public static void i(Object caller, String message) {
        LoggerManager.getLogger(caller).i(caller, message);
    }
    public static void w(Object caller, String message) {
        LoggerManager.getLogger(caller).w(caller, message);
    }
    public static void e(Object caller, String message) {
        LoggerManager.getLogger(caller).e(caller, message);
    }
    public static void a(Object caller, String message) {
        LoggerManager.getLogger(caller).a(caller, message);
    }

}
