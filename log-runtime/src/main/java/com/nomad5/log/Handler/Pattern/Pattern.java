package com.nomad5.log.Handler.Pattern;

import android.annotation.SuppressLint;

import com.nomad5.log.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nomad5.log.Handler.PatternHandler;
import com.nomad5.log.Logger;
import com.nomad5.log.LoggerManager;

/**
 * This class responsible for formatting messages for {@link PatternHandler}.
 *
 * Pattern itself is a base class, implementations are inner classes.
 */
public abstract class Pattern
{
    // some members
    private final int count;
    private final int length;

    /**
     * Private construction
     */
    private Pattern(int count, int length)
    {
        this.count = count;
        this.length = length;
    }

    /**
     * Apply pattern
     */
    public final String apply(StackTraceElement caller, String loggerName, Logger.Level level)
    {
        String string = this.doApply(caller, loggerName, level);
        return Utils.shorten(string, this.count, this.length);
    }

    /**
     * Override this in pattern implementations
     */
    protected abstract String doApply(StackTraceElement caller, String loggerName, Logger.Level level);

    /**
     * Is caller needed for this pattern
     */
    public boolean isCallerNeeded()
    {
        return false;
    }

    /**
     * Compile string to pattern
     */
    public static Pattern compile(String pattern)
    {
        try
        {
            return pattern == null ? null : new PatternCompiler().compile(pattern);
        }
        catch(Exception e)
        {
            LoggerManager.getLogger(Logger.ROOT_LOGGER_NAME).e(e, "cannot parse pattern: '%s'", pattern);
            return new PlainPattern(0, 0, pattern);
        }

    }

    /**
     * Plain pattern helper class
     */
    public static class PlainPattern extends Pattern
    {
        private final String string;

        public PlainPattern(int count, int length, String string)
        {
            super(count, length);
            this.string = string;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            return this.string;
        }
    }

    /**
     * Date pattern
     */
    public static class DatePattern extends Pattern
    {
        private final SimpleDateFormat dateFormat;

        @SuppressLint("SimpleDateFormat")
        public DatePattern(int count, int length, String dateFormat)
        {
            super(count, length);
            if(dateFormat != null)
            {
                this.dateFormat = new SimpleDateFormat(dateFormat);
            }
            else
            {
                this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            }
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            return this.dateFormat.format(new Date());
        }
    }

    /**
     * Level pattern
     */
    public static class LevelPattern extends Pattern
    {
        public LevelPattern(int count, int length)
        {
            super(count, length);
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            return level.toString();
        }
    }

    /**
     * Logger pattern
     */
    public static class LoggerPattern extends Pattern
    {
        private int loggerCount;
        private int loggerLength;

        public LoggerPattern(int count, int length, int loggerCount, int loggerLength)
        {
            super(count, length);
            this.loggerCount = loggerCount;
            this.loggerLength = loggerLength;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            return Utils.shortenClassName(loggerName, this.loggerCount, this.loggerLength);
        }
    }

    /**
     * Caller pattern
     */
    public static class CallerPattern extends Pattern
    {
        private int callerCount;
        private int callerLength;

        public CallerPattern(int count, int length, int callerCount, int callerLength)
        {
            super(count, length);
            this.callerCount = callerCount;
            this.callerLength = callerLength;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            if(caller == null)
            {
                throw new IllegalArgumentException("Caller not found");
            }
            else
            {
                String callerString;
                if(caller.getLineNumber() < 0)
                {
                    callerString = String.format("%s#%s", caller.getClassName(), caller.getMethodName());
                }
                else
                {
                    callerString = String.format("%s#%s:%d", caller.getClassName(), caller.getMethodName(), caller.getLineNumber());
                }
                return Utils.shortenClassName(callerString, this.callerCount, this.callerLength);
            }
        }

        @Override
        public boolean isCallerNeeded()
        {
            return true;
        }
    }

    /**
     * Source pattern
     */
    public static class SourcePattern extends Pattern
    {
        public SourcePattern(int count, int length)
        {
            super(count, length);
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            if(caller == null)
            {
                throw new IllegalArgumentException("Caller not found");
            }
            else
            {
                StringBuilder builder = new StringBuilder();
                if(caller.isNativeMethod())
                {
                    builder.append("(native)");
                }
                else
                {
                    if(caller.getFileName() == null)
                    {
                        builder.append("(unknown)");
                    }
                    else
                    {
                        if(caller.getLineNumber() >= 0)
                        {
                            builder.append(String.format("%s:%d", caller.getFileName(), caller.getLineNumber()));
                        }
                        else
                        {
                            builder.append(String.format("%s", caller.getFileName()));
                        }
                    }
                }
                return builder.toString();
            }
        }

        @Override
        public boolean isCallerNeeded()
        {
            return true;
        }
    }

    /**
     * Concatenation pattern
     */
    public static class ConcatenatePattern extends Pattern
    {
        private final List<Pattern> patternList;

        public ConcatenatePattern(int count, int length, List<Pattern> patternList)
        {
            super(count, length);
            this.patternList = new ArrayList<>(patternList);
        }

        public void addPattern(Pattern pattern)
        {
            this.patternList.add(pattern);
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            StringBuilder builder = new StringBuilder();
            for(Pattern pattern : this.patternList)
            {
                builder.append(pattern.apply(caller, loggerName, level));
            }
            return builder.toString();
        }

        @Override
        public boolean isCallerNeeded()
        {
            for(Pattern pattern : this.patternList)
            {
                if(pattern.isCallerNeeded())
                {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Thread name pattern
     */
    public static class ThreadNamePattern extends Pattern
    {
        public ThreadNamePattern(int count, int length)
        {
            super(count, length);
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            return Thread.currentThread().getName();
        }
    }

    /**
     * Class pattern
     */
    public static class ClassPattern extends Pattern
    {
        private int callerCount;
        private int callerLength;

        public ClassPattern(int count, int length, int loggerCount, int loggerLength)
        {
            super(count, length);
            this.callerCount = loggerCount;
            this.callerLength = loggerLength;
        }

        @Override
        protected String doApply(StackTraceElement caller, String loggerName, Logger.Level level)
        {
            if(caller == null)
            {
                throw new IllegalArgumentException("Caller not found");
            }
            else
            {
                return Utils.shortenClassName(caller.getClassName(), this.callerCount, this.callerLength);
            }
        }

        @Override
        public boolean isCallerNeeded()
        {
            return true;
        }
    }
}


