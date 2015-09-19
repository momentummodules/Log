package momentum.lib.log;

/**
 * Utilities for the logging stuff
 */
public final class Utils
{
    /**
     * Ignore this packages in stacktrace calculations
     */
    private static String[] ignorePackages = new String[]{  "org.aspectj.runtime.reflect",
                                                            "hugo.weaving.internal"};

    /**
     * The package name of the main logger class
     */
    private static final String PACKAGE_NAME = Logger.class.getPackage().getName();

    /**
     * Utils class also just static stuff
     */
    private Utils()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Internal class to get the caller via SecurityManager
     */
    private static final class CallerResolver extends SecurityManager
    {
        public Class<?> getCaller()
        {
            Class[] classContext = this.getClassContext();
            // sometimes class context is null (usually on new Android devices)
            if(classContext == null || classContext.length <= 0)
            {
                return null; // if class context is null or empty
            }
            boolean packageFound = false;
            for(Class aClass : classContext)
            {
                if(!packageFound)
                {
                    if(aClass.getPackage().getName().startsWith(PACKAGE_NAME))
                    {
                        packageFound = true;
                    }
                }
                else
                {
                    if(!aClass.getPackage().getName().startsWith(PACKAGE_NAME))
                    {
                        return aClass;
                    }
                }
            }
            return classContext[classContext.length - 1];
        }
    }
    private static final CallerResolver CALLER_RESOLVER = new CallerResolver();

    /**
     * Get caller via stacktrace. Set skipIgnored to true if you want to
     * ignore the packages in String[] ignorePackages
     */
    private static StackTraceElement getCallerStackTrace(boolean skipIgnored)
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if(stackTrace == null || stackTrace.length <= 0)
        {
            return null; // if stack trace is null or empty
        }

        boolean packageFound = false;
        boolean packageFoundAndSkip = false;
        for(StackTraceElement stackTraceElement : stackTrace)
        {
            // 1. find package
            if(!packageFound)
            {
                if(stackTraceElement.getClassName().startsWith(PACKAGE_NAME))
                {
                    packageFound = true;
                    continue;
                }
            }
            else
            {
                // 2. then skip package
                if(!stackTraceElement.getClassName().startsWith(PACKAGE_NAME))
                {
                    if(skipIgnored)
                    {
                        packageFoundAndSkip = true;
                    }
                    else
                    {
                        return stackTraceElement;
                    }
                }
            }
            if(packageFoundAndSkip)
            {
                // 3. finally skip ignored packages
                boolean skipThis = false;
                for(String ignoredPackage : ignorePackages)
                {
                    if(stackTraceElement.getClassName().startsWith(ignoredPackage))
                    {
                        skipThis = true;
                    }
                }
                if(!skipThis)
                {
                    return stackTraceElement;
                }
            }
        }
        return stackTrace[stackTrace.length - 1];
    }

    /**
     * Returns a name of a class that calls logging methods.
     *
     * Can be much faster than {@link #getCaller(boolean)} because
     * this method tries to use {@link SecurityManager} to get
     * caller context.
     */
    public static String getCallerClassName(boolean ignorePackages)
    {
        Class<?> caller = CALLER_RESOLVER.getCaller();
        if(caller == null)
        {
            StackTraceElement callerStackTrace = getCallerStackTrace(ignorePackages);
            return callerStackTrace == null ? null : callerStackTrace.getClassName();
        }
        else
        {
            return caller.getName();
        }
    }

    /**
     * Returns stack trace element corresponding to a class that calls
     * logging methods.
     *
     * This method compares names of the packages of stack trace elements
     * with the package of this library to find information about caller.
     *
     * @return the caller stack trace element.
     */
    public static StackTraceElement getCaller(boolean ignorePackages)
    {
        return getCallerStackTrace(ignorePackages);
    }

    /**
     * Get classname from object
     */
    public static String getClassName(Object object)
    {
        return object.getClass().getName();
    }

    /**
     * Shorten string
     *
     * @param string modified string.
     * @param count  the desired minimum length of result.
     * @param length the desired maximum of string length.
     *
     *               Example            Result
     *               %6(text)           '  text'
     *               %-6(text)          'text  '
     *               %.3(text)          'tex'
     *               %.-3(text)         'ext'
     */
    public static String shorten(String string, int count, int length)
    {
        if(string == null) return null;
        String resultString = string;
        if(Math.abs(length) < resultString.length())
        {
            if(length > 0)
                resultString = string.substring(0, length);
            if(length < 0)
                resultString = string.substring(string.length() + length, string.length());
        }
        if(Math.abs(count) > resultString.length())
        {
            return String.format("%" + count + "s", resultString);
        }
        return resultString;
    }

    /**
     * Shortens class name till the specified length.
     *
     * Note that only packages can be shortened so this method returns at least simple class name.
     *
     * @param className the class name.
     * @param maxLength the desired maximum length of result.
     * @param count     the desired maximum count of packages
     * @return the shortened class name.
     */
    // todo optimize it
    public static String shortenClassName(String className, int count, int maxLength)
    {
        className = shortenPackagesName(className, count);
        if(className == null) return null;
        if(maxLength == 0) return className;
        if(maxLength > className.length()) return className;
        if(maxLength < 0)
        {
            maxLength = -maxLength;
            StringBuilder builder = new StringBuilder();
            for(int index = className.length() - 1; index > 0; )
            {
                int i = className.lastIndexOf('.', index);

                if(i == -1)
                {
                    if(builder.length() > 0
                               && builder.length() + index + 1 > maxLength)
                    {
                        builder.insert(0, '*');
                        break;
                    }

                    builder.insert(0, className.substring(0, index + 1));
                }
                else
                {
                    if(builder.length() > 0
                               && builder.length() + (index + 1 - i) + 1 > maxLength)
                    {
                        builder.insert(0, '*');
                        break;
                    }

                    builder.insert(0, className.substring(i, index + 1));
                }

                index = i - 1;
            }
            return builder.toString();
        }
        else
        {
            StringBuilder builder = new StringBuilder();
            for(int index = 0; index < className.length(); )
            {
                int i = className.indexOf('.', index);

                if(i == -1)
                {
                    if(builder.length() > 0)
                    {
                        builder.insert(builder.length(), '*');
                        break;
                    }
                    builder.insert(builder.length(), className.substring(index, className.length()));
                    break;
                }
                else
                {
                    if(builder.length() > 0 && i + 1 > maxLength)
                    {
                        builder.insert(builder.length(), '*');
                        break;
                    }
                    builder.insert(builder.length(), className.substring(index, i + 1));
                }
                index = i + 1;
            }
            return builder.toString();
        }
    }

    // todo optimize it
    private static String shortenPackagesName(String className, int count)
    {
        if(className == null) return null;
        if(count == 0) return className;
        StringBuilder builder = new StringBuilder();
        if(count > 0)
        {
            int points = 1;
            for(int index = 0; index < className.length(); )
            {
                int i = className.indexOf('.', index);

                if(i == -1)
                {
                    builder.insert(builder.length(), className.substring(index, className.length()));
                    break;

                }
                else
                {
                    if(points == count)
                    {
                        builder.insert(builder.length(), className.substring(index, i));
                        break;
                    }
                    builder.insert(builder.length(), className.substring(index, i + 1));
                }
                index = i + 1;
                points++;
            }
        }
        else if(count < 0)
        {
            String exceptString = shortenPackagesName(className, -count);
            if(className.equals(exceptString))
            {
                int from = className.lastIndexOf('.') + 1;
                int to = className.length();
                builder.insert(builder.length(), className.substring(from, to));
            }
            else
            {
                return className.replaceFirst(exceptString + '.', "");
            }
        }
        return builder.toString();
    }
}
