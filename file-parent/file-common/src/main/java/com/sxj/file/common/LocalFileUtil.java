/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sxj.file.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sxj.util.logger.SxjLogger;

/**
 * 文件操作类
 * 
 * @author Administrator
 */
public class LocalFileUtil
{
    
    /**
     * 获取文件扩展名
     * 
     * @param fileName
     * @return
     */
    public static String getFileExtName(String fileName)
    {
        String[] fileNames = fileName.split("\\p{Punct}");
        String file_ext_name = fileNames[fileNames.length - 1];
        return file_ext_name;
    }
    
    /**
     * 写文件
     * 
     * @param fileName
     * @param context
     * @param encoding
     * @return
     */
    public static boolean writeText(String fileName, String context,
            String encoding)
    {
        try
        {
            byte[] bs = context.getBytes(encoding);
            writeByte(fileName, bs);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * 按字节读取文件
     * 
     * @param fileName
     * @return
     */
    public static byte[] readByte(String fileName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            byte[] r = new byte[fis.available()];
            fis.read(r);
            fis.close();
            return r;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 按字节读取文件
     * 
     * @param f
     * @return
     */
    public static byte[] readByte(File f)
    {
        try
        {
            FileInputStream fis = new FileInputStream(f);
            byte[] r = readByte(fis);
            fis.close();
            return r;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 按字节读取文件
     * 
     * @param is
     * @return
     */
    public static byte[] readByte(InputStream is)
    {
        try
        {
            byte[] r = new byte[is.available()];
            is.read(r);
            return r;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 按字节写文件
     * 
     * @param fileName
     * @param b
     * @return
     */
    public static boolean writeByte(String fileName, byte[] b)
    {
        try
        {
            BufferedOutputStream fos = new BufferedOutputStream(
                    new FileOutputStream(fileName));
            fos.write(b);
            fos.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 按字节写文件
     * 
     * @param f
     * @param b
     * @return
     */
    public static boolean writeByte(File f, byte[] b)
    {
        try
        {
            BufferedOutputStream fos = new BufferedOutputStream(
                    new FileOutputStream(f));
            fos.write(b);
            fos.close();
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 读取文件
     * 
     * @param f
     * @param encoding
     * @return
     */
    public static String readText(File f, String encoding)
    {
        try
        {
            InputStream is = new FileInputStream(f);
            String str = readText(is, encoding);
            is.close();
            return str;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 读取文件
     * 
     * @param is
     * @param encoding
     * @return
     */
    public static String readText(InputStream is, String encoding)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 读取文件
     * 
     * @param fileName
     * @param encoding
     * @return
     */
    public static String readText(String fileName, String encoding)
    {
        try
        {
            InputStream is = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    encoding));
            StringBuffer sb = new StringBuffer();
            int c = br.read();
            if ((!(encoding.equalsIgnoreCase("utf-8"))) || (c != 65279))
            {
                sb.append((char) c);
            }
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            is.close();
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 远程读取文件
     * 
     * @param urlPath
     * @param encoding
     * @return
     */
    public static String readURLText(String urlPath, String encoding)
    {
        try
        {
            URL url = new URL(urlPath);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream(), encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            in.close();
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 删除文件
     * 
     * @param path
     * @return
     */
    public static boolean delete(String path)
    {
        File file = new File(path);
        return delete(file);
    }
    
    /**
     * 删除文件
     * 
     * @param file
     * @return
     */
    public static boolean delete(File file)
    {
        if (!(file.exists()))
        {
            SxjLogger.warn("文件或文件夹不存在", LocalFileUtil.class);
            return false;
        }
        if (file.isFile())
        {
            return file.delete();
        }
        return deleteDir(file);
    }
    
    private static boolean deleteDir(File dir)
    {
        try
        {
            return ((deleteFromDir(dir)) && (dir.delete()));
        }
        catch (Exception e)
        {
            SxjLogger.warn("删除文件操作出错!", LocalFileUtil.class);
        }
        return false;
    }
    
    /**
     * 删除文件
     * 
     * @param dirPath
     * @return
     */
    public static boolean deleteFromDir(String dirPath)
    {
        File file = new File(dirPath);
        return deleteFromDir(file);
    }
    
    /**
     * 删除文件
     * 
     * @param dir
     * @return
     */
    public static boolean deleteFromDir(File dir)
    {
        if (!dir.exists())
        {
            SxjLogger.warn("文件夹不存在!", LocalFileUtil.class);
            return false;
        }
        if (!(dir.isDirectory()))
        {
            SxjLogger.warn(dir + "不是文件夹!", LocalFileUtil.class);
            return false;
        }
        File[] tempList = dir.listFiles();
        for (int i = 0; i < tempList.length; i++)
        {
            if (!(delete(tempList[i])))
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 给文件重命名
     * 
     * @param path
     * @return
     */
    public static boolean mkdir(String path)
    {
        File dir = new File(path);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        return true;
    }
    
    /**
     * 复制文件
     * 
     * @param oldPath
     * @param newPath
     * @param filter
     * @return
     */
    public static boolean copy(String oldPath, String newPath, FileFilter filter)
    {
        File oldFile = new File(oldPath);
        File[] oldFiles = oldFile.listFiles(filter);
        boolean flag = true;
        if (oldFiles != null)
        {
            for (int i = 0; i < oldFiles.length; ++i)
            {
                if (!(copy(oldFiles[i], newPath + "/" + oldFiles[i].getName())))
                {
                    flag = false;
                }
            }
        }
        return flag;
    }
    
    /**
     * 复制文件
     * 
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean copy(String oldPath, String newPath)
    {
        File oldFile = new File(oldPath);
        return copy(oldFile, newPath);
    }
    
    /**
     * 复制文件
     * 
     * @param oldFile
     * @param newPath
     * @param filter
     * @return
     */
    public static boolean copy(File oldFile, String newPath)
    {
        if (!(oldFile.exists()))
        {
            SxjLogger.warn("文件或者文件夹不存在" + oldFile, LocalFileUtil.class);
            return false;
        }
        if (oldFile.isFile())
        {
            return copyFile(oldFile, newPath);
        }
        return copyDir(oldFile, newPath);
    }
    
    private static boolean copyFile(File oldFile, String newPath)
    {
        if (!(oldFile.exists()))
        {
            SxjLogger.warn("文件不存在:" + oldFile, LocalFileUtil.class);
            return false;
        }
        if (!(oldFile.isFile()))
        {
            SxjLogger.warn(oldFile + "不是文件!", LocalFileUtil.class);
            return false;
        }
        try
        {
            int byteread = 0;
            InputStream inStream = new FileInputStream(oldFile);
            FileOutputStream fs = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((byteread = inStream.read(buffer)) != -1)
            {
                fs.write(buffer, 0, byteread);
            }
            fs.close();
            inStream.close();
        }
        catch (Exception e)
        {
            SxjLogger.warn("复制单个文件" + oldFile.getPath() + "操作出错,错误原因:"
                    + e.getMessage(),
                    LocalFileUtil.class);
            return false;
        }
        return true;
    }
    
    private static boolean copyDir(File oldDir, String newPath)
    {
        if (!(oldDir.exists()))
        {
            SxjLogger.warn("文件夹不存在:" + oldDir, LocalFileUtil.class);
            return false;
        }
        if (!(oldDir.isDirectory()))
        {
            SxjLogger.warn(oldDir + "不是文件夹!", LocalFileUtil.class);
            return false;
        }
        try
        {
            new File(newPath).mkdirs();
            File[] files = oldDir.listFiles();
            File temp = null;
            for (int i = 0; i < files.length; i++)
            {
                temp = files[i];
                if (temp.isFile())
                {
                    if (!(copyFile(temp, newPath + "/" + temp.getName())))
                    {
                        return false;
                    }
                }
                else if ((temp.isDirectory())
                        && (!(copyDir(temp, newPath + "/" + temp.getName()))))
                {
                    return false;
                }
            }
            return true;
        }
        catch (Exception e)
        {
            SxjLogger.warn("复制整个文件夹内容操作出错.错误原因:" + e.getMessage(),
                    LocalFileUtil.class);
        }
        return false;
    }
    
    /**
     * 将文件流写入自定文件
     * 
     * @param file
     * @param newPath
     * @return
     */
    public static boolean writerFile(File file, String newPath)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(newPath);
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, len);
            }
            fis.close();
            fos.close();
            return true;
        }
        catch (Exception e)
        {
            SxjLogger.warn("写入文件失败!" + e.getMessage(), LocalFileUtil.class);
        }
        return false;
    }
    
    /**
     * 移动文件
     * 
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean move(String oldPath, String newPath)
    {
        return ((copy(oldPath, newPath)) && (delete(oldPath)));
    }
    
    /**
     * 移动文件
     * 
     * @param oldFile
     * @param newPath
     * @return
     */
    public static boolean move(File oldFile, String newPath)
    {
        return ((copy(oldFile, newPath)) && (delete(oldFile)));
    }
    
    /**
     * 序列化
     * 
     * @param obj
     * @param fileName
     * @return
     */
    public static void serialize(Serializable obj, String fileName)
    {
        try
        {
            FileOutputStream f = new FileOutputStream(fileName);
            ObjectOutputStream s = new ObjectOutputStream(f);
            s.writeObject(obj);
            s.flush();
            s.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 序列化
     * 
     * @param obj
     * @return
     */
    public static byte[] serialize(Serializable obj)
    {
        try
        {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream s = new ObjectOutputStream(b);
            s.writeObject(obj);
            s.flush();
            s.close();
            return b.toByteArray();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 反序列化
     * 
     * @param fileName
     * @return
     */
    public static Object unserialize(String fileName)
    {
        try
        {
            FileInputStream in = new FileInputStream(fileName);
            ObjectInputStream s = new ObjectInputStream(in);
            Object o = s.readObject();
            s.close();
            return o;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 反序列化
     * 
     * @param bs
     * @return
     */
    public static Object unserialize(byte[] bs)
    {
        try
        {
            ByteArrayInputStream in = new ByteArrayInputStream(bs);
            ObjectInputStream s = new ObjectInputStream(in);
            Object o = s.readObject();
            s.close();
            return o;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
    * 将指定文件夹打包成zip
    * @param folder
    * @throws IOException 
    */
    public static File zipFile(String filefolder, String folder, String name)
            throws IOException
    {
        File zipFile = new File(folder + "/" + name);
        if (!new File(folder).exists())
        {
            new File(folder).mkdir();
        }
        if (zipFile.exists())
        {
            zipFile.delete();
        }
        ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(
                zipFile));
        File dir = new File(filefolder);
        File[] fs = dir.listFiles();
        byte[] buf = null;
        if (fs != null)
        {
            for (File f : fs)
            {
                zipout.putNextEntry(new ZipEntry(f.getName()));
                FileInputStream fileInputStream = new FileInputStream(f);
                buf = new byte[2048];
                BufferedInputStream origin = new BufferedInputStream(
                        fileInputStream, 2048);
                int len;
                while ((len = origin.read(buf, 0, 2048)) != -1)
                {
                    zipout.write(buf, 0, len);
                }
                zipout.flush();
                origin.close();
                fileInputStream.close();
            }
        }
        zipout.flush();
        zipout.close();
        return zipFile;
    }
    
    /**
     * 删除文件夹
     * @param folder
     */
    public static void dropFolderOrFile(File file)
    {
        if (file.isDirectory())
        {
            File[] fs = file.listFiles();
            for (File f : fs)
            {
                dropFolderOrFile(f);
            }
        }
        file.delete();
    }
    // public static byte[] mapToBytes(Mapx map) {
    // ByteArrayOutputStream bos = new ByteArrayOutputStream();
    // try {
    // Object[] ks = map.keyArray();
    // Object[] vs = map.valueArray();
    // for (int i = 0; i < map.size(); ++i) {
    // String k = String.valueOf(ks[i]);
    // Object v = vs[i];
    // if (v == null) {
    // bos.write(new byte[1]);
    // } else if (v instanceof String) {
    // bos.write(new byte[] { 1 });
    // } else if (v instanceof Long) {
    // bos.write(new byte[] { 2 });
    // } else if (v instanceof Integer) {
    // bos.write(new byte[] { 3 });
    // } else if (v instanceof Boolean) {
    // bos.write(new byte[] { 4 });
    // } else if (v instanceof Date) {
    // bos.write(new byte[] { 5 });
    // } else if (v instanceof Mapx) {
    // bos.write(new byte[] { 6 });
    // } else if (v instanceof Serializable) {
    // bos.write(new byte[] { 7 });
    // } else {
    // throw new RuntimeException("未知的数据类型:"
    // + v.getClass().getName());
    // }
    // byte[] bs = k.getBytes();
    // bos.write(NumberUtils.toBytes(bs.length));
    // bos.write(bs);
    // if (v == null) {
    // continue;
    // }
    // if (v instanceof String) {
    // bs = v.toString().getBytes();
    // bos.write(NumberUtils.toBytes(bs.length));
    // bos.write(bs);
    // } else if (v instanceof Long) {
    // bos.write(NumberUtils.toBytes(((Long) v).longValue()));
    // } else if (v instanceof Integer) {
    // bos.write(NumberUtils.toBytes(((Integer) v).intValue()));
    // } else if (v instanceof Boolean) {
    // bos.write((((Boolean) v).booleanValue()) ? 1 : 0);
    // } else if (v instanceof Date) {
    // bos.write(NumberUtils.toBytes(((Date) v).getTime()));
    // } else {
    // byte[] arr;
    // if (v instanceof Mapx) {
    // arr = mapToBytes((Mapx) v);
    // bos.write(NumberUtils.toBytes(arr.length));
    // bos.write(arr);
    // } else if (v instanceof Serializable) {
    // arr = serialize((Serializable) v);
    // bos.write(NumberUtils.toBytes(arr.length));
    // bos.write(arr);
    // }
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return bos.toByteArray();
    // }
    //
    // public static Mapx bytesToMap(byte[] arr) {
    // ByteArrayInputStream bis = new ByteArrayInputStream(arr);
    // int b = -1;
    // Mapx map = new Mapx();
    // byte[] kbs = new byte[4];
    // byte[] vbs = (byte[]) null;
    // try {
    // while ((b = bis.read()) != -1) {
    // bis.read(kbs);
    // int len = NumberUtils.toInt(kbs);
    // vbs = new byte[len];
    // bis.read(vbs);
    // String k = new String(vbs);
    // Object v = null;
    // if (b == 1) {
    // bis.read(kbs);
    // len = NumberUtils.toInt(kbs);
    // vbs = new byte[len];
    // bis.read(vbs);
    // v = new String(vbs);
    // } else if (b == 2) {
    // vbs = new byte[8];
    // bis.read(vbs);
    // v = new Long(NumberUtils.toLong(vbs));
    // } else if (b == 3) {
    // vbs = new byte[4];
    // bis.read(vbs);
    // v = new Integer(NumberUtils.toInt(vbs));
    // } else if (b == 4) {
    // int i = bis.read();
    // v = new Boolean(i == 1);
    // } else if (b == 5) {
    // vbs = new byte[8];
    // bis.read(vbs);
    // v = new Date(NumberUtils.toLong(vbs));
    // } else if (b == 6) {
    // bis.read(kbs);
    // len = NumberUtils.toInt(kbs);
    // vbs = new byte[len];
    // bis.read(vbs);
    // v = bytesToMap(vbs);
    // } else if (b == 7) {
    // bis.read(kbs);
    // len = NumberUtils.toInt(kbs);
    // vbs = new byte[len];
    // bis.read(vbs);
    // v = unserialize(vbs);
    // }
    // map.put(k, v);
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // return map;
    // }
}
