package momentum.log;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Formatter;

public class SimplePatternHandler extends PatternHandler
{
    /**
     * Creates new {@link SimplePatternHandler}.
     *
     * @param level          the level.
     * @param tagPattern     the tag pattern.
     * @param messagePattern the message pattern.
     */
    public SimplePatternHandler(Logger.Level level, String tagPattern, String messagePattern)
    {
        super(level, tagPattern, messagePattern);
    }

    @Override
    public void print(String loggerName, Logger.Level level, Object callerObject,
                      Throwable throwable, String message) throws IllegalArgumentException
    {
        if(isEnabled(level))
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
            if((compiledTagPattern != null && compiledTagPattern.isCallerNeeded())
                       || (compiledMessagePattern != null && compiledMessagePattern.isCallerNeeded()))
            {
                if(callerObject != null)
                {
                    String cal = Utils.getClassName(callerObject);
                    caller = new StackTraceElement(cal, "...", cal, -1);
                }
                else
                {
                    caller = Utils.getCaller();
                }
            }

            String tag = compiledTagPattern == null ? "" : compiledTagPattern.apply(caller, loggerName, level);
            String messageHead = compiledMessagePattern == null ? "" : compiledMessagePattern.apply(caller, loggerName, level);

            if(messageHead.length() > 0 && !Character.isWhitespace(messageHead.charAt(0)))
            {
                messageHead = messageHead + " ";
            }
            Log.println(level.intValue(), tag, messageHead + messageBody);
        }
    }

    @Override
    public void print(String loggerName, Logger.Level level, Object callerObject,
                      Throwable throwable, String messageFormat, Object... args) throws IllegalArgumentException
    {
        if(isEnabled(level))
        {
            if(messageFormat == null && args != null && args.length > 0)
            {
                throw new IllegalArgumentException("message format is not set but arguments are presented");
            }

            print(loggerName, level, callerObject, throwable, messageFormat == null ? null : String.format(messageFormat, args));
        }
    }
}
