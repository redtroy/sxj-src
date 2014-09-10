package com.sxj.util.common;

import java.io.*;
import java.net.URL;

/**
 * 文件处理工具类
 *
 * @author dujinxin
 */
public class FileUtil {

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtName(String fileName) {
        String[] fileNames = fileName.split("\\p{Punct}");
        return fileNames[fileNames.length - 1];
    }

    /**
     * 按字节读取文件
     *
     * @param fileName 文件名
     * @return 字节数组
     */
    public static byte[] readBytes(String fileName) {
        return readByte(new File(fileName));
    }

    /**
     * 按字节读取文件
     *
     * @param f <code>File</code>
     * @return 字节数组
     */
    public static byte[] readByte(File f) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            return readBytes(fis);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 按字节读取文件
     * <p/>
     * <b>注意</b>：读取后不关闭输入流
     *
     * @param is <code>InputStream</code>
     * @return 字节数组
     */
    public static byte[] readBytes(InputStream is) {
        try {
            int size = is.available();
            byte[] buf = new byte[size];
            is.read(buf);
            return buf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按字节写文件
     *
     * @param fileName 文件名
     * @param b        字节数组
     * @return 写入成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean writeBytes(String fileName, byte[] b) {
        return writeBytes(new File(fileName), b);
    }

    /**
     * 按字节写文件
     *
     * @param f <code>File</code>
     * @param b 字节数组
     * @return 写入成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean writeBytes(File f, byte[] b) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(f));
            bos.write(b);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取文件内容为指定编码的字符串
     *
     * @param fileName 文件名
     * @param encoding 字符串编码
     * @return 指定编码的字符串
     */
    public static String readText(String fileName, String encoding) {
        return readText(new File(fileName), encoding);
    }

    /**
     * 读取文件内容为指定编码的字符串
     *
     * @param f        <code>File</code>
     * @param encoding 字符串编码
     * @return 指定编码的字符串
     */
    public static String readText(File f, String encoding) {
        InputStream is = null;
        try {
            is = new FileInputStream(f);
            return readText(is, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 读取文件内容为指定编码的字符串
     * <p/>
     * <b>注意</b>：读取后不关闭输入流
     *
     * @param is       <code>InputStream</code>
     * @param encoding 字符串编码
     * @return 指定编码的字符串
     */
    public static String readText(InputStream is, String encoding) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以指定编码将字符串写入文件
     *
     * @param fileName 文件名
     * @param content  字符串
     * @param encoding 字符串编码
     * @return 写入成功返回<code>true</code>；否则返回<code>false</code>
     */
    public static boolean writeText(String fileName, String content, String encoding) {
        try {
            byte[] b = content.getBytes(encoding);
            writeBytes(fileName, b);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    /**
     * 读取远程文件内容为指定编码的字符串
     *
     * @param urlPath  URL路径
     * @param encoding 字符串编码
     * @return 指定编码的字符串
     */
    public static String readURLText(String urlPath, String encoding) {
        BufferedReader br = null;
        try {
            URL url = new URL(urlPath);
            br = new BufferedReader(new InputStreamReader(url.openStream(), encoding));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param pathname 文件路径名
     * @return 删除成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean delete(String pathname) {
        File file = new File(pathname);
        return delete(file);
    }

    /**
     * 删除文件（包括目录）
     *
     * @param file <code>File</code>
     * @return 文件不存在或删除成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean delete(File file) {
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        } else if (file.isDirectory()) {
            return deleteDir(file);
        }

        return false;
    }

    private static boolean deleteDir(File dir) {
        try {
            return ((deleteFromDir(dir)) && (dir.delete()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件目录
     *
     * @param dirPath 目录路径
     * @return 目录不存在或删除成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean deleteFromDir(String dirPath) {
        File file = new File(dirPath);
        return deleteFromDir(file);
    }

    /**
     * 删除文件
     *
     * @param dir 目录类型的<code>File</code>
     * @return 目录不存在或删除成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean deleteFromDir(File dir) {
        if (!dir.exists()) {
            return true;
        }
        if (!(dir.isDirectory())) {
            return false;
        }

        File[] files = dir.listFiles();
        for (File file : files) {
            if (!(delete(file))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 创建目录
     *
     * @param path 目录路径
     * @return 目录已经存在或创建成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean mkdir(String path) {
        File dir = new File(path);
        return dir.exists() || dir.mkdir();
    }

    /**
     * 复制一个目录下的所有文件到新的目录
     * <p/>
     * 如果有一个文件复制不成功则停止复制并返回<code>false</code>
     *
     * @param oldPath 原路径
     * @param newPath 新路径
     * @param filter  文件过滤器 <code>FileFilter</code>
     * @return 全部复制成功返回<code>true</code>；否则返回<code>false</code>
     */
    public static boolean copy(String oldPath, String newPath, FileFilter filter) {
        File oldFile = new File(oldPath);
        File[] oldFiles = oldFile.listFiles(filter);
        if (oldFiles != null) {
            for (File file : oldFiles) {
                if (!(copy(file, newPath + "/" + file.getName()))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 复制一个目录下的所有文件到新的目录
     *
     * @param oldPath 原路径
     * @param newPath 新路径
     * @return 全部复制成功返回<code>true</code>；否则返回<code>false</code>
     */
    public static boolean copy(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        return copy(oldFile, newPath);
    }

    /**
     * 复制文件
     *
     * @param oldFile 原文件
     * @param newPath 新路径
     * @return 全部复制成功返回<code>true</code>；否则返回<code>false</code>
     */
    public static boolean copy(File oldFile, String newPath) {
        if (!(oldFile.exists())) {
            return false;
        }
        if (oldFile.isFile()) {
            return copyFile(oldFile, newPath);
        }
        return copyDir(oldFile, newPath);
    }

    private static boolean copyFile(File oldFile, String newPath) {
        if (!oldFile.exists() || !oldFile.isFile()) {
            return false;
        }

        InputStream in = null;
        FileOutputStream fos = null;
        try {
            int len;
            in = new FileInputStream(oldFile);
            fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private static boolean copyDir(File oldDir, String newPath) {
        if (!oldDir.exists() || !oldDir.isDirectory()) {
            return false;
        }

        try {
            if (!new File(newPath).mkdirs())
                return false;

            File[] files = oldDir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (!(copyFile(file, newPath + "/" + file.getName()))) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!(copyDir(file, newPath + "/" + file.getName()))) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将文件内容写入新文件
     *
     * @param file    <code>File</code>
     * @param newPath 新文件路径
     * @return 写入成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean writerFile(File file, String newPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(newPath);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 移动文件
     *
     * @param oldPath 原路径
     * @param newPath 新路径
     * @return 移动成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean move(String oldPath, String newPath) {
        return ((copy(oldPath, newPath)) && (delete(oldPath)));
    }

    /**
     * 移动文件
     *
     * @param oldFile 原文件
     * @param newPath 新路径
     * @return 移动成功返回<code>true</code>；失败返回<code>false</code>
     */
    public static boolean move(File oldFile, String newPath) {
        return ((copy(oldFile, newPath)) && (delete(oldFile)));
    }

    /**
     * 序列化
     *
     * @param obj      序列化的对象
     * @param fileName 文件名
     */
    public static void serialize(Serializable obj, String fileName) {
        ObjectOutputStream oos = null;
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获得序列化对象后的字节数组
     * <p/>
     * 序列化对象的<code>ObjectOutputStream</code>关闭后仍可返回序列化对象后的字节数组
     *
     * @param obj 序列化的对象
     * @return 序列化对象后的字节数组
     */
    public static byte[] serialize(Serializable obj) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反序列化
     *
     * @param fileName 文件名
     * @return 反序列化成功返回<code>Object</code>；失败则抛出异常
     */
    public static Object deserialize(String fileName) {
        ObjectInputStream ois = null;
        try {
            FileInputStream in = new FileInputStream(fileName);
            ois = new ObjectInputStream(in);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反序列化
     *
     * @param b 字节数组
     * @return 反序列化成功返回<code>Object</code>；失败则抛出异常
     */
    public static Object deserialize(byte[] b) {
        ObjectInputStream ois = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            ois = new ObjectInputStream(in);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
