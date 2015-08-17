package com.sxj.file.common.jpeg;

//FIXME: change to IIOException
import javax.imageio.IIOException;

public class JPEGException extends IIOException
{
    public JPEGException(String message)
    {
        super(message);
    }
}
