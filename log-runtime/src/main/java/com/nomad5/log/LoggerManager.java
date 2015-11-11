package com.nomad5.log;

import com.nomad5.log.Handler.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.nomad5.log.Handler.PatternHandler;
import com.nomad5.log.LoggerImpl.SimpleLogger;

/**
 * The logger manager.
 *
 * To configure this logger manager you can include an
 * 'log.properties' file in src/main/resources directory.
 * The format of configuration file is:
 *
 * # root logger configuration
 * root=&lt;level&gt;:&lt;tag&gt;
 * # package / class logger configuration
 * logger.&lt;package or class name&gt;=&lt;level&gt;:&lt;tag&gt;
 *
 * # Add something like this for the integrated Annotation logging
 * logger.hugo.weaving.internal.Hugo=VERBOSE:LogAnno:[%t]
 *
 * You can use values of {@link Logger.Level} enum as level constants.
 * For example, the following configuration will
 * log all ERROR messages with tag "MyApplication" and all
 * messages from classes {@code com.example.server.*} with
 * tag "MyApplication-server":
 *
 * root=ERROR:MyApplication
 * logger.com.example.server=DEBUG:MyApplication-server
 *
 */
@SuppressWarnings("unused")
public final class LoggerManager
{
    /**
     * Dummy constructor, not allowed most stuff is static
     */
    private LoggerManager()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * The default handler and default logger
     */
    private static final Handler DEFAULT_HANDLER    = new PatternHandler(Logger.Level.VERBOSE, "%logger", "%date %caller%n");
    private static final Logger DEFAULT_LOGGER      = new SimpleLogger(Logger.ROOT_LOGGER_NAME, DEFAULT_HANDLER);

    /**
     * Maximum log tag length (android max is currently 23)
     */
    public static final int MAX_LOG_TAG_LENGTH      = 23;

    /**
     * Some constants for the config file
     */
    private static final String PROPERTIES_NAME     = "log.properties";
    private static final String CONF_ROOT           = "root";
    private static final String CONF_LOGGER         = "logger.";
    private static final Pattern CONF_LOGGER_REGEX  = Pattern.compile("(.*?):(.*?)(:(.*))?");

    /**
     * The logger cache
     */
    private static final Map<String, Logger> LOGGER_CACHE = new WeakHashMap<>();

    /**
     * The handler map
     */
    private static final Map<String, Handler> HANDLER_MAP = Collections.unmodifiableMap(loadConfiguration());

    /**
     * Load the configuration
     */
    private static Map<String, Handler> loadConfiguration()
    {
        Map<String, Handler> handlerMap = new HashMap<>();
        // read properties file
        Properties properties = new Properties();
        try
        {
            loadProperties(properties);
        }
        catch(IOException e)
        {
            DEFAULT_LOGGER.e(e, "Cannot configure logger from '%s'. Default configuration will be used", PROPERTIES_NAME);
            handlerMap.put(null, DEFAULT_HANDLER);
            return handlerMap;
        }
        // something is wrong if property file is empty
        if(!properties.propertyNames().hasMoreElements())
        {
            DEFAULT_LOGGER.e(null, "Logger configuration file is empty. Default configuration will be used");
            handlerMap.put(null, DEFAULT_HANDLER);
            return handlerMap;
        }
        // parse properties to logger map
        for(Enumeration<?> names = properties.propertyNames(); names.hasMoreElements(); )
        {
            String propertyName = (String) names.nextElement();
            String propertyValue = properties.getProperty(propertyName);

            Handler handler = decodeHandler(propertyValue);
            if(handler != null)
            {
                if(propertyName.equals(CONF_ROOT))
                {
                    handlerMap.put(null, handler);
                }
                else if(propertyName.startsWith(CONF_LOGGER))
                {
                    String loggerName = propertyName.substring(CONF_LOGGER.length());
                    if(loggerName.equalsIgnoreCase(Logger.ROOT_LOGGER_NAME))
                    {
                        loggerName = null;
                    }
                    handlerMap.put(loggerName, handler);
                }
                else
                {
                    DEFAULT_LOGGER.e("unknown key '%s' in '%s' file", propertyName, PROPERTIES_NAME);
                }
            }
        }
        // logger map should have root logger (corresponding to "null" key)
        if(!handlerMap.containsKey(null))
        {
            handlerMap.put(null, DEFAULT_HANDLER);
        }
        return handlerMap;
    }

    /**
     * Load properties
     */
    private static void loadProperties(Properties properties) throws IOException
    {
        InputStream inputStream = null;
        try
        {
            inputStream = LoggerManager.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME);
            if(inputStream != null)
            {
                properties.load(inputStream);
            }
            else
            {
                inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(PROPERTIES_NAME);
                if(inputStream != null)
                {
                    properties.load(inputStream);
                }
            }
        }
        finally
        {
            if(inputStream != null)
            {
                inputStream.close();
            }
        }
    }

    /**
     * Decode the string to handler
     */
    private static Handler decodeHandler(String handlerString)
    {
        Matcher matcher = CONF_LOGGER_REGEX.matcher(handlerString);
        if(matcher.matches())
        {
            String levelString = matcher.group(1);
            String tag = matcher.group(2);
            String message = matcher.group(4);
            if(tag.length() > 23)
            {
                String trimmedTag = tag.substring(0, MAX_LOG_TAG_LENGTH);
                DEFAULT_LOGGER.w(null, "Android doesn't support tags %d characters longer. Tag '%s' will be trimmed to '%s'", MAX_LOG_TAG_LENGTH, tag, trimmedTag);
                tag = trimmedTag;
            }
            try
            {
                return new PatternHandler(Logger.Level.valueOf(levelString), tag, message);
            }
            catch(IllegalArgumentException e)
            {
                DEFAULT_LOGGER.w(null, "Cannot parse '%s' as logging level. Only %s are allowed", levelString, Arrays.toString(Logger.Level.values()));
                return null;
            }
        }
        else
        {
            DEFAULT_LOGGER.w(null, "Wrong format of logger configuration: '%s'", handlerString);
            return null;
        }
    }

    /**
     * Find log handler with specific name
     */
    private static Handler findHandler(String name)
    {
        String currentKey = null;
        if(name != null)
        {
            for(String key : HANDLER_MAP.keySet())
            {
                if(key != null && name.startsWith(key))
                {
                    // check that key corresponds to a name of sub-package
                    if(key.length() >= name.length() || name.charAt(key.length()) == '.' || name.charAt(key.length()) == '$')
                    {
                        // update current best matching key
                        if(currentKey == null || currentKey.length() < key.length())
                        {
                            currentKey = key;
                        }
                    }
                }
            }
        }
        Handler handler = HANDLER_MAP.get(currentKey);
        return handler != null ? handler : DEFAULT_HANDLER;
    }

    /**
     * Returns logger corresponding to the specified name.
     */
    public static Logger getLogger(String name)
    {
        Logger logger;
        // try to find a logger in the cache
        synchronized(LOGGER_CACHE)
        {
            logger = LOGGER_CACHE.get(name);
        }
        // load logger from configuration
        if(logger == null)
        {
            logger = new SimpleLogger(name, findHandler(name));
            synchronized(LOGGER_CACHE)
            {
                LOGGER_CACHE.put(logger.getName(), logger);
            }
        }
        // return logger
        return logger;
    }

    /**
     * Returns logger corresponding to the specified class.
     */
    public static Logger getLogger(Class<?> aClass)
    {
        return getLogger(aClass == null ? null : aClass.getName());
    }

    /**
     * Returns logger corresponding to the caller class.
     */
    public static Logger getLogger()
    {
        return getLogger(Utils.getCallerClassName(false));
    }

    /**
     * Returns logger depending on the caller object
     */
    public static Logger getLogger(Object caller)
    {
        if(caller != null)  return getLogger(Utils.getClassName(caller));
        else                return getLogger();
    }
}
