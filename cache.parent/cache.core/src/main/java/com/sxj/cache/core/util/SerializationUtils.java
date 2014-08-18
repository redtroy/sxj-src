package com.sxj.cache.core.util;

public class SerializationUtils
{
    
    public static byte[] serialize(Object obj)
    {
        return null;
    }
    
    public static Object deserialize(byte[] bytes)
    {
        return null;
    }
    //
    //	public static byte[] fstserialize(Object obj) {
    ////		ByteArrayOutputStream out = null;
    ////		FSTObjectOutput fout = null;
    ////		try {
    ////			out = new ByteArrayOutputStream();
    ////			fout = new FSTObjectOutput(out);
    ////			fout.writeObject(obj);
    ////			return out.toByteArray();
    ////		} catch (IOException e) {
    ////			throw new CacheException(e);
    ////		} finally {
    ////			Util.close(out);
    ////			Util.close(fout);
    ////		}
    //	    return null;
    //	}
    //
    //	public static Object fstdeserialize(byte[] bytes) {
    ////		FSTObjectInput in = null;
    ////		try {
    ////			in = new FSTObjectInput(new ByteArrayInputStream(bytes));
    ////			return in.readObject();
    ////		} catch (Exception e) {
    ////			throw new CacheException(e);
    ////		} finally {
    ////			Util.close(in);
    ////		}
    //	   return null;
    //	}
    //
    //	public static byte[] javaserialize(Object obj) {
    //		ObjectOutputStream oos = null;
    //		try {
    //			ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //			oos = new ObjectOutputStream(baos);
    //			oos.writeObject(obj);
    //			return baos.toByteArray();
    //		} catch (IOException e) {
    //			throw new CacheException(e);
    //		} finally {
    //			Util.close(oos);
    //		}
    //	}
    //
    //	public static Object javadeserialize(byte[] bits) {
    //		ObjectInputStream ois = null;
    //		try {
    //			ByteArrayInputStream bais = new ByteArrayInputStream(bits);
    //			ois = new ObjectInputStream(bais);
    //			return ois.readObject();
    //		} catch (Exception e) {
    //			throw new CacheException(e);
    //		} finally {
    //			Util.close(ois);
    //		}
    //	}
    
}
