package com.sxj.spring.modules.util;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassLoaderUtil
{
    public static InputStream getResource(String configFile)
            throws FileNotFoundException
    {
        //        InputStream configStream = ClassLoaderUtil.class.getClassLoader()
        //                .getParent()
        //                .getResourceAsStream(configFile);
        //        if (configStream == null)
        //            configStream = ClassLoaderUtil.class.getClassLoader()
        //                    .getResourceAsStream(configFile);
        //        if (configStream == null)
        //            configStream = Thread.currentThread()
        //                    .getContextClassLoader()
        //                    .getResourceAsStream(configFile);
        //        configStream = getResource(ClassLoaderUtil.class.getClassLoader(),
        //                configFile);
        InputStream configStream = getResource(ClassLoaderUtil.class.getClassLoader(),
                configFile);
        if (configStream == null)
            configStream = getResource(Thread.currentThread()
                    .getContextClassLoader(), configFile);
        if (configStream == null)
            throw new FileNotFoundException("Cannot find "
                    + ClassLoaderUtil.class.getClassLoader()
                            .getResource(configFile)
                            .toExternalForm() + " !!!");
        return configStream;
    }
    
    private static InputStream getResource(ClassLoader loader, String configFile)
    {
        InputStream input = loader.getResourceAsStream(configFile);
        if (input == null && loader.getParent() != null)
            return getResource(loader.getParent(), configFile);
        return input;
        
    }
}
