package com.sxj.cache.core.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sxj.spring.modules.util.Encodes;

public class JdkSerializer implements Serializer
{
    
    @Override
    public String serialize(Object obj)
    {
        try
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            byte[] byteArray = bos.toByteArray();
            return Encodes.encodeBase64(byteArray);
        }
        catch (IOException ioe)
        {
            throw new RuntimeException(ioe);
        }
    }
    
    @Override
    public Object deserialize(String str)
    {
        try
        {
            byte[] decodeBase64 = Encodes.decodeBase64(str);
            ByteArrayInputStream bis = new ByteArrayInputStream(decodeBase64);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            throw new RuntimeException(ioe);
        }
    }
    
    public static void main(String args[])
    {
        List<String> strs = new ArrayList<String>();
        strs.add("bac");
        String serialize = new JdkSerializer().serialize(strs);
        Object deserialize = new JdkSerializer().deserialize(serialize);
        System.out.println(deserialize);
    }
    
}
