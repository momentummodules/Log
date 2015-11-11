package com.nomad5.log.LoggerImpl;

import com.nomad5.log.Handler.Handler;
import com.nomad5.log.Logger;

/**
 * Simple implementation of {@link Logger} that prints all messages
 * using {@link Handler} interface.
 */
public class SimpleLogger extends AbstractLogger
{

    private final Handler handler;

    /**
     * Creates new {@link SimpleLogger} instance.
     *
     * @param name    the name of the logger.
     * @param handler the handler to log messages.
     */
    public SimpleLogger(String name, Handler handler)
    {
        super(name);
        this.handler = handler;
    }

    @Override
    public boolean isEnabled(Level level)
    {
        return this.handler != null && this.handler.isEnabled(level);
    }

    @Override
    public void print(Level level, Object caller, Throwable throwable, String message)
    {
        if(this.handler != null)
        {
            this.handler.print(this.getName(), level, caller, throwable, message);
        }
    }

    @Override
    public void print(Level level, Object caller, Throwable throwable, String messageFormat, Object... args)
    {
        if(this.handler != null)
        {
            this.handler.print(this.getName(), level, caller, throwable, messageFormat, args);
        }
    }
}
