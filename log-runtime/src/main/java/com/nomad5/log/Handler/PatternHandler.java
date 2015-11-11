package com.nomad5.log.Handler;

import android.util.Log;

import com.nomad5.log.Handler.Pattern.Pattern;
import com.nomad5.log.Utils;

import java.text.SimpleDateFormat;
import java.util.Formatter;

import com.nomad5.log.Logger;

/**
 * The basic implementation of {@link Handler} interface.
 * <p>
 * This log handler is configured with a logging level, a tag and
 * a message patterns.
 * <p>
 * The logging level parameter is the minimal level of log messages printed
 * by this handler instance. The logging level can be {@code null} which
 * means no messages should be printed using this logger.
 * <p>
 * Attention: Android may set its own requirement for logging level
 * using {@link Log#isLoggable(String, int)} method. This logger doesn't take
 * it into account in {@link #isEnabled(Logger.Level)} method.
 * <p>
 * The patterns are format strings written according to a special rules described
 * below. Log messages will be formatted and printed as it is specified in
 * the tag and the message pattern. The tag pattern configures log tag used
 * to print messages. The message pattern configures a head of the message but
 * not whole message printed to log.
 * <p>
 * The tag and the message patterns are wrote according to similar rules.
 * So we will show only one pattern in further examples.
 * <p>
 * The patterns is strings that contains a set of placeholders and other
 * special marks. Each special mark should start with '%' sign. To escape
 * this sign you can double it.
 * <p>
 * The list of special marks and placeholders:
 * <p>
 * <p>
 * %%
 * Escapes special sign. Prints just one '%' instead.
 * <p>
 * <p>
 * %n
 * Prints a new line character '\n'.
 * <p>
 * <p>
 * %date{date format}
 * Prints date/time of a message. Date format
 * should be supported by {@link SimpleDateFormat}.
 * Default date format is "yyyy-MM-dd HH:mm:ss.SSS".
 * <p>
 * <p>
 * %level
 * Prints logging level of a message.
 * <p>
 * <p>
 * %logger{count.length}
 * Prints a name of the logger. The algorithm will shorten some part of
 * full logger name to the specified length. You can find examples below.
 * <p>
 * Conversion specifier	    Logger name	                                        Result
 * %logger	                com.example.android.MainActivity	                com.example.android.MainActivity
 * %logger{0}	            com.example.android.MainActivity	                com.example.android.MainActivity
 * %logger{3}	            com.example.android.MainActivity	                com.example.android
 * %logger{-1}	            com.example.android.MainActivity	                example.android.MainActivity
 * %logger{.0}	            com.example.android.MainActivity	                com.example.android.MainActivity
 * %logger{.30}	            com.example.android.MainActivity	                com.example.android.*
 * %logger{.15}	            com.example.android.MainActivity	                com.example.*
 * %logger{.-25}	        com.example.android.MainActivity	                *.android.MainActivity
 * %logger{3.-18}	        com.example.android.MainActivity	                *.example.android
 * %logger{-3.-10}	        com.example.android.MainActivity$SubClass	        MainActivity$SubClass
 * <p>
 * <p>
 * %caller{count.length}
 * Prints information about a caller class which causes the logging event.
 * Additional parameters 'count' and 'length' means the same as
 * the parameters of %logger. Examples:
 * <p>
 * Conversion specifier	    Caller	                                            Result
 * %caller	                Class com.example.android.MainActivity at line 154	com.example.android.MainActivity:154
 * %caller{-3.-15}	        Class com.example.android.MainActivity at line 154	MainActivity:154
 * <p>
 * <p>
 * %source{count.length}
 * Prints source of class which causes the logging event. Examples:
 * <p>
 * Conversion specifier	Caller	                                                Result
 * %source or %s	    Class com.example.android.MainActivity at line 154	    (MainActivity.java:154)
 * %source or %s	    Native	                                                (native)
 * %source or %s	    Unknown	                                                (unknown)
 * <p>
 * %thread{count.length}
 * Prints a name of the thread which causes the logging event.
 * <p>
 * <p>
 * %class{count.length}
 * Prints the classname of the object
 * <p>
 * <p>
 * %(...)
 * Special mark used to grouping parts of message. Format modifiers
 * (if specified) are applied on whole group. Examples:
 * <p>
 * Example	                        Result
 * [%50(%d %caller{-3.-15})]	    [          2013-07-12 19:45:26.315 MainActivity:154]
 * [%-50(%d %caller{-3.-15})]	    [2013-07-12 19:45:26.315 MainActivity:154          ]
 * <p>
 * <p>
 * After special sign '%' user can add format modifiers. The modifiers
 * is similar to standard modifiers of {@link Formatter} conversions.
 * <p>
 * Example	    Result
 * %6(text)	    '  text'
 * %-6(text)	'text  '
 * %.3(text)	'tex'
 * %.-3(text)	'ext'
 */
@SuppressWarnings("unused")
public class PatternHandler implements Handler
{
    protected final Logger.Level level;
    protected final String tagPattern;
    protected final String messagePattern;
    protected final Pattern compiledTagPattern;
    protected final Pattern compiledMessagePattern;

    /**
     * Creates new {@link PatternHandler}.
     */
    public PatternHandler(Logger.Level level, String tagPattern, String messagePattern)
    {
        this.level = level;
        this.tagPattern = tagPattern;
        this.compiledTagPattern = Pattern.compile(tagPattern);
        this.messagePattern = messagePattern;
        this.compiledMessagePattern = Pattern.compile(messagePattern);
    }

    /**
     * Returns the level.
     */
    public Logger.Level getLevel()
    {
        return this.level;
    }

    /**
     * Returns the tag messagePattern.
     */
    public String getTagPattern()
    {
        return this.tagPattern;
    }

    /**
     * Returns the message messagePattern.
     */
    public String getMessagePattern()
    {
        return this.messagePattern;
    }

    @Override
    public boolean isEnabled(Logger.Level level)
    {
        return this.level != null && level != null && this.level.includes(level);
    }

    @Override
    public void print(String loggerName,
                      Logger.Level level,
                      Object callerObject,
                      Throwable throwable,
                      String message) throws IllegalArgumentException
    {
        if(this.isEnabled(level))
        {
            String messageBody;

            if(message == null)
            {
                if(throwable == null)
                {
                    messageBody = "";
                }
                else
                {
                    messageBody = Log.getStackTraceString(throwable);
                }
            }
            else
            {
                if(throwable == null)
                {
                    messageBody = message;
                }
                else
                {
                    messageBody = message + '\n' + Log.getStackTraceString(throwable);
                }
            }

            StackTraceElement caller = null;
            if((this.compiledTagPattern != null && this.compiledTagPattern.isCallerNeeded())
                       || (this.compiledMessagePattern != null && this.compiledMessagePattern.isCallerNeeded()))
            {
                if(callerObject != null)
                {
                    String cal = Utils.getClassName(callerObject);
                    caller = new StackTraceElement(cal, "<no-method>", "<no-file>", -1);
                }
                else
                {
                    caller = Utils.getCaller(true);
                }
            }

            String tag = this.compiledTagPattern == null ? "" : this.compiledTagPattern.apply(caller, loggerName, level);
            String messageHead = this.compiledMessagePattern == null ? "" : this.compiledMessagePattern.apply(caller, loggerName, level);

            if(messageHead.length() > 0 && !Character.isWhitespace(messageHead.charAt(0)))
            {
                messageHead = messageHead + " ";
            }
            Log.println(level.intValue(), tag, messageHead + messageBody);
        }
    }

    @Override
    public void print(String loggerName,
                      Logger.Level level,
                      Object callerObject,
                      Throwable throwable,
                      String messageFormat,
                      Object... args) throws IllegalArgumentException
    {
        if(this.isEnabled(level))
        {
            if(messageFormat == null && args != null && args.length > 0)
            {
                throw new IllegalArgumentException("message format is not set but arguments are presented");
            }
            this.print(loggerName, level, callerObject, throwable, messageFormat == null ? null : String.format(messageFormat, args));
        }
    }
}
