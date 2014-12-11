package com.sxj.util.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: SxjLogger
 * @Description: TODO(日志记录类)
 * @author dujinxin
 * @date
 */
public class SxjLogger
{
    
    public final static <T> void debug(Object message, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isDebugEnabled())
            {
                logger.debug(message.toString());
            }
        }
    }
    
    public final static <T> void error(Object message, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isErrorEnabled())
            {
                logger.error(message.toString());
            }
        }
    }
    
    public final static <T> void info(Object message, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isInfoEnabled())
            {
                logger.info(message.toString());
            }
        }
    }
    
    public final static <T> void warn(Object message, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isWarnEnabled())
            {
                logger.warn(message.toString());
            }
        }
    }
    
    public final static <T> void debug(Object message, Throwable t, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isDebugEnabled())
            {
                logger.debug(message.toString(), t);
            }
        }
    }
    
    public final static <T> void error(Object message, Throwable t, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isErrorEnabled())
            {
                logger.error(message.toString(), t);
            }
        }
    }
    
    public final static <T> void info(Object message, Throwable t, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isInfoEnabled())
            {
                logger.info(message.toString(), t);
            }
        }
    }
    
    public final static <T> void warn(Object message, Throwable t, Class<T> o)
    {
        if (o != null)
        {
            Logger logger = LoggerFactory.getLogger(o.getName());
            if (logger.isWarnEnabled())
            {
                logger.warn(message.toString(), t);
            }
        }
    }
    
}
