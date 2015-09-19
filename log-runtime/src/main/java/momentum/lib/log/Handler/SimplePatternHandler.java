package momentum.lib.log.Handler;

import android.util.Log;

import momentum.lib.log.Logger;
import momentum.lib.log.Utils;

public class SimplePatternHandler extends PatternHandler
{
    /**
     * Creates new {@link SimplePatternHandler}.
     */
    public SimplePatternHandler(Logger.Level level, String tagPattern, String messagePattern)
    {
        super(level, tagPattern, messagePattern);
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
}
