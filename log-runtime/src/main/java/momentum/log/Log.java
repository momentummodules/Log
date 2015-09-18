package momentum.log;

/**
 * This class simplifies usage of Android Logger for debugging purposes.
 *
 * This class gets logger using {@link LoggerManager#getLogger()} and
 * delegates calls to it.
 */
public final class Log {

    private Log() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEnabled(Logger.Level level) {
        return LoggerManager.getLogger().isEnabled(level);
    }

    public static boolean isVerboseEnabled() {
        return LoggerManager.getLogger().isVerboseEnabled();
    }
    public static boolean isDebugEnabled() {
        return LoggerManager.getLogger().isDebugEnabled();
    }
    public static boolean isInfoEnabled() {
        return LoggerManager.getLogger().isInfoEnabled();
    }
    public static boolean isWarnEnabled() {
        return LoggerManager.getLogger().isWarnEnabled();
    }
    public static boolean isErrorEnabled() {
        return LoggerManager.getLogger().isErrorEnabled();
    }
    public static boolean isAssertEnabled() {
        return LoggerManager.getLogger().isAssertEnabled();
    }

    public static void print(Logger.Level level,Object caller,  Throwable throwable, String message) {
        LoggerManager.getLogger().print(level, caller, throwable, message);
    }
    public static void print(Logger.Level level, Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().print(level, caller, throwable, messageFormat, args);
    }

    public static void v(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger().v(caller, message, throwable);
    }
    public static void d(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger().d(caller, message, throwable);
    }
    public static void i(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger().i(caller, message, throwable);
    }
    public static void w(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger().w(caller, message, throwable);
    }
    public static void e(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger().e(caller, message, throwable);
    }
    public static void a(Object caller, String message, Throwable throwable) {
        LoggerManager.getLogger().a(caller, message, throwable);
    }

    public static void v(Object caller, Throwable throwable) {
        LoggerManager.getLogger().v(caller, throwable);
    }
    public static void d(Object caller, Throwable throwable) {
        LoggerManager.getLogger().d(caller, throwable);
    }
    public static void i(Object caller, Throwable throwable) {
        LoggerManager.getLogger().i(caller, throwable);
    }
    public static void w(Object caller, Throwable throwable) {
        LoggerManager.getLogger().w(caller, throwable);
    }
    public static void e(Object caller, Throwable throwable) {
        LoggerManager.getLogger().e(caller, throwable);
    }
    public static void a(Object caller, Throwable throwable) {
        LoggerManager.getLogger().a(caller, throwable);
    }

    public static void v(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().v(caller, throwable, messageFormat, args);
    }
    public static void d(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().d(caller, throwable, messageFormat, args);
    }
    public static void i(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().i(caller, throwable, messageFormat, args);
    }
    public static void w(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().w(caller, throwable, messageFormat, args);
    }
    public static void e(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().e(caller, throwable, messageFormat, args);
    }
    public static void a(Object caller, Throwable throwable, String messageFormat, Object... args) {
        LoggerManager.getLogger().a(caller, throwable, messageFormat, args);
    }

    public static void v(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger().v(caller, throwable, message);
    }
    public static void d(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger().d(caller, throwable, message);
    }
    public static void i(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger().i(caller, throwable, message);
    }
    public static void w(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger().w(caller, throwable, message);
    }
    public static void e(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger().e(caller, throwable, message);
    }
    public static void a(Object caller, Throwable throwable, String message) {
        LoggerManager.getLogger().a(caller, throwable, message);
    }

    public static void v(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger().v(caller, messageFormat, args);
    }
    public static void d(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger().d(caller, messageFormat, args);
    }
    public static void i(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger().i(caller, messageFormat, args);
    }
    public static void w(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger().w(caller, messageFormat, args);
    }
    public static void e(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger().e(caller, messageFormat, args);
    }
    public static void a(Object caller, String messageFormat, Object... args) {
        LoggerManager.getLogger().a(caller, messageFormat, args);
    }

    public static void v(Object caller, String message) {
        LoggerManager.getLogger().v(caller, message);
    }
    public static void d(Object caller, String message) {
        LoggerManager.getLogger().d(caller, message);
    }
    public static void i(Object caller, String message) {
        LoggerManager.getLogger().i(caller, message);
    }
    public static void w(Object caller, String message) {
        LoggerManager.getLogger().w(caller, message);
    }
    public static void e(Object caller, String message) {
        LoggerManager.getLogger().e(caller, message);
    }
    public static void a(Object caller, String message) {
        LoggerManager.getLogger().a(caller, message);
    }

}
