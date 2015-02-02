package com.sxj.spring.modules.util;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ClassLoaderUtil
{
    public static InputStream getResource(String configFile)
            throws FileNotFoundException
    {
        InputStream configStream = ClassLoaderUtil.class.getClassLoader()
                .getParent()
                .getResourceAsStream(configFile);
        if (configStream == null)
            configStream = ClassLoaderUtil.class.getClassLoader()
                    .getResourceAsStream(configFile);
        if (configStream == null)
            configStream = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream(configFile);
        if (configStream == null)
            throw new FileNotFoundException("Cannot find "
                    + ClassLoaderUtil.class.getClassLoader()
                            .getResource(configFile)
                            .toExternalForm() + " !!!");
        return configStream;
    }
}
