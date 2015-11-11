package com.nomad5.log.Handler.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by mlostek on 19.09.2015.
 *
 * The pattern compiler
 */
public class PatternCompiler
{
    private String patternString;
    private int position;
    private List<Pattern.ConcatenatePattern> queue;

    //region//////////////////////////////////////////////////////////////// PATTERN REGEX
    private final java.util.regex.Pattern PERCENT_PATTERN =
            java.util.regex.Pattern.compile("%%");

    private final java.util.regex.Pattern NEWLINE_PATTERN =
            java.util.regex.Pattern.compile("%n");

    private final java.util.regex.Pattern LEVEL_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?level");

    private final java.util.regex.Pattern LOGGER_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?logger(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");

    private final java.util.regex.Pattern CALLER_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?caller(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");

    private final java.util.regex.Pattern SOURCE_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?source");

    private final java.util.regex.Pattern CLASS_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?class(\\{([+-]?\\d+)?(\\.([+-]?\\d+))?\\})?");

    private final java.util.regex.Pattern DATE_PATTERN =
            java.util.regex.Pattern.compile("%date(\\{(.*?)\\})?");

    private final java.util.regex.Pattern CONCATENATE_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?\\(");

    private final java.util.regex.Pattern THREAD_NAME_PATTERN =
            java.util.regex.Pattern.compile("%([+-]?\\d+)?(\\.([+-]?\\d+))?thread");
    //endregion////////////////////////////////////////////////////////////////////////////

    /**
     * Compile from string
     */
    public Pattern compile(String string)
    {
        if(string == null)
        {
            return null;
        }
        this.position = 0;
        this.patternString = string;
        this.queue = new ArrayList<>();
        this.queue.add(new Pattern.ConcatenatePattern(0, 0, new ArrayList<Pattern>()));

        while(string.length() > this.position)
        {
            int index = string.indexOf("%", this.position);
            int bracketIndex = string.indexOf(")", this.position);
            if(this.queue.size() > 1 && bracketIndex < index)
            {
                this.queue.get(this.queue.size() - 1).addPattern(new Pattern.PlainPattern(0, 0, string.substring(this.position, bracketIndex)));
                this.queue.get(this.queue.size() - 2).addPattern(this.queue.remove(this.queue.size() - 1));
                this.position = bracketIndex + 1;
            }
            if(index == -1)
            {
                this.queue.get(this.queue.size() - 1).addPattern(new Pattern.PlainPattern(0, 0, string.substring(this.position)));
                break;
            }
            else
            {
                this.queue.get(this.queue.size() - 1).addPattern(new Pattern.PlainPattern(0, 0, string.substring(this.position, index)));
                this.position = index;
                this.parse();
            }
        }
        return this.queue.get(0);
    }

    /**
     * Parse the pattern
     */
    private void parse()
    {
        Matcher matcher;
        if((matcher = this.findPattern(this.PERCENT_PATTERN)) != null)
        {
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.PlainPattern(0, 0, "%"));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.NEWLINE_PATTERN)) != null)
        {
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.PlainPattern(0, 0, "\n"));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.LEVEL_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.LevelPattern(count, length));
            this.position = matcher.end();
            return;
        }
        // the order is important because short logger pattern may match long caller occurrence
        if((matcher = this.findPattern(this.CALLER_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            int countCaller = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
            int lengthCaller = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.CallerPattern(count, length, countCaller, lengthCaller));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.SOURCE_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.SourcePattern(count, length));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.LOGGER_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            int countLogger = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
            int lengthLogger = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.LoggerPattern(count, length, countLogger, lengthLogger));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.CLASS_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            int countLogger = Integer.parseInt(matcher.group(5) == null ? "0" : matcher.group(5));
            int lengthLogger = Integer.parseInt(matcher.group(7) == null ? "0" : matcher.group(7));
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.ClassPattern(count, length, countLogger, lengthLogger));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.DATE_PATTERN)) != null)
        {
            String dateFormat = matcher.group(2);
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.DatePattern(0, 0, dateFormat));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.THREAD_NAME_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            this.queue.get(this.queue.size() - 1).addPattern(new Pattern.ThreadNamePattern(count, length));
            this.position = matcher.end();
            return;
        }
        if((matcher = this.findPattern(this.CONCATENATE_PATTERN)) != null)
        {
            int count = Integer.parseInt(matcher.group(1) == null ? "0" : matcher.group(1));
            int length = Integer.parseInt(matcher.group(3) == null ? "0" : matcher.group(3));
            this.queue.add(new Pattern.ConcatenatePattern(count, length, new ArrayList<Pattern>()));
            this.position = matcher.end();
            return;
        }
        throw new IllegalArgumentException();
    }

    /**
     * Find the pattern
     */
    private Matcher findPattern(java.util.regex.Pattern pattern)
    {
        Matcher matcher = pattern.matcher(this.patternString);
        return matcher.find(this.position) && matcher.start() == this.position ? matcher : null;
    }
}
