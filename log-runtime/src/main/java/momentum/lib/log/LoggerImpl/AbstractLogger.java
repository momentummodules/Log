package momentum.lib.log.LoggerImpl;

import momentum.lib.log.Logger;

/**
 * Abstract implementation of {@link Logger} interface.
 */
public abstract class AbstractLogger implements Logger
{
    // the name of the logger
    private final String name;

    /**
     * Constructor of {@link AbstractLogger}.
     */
    public AbstractLogger(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public boolean isVerboseEnabled()
    {
        return this.isEnabled(Level.VERBOSE);
    }

    @Override
    public boolean isDebugEnabled()
    {
        return this.isEnabled(Level.DEBUG);
    }

    @Override
    public boolean isInfoEnabled()
    {
        return this.isEnabled(Level.INFO);
    }

    @Override
    public boolean isWarnEnabled()
    {
        return this.isEnabled(Level.WARN);
    }

    @Override
    public boolean isErrorEnabled()
    {
        return this.isEnabled(Level.ERROR);
    }

    @Override
    public boolean isAssertEnabled()
    {
        return this.isEnabled(Level.ASSERT);
    }

    @Override
    public void v(Object caller, String message, Throwable throwable)
    {
        this.print(Level.VERBOSE, caller, throwable, message);
    }

    @Override
    public void d(Object caller, String message, Throwable throwable)
    {
        this.print(Level.DEBUG, caller, throwable, message);
    }

    @Override
    public void i(Object caller, String message, Throwable throwable)
    {
        this.print(Level.INFO, caller, throwable, message);
    }

    @Override
    public void w(Object caller, String message, Throwable throwable)
    {
        this.print(Level.WARN, caller, throwable, message);
    }

    @Override
    public void e(Object caller, String message, Throwable throwable)
    {
        this.print(Level.ERROR, caller, throwable, message);
    }

    @Override
    public void a(Object caller, String message, Throwable throwable)
    {
        this.print(Level.ASSERT, caller, throwable, message);
    }

    @Override
    public void v(Object caller, Throwable throwable)
    {
        this.print(Level.VERBOSE, caller, throwable, null);
    }

    @Override
    public void d(Object caller, Throwable throwable)
    {
        this.print(Level.DEBUG, caller, throwable, null);
    }

    @Override
    public void i(Object caller, Throwable throwable)
    {
        this.print(Level.INFO, caller, throwable, null);
    }

    @Override
    public void w(Object caller, Throwable throwable)
    {
        this.print(Level.WARN, caller, throwable, null);
    }

    @Override
    public void e(Object caller, Throwable throwable)
    {
        this.print(Level.ERROR, caller, throwable, null);
    }

    @Override
    public void a(Object caller, Throwable throwable)
    {
        this.print(Level.ASSERT, caller, throwable, null);
    }

    @Override
    public void v(Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        this.print(Level.VERBOSE, caller, throwable, messageFormat, args);
    }

    @Override
    public void d(Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        this.print(Level.DEBUG, caller, throwable, messageFormat, args);
    }

    @Override
    public void i(Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        this.print(Level.INFO, caller, throwable, messageFormat, args);
    }

    @Override
    public void w(Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        this.print(Level.WARN, caller, throwable, messageFormat, args);
    }

    @Override
    public void e(Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        this.print(Level.ERROR, caller, throwable, messageFormat, args);
    }

    @Override
    public void a(Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        this.print(Level.ASSERT, caller, throwable, messageFormat, args);
    }

    @Override
    public void v(Object caller, Throwable throwable, String message)
    {
        this.print(Level.VERBOSE, caller, throwable, message);
    }

    @Override
    public void d(Object caller, Throwable throwable, String message)
    {
        this.print(Level.DEBUG, caller, throwable, message);
    }

    @Override
    public void i(Object caller, Throwable throwable, String message)
    {
        this.print(Level.INFO, caller, throwable, message);
    }

    @Override
    public void w(Object caller, Throwable throwable, String message)
    {
        this.print(Level.WARN, caller, throwable, message);
    }

    @Override
    public void e(Object caller, Throwable throwable, String message)
    {
        this.print(Level.ERROR, caller, throwable, message);
    }

    @Override
    public void a(Object caller, Throwable throwable, String message)
    {
        this.print(Level.ASSERT, caller, throwable, message);
    }

    @Override
    public void v(Object caller, String messageFormat, Object... args)
    {
        this.print(Level.VERBOSE, caller, null, messageFormat, args);
    }

    @Override
    public void d(Object caller, String messageFormat, Object... args)
    {
        this.print(Level.DEBUG, caller, null, messageFormat, args);
    }

    @Override
    public void i(Object caller, String messageFormat, Object... args)
    {
        this.print(Level.INFO, caller, null, messageFormat, args);
    }

    @Override
    public void w(Object caller, String messageFormat, Object... args)
    {
        this.print(Level.WARN, caller, null, messageFormat, args);
    }

    @Override
    public void e(Object caller, String messageFormat, Object... args)
    {
        this.print(Level.ERROR, caller, null, messageFormat, args);
    }

    @Override
    public void a(Object caller, String messageFormat, Object... args)
    {
        this.print(Level.ASSERT, caller, null, messageFormat, args);
    }

    @Override
    public void v(Object caller, String message)
    {
        this.print(Level.VERBOSE, caller, null, message);
    }

    @Override
    public void d(Object caller, String message)
    {
        this.print(Level.DEBUG, caller, null, message);
    }

    @Override
    public void i(Object caller, String message)
    {
        this.print(Level.INFO, caller, null, message);
    }

    @Override
    public void w(Object caller, String message)
    {
        this.print(Level.WARN, caller, null, message);
    }

    @Override
    public void e(Object caller, String message)
    {
        this.print(Level.ERROR, caller, null, message);
    }

    @Override
    public void a(Object caller, String message)
    {
        this.print(Level.ASSERT, caller, null, message);
    }

}
