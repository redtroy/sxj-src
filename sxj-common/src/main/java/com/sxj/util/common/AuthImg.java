package com.sxj.util.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AuthImg
{
    // 备选字体
    String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53",
            "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
    
    private Random random = new Random();
    
    // 生成随机颜色
    Color getRandColor(Random random, int fc, int bc)
    {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
    /**
     * 获取验证码
     * @return imgStr 验证码字符，Img 验证码图片
     */
    public ValidateImage getImage()
    {
        // 设置备选汉字
        String base = "23456789abcdefghjkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
        // 备选汉字的长度
        int length = base.length();
        // 指定图形验证码图片的大小
        int width = 120, height = 30;
        // 生成一张新图片
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        // 在图片中绘制内容
        Graphics g = image.getGraphics();
        // 创建随机类的实例
        Random random = new Random();
        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(random, 200, 250));
        g.fillRect(0, 0, width, height);
        int fontTypesLength = fontTypes.length;
        // 保存生成的汉字字符串
        String sRand = "";
        for (int i = 0; i < 4; i++)
        {
            int start = random.nextInt(length);
            String rand = base.substring(start, start + 1);
            sRand += rand;
            // 设置字体的颜色
            g.setColor(getRandColor(random, 10, 150));
            // 设置字体
            g.setFont(new Font(fontTypes[random.nextInt(fontTypesLength)],
                    Font.BOLD, 22 + random.nextInt(6)));
            // 将此汉字画到图片上
            g.drawString(rand, 24 * i + 10 + random.nextInt(8), 24);
        }
        // 添加噪点  
        float yawpRate = 0.05f;// 噪声率  
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
        for (int i = 0; i < 10; i++)
        {
            g.setColor(getRandColor(random, 100, 255));// 设置线条的颜色  
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }
        String str = sRand.toLowerCase();
        g.dispose();
        ValidateImage img = new ValidateImage();
        img.setImg(image);
        img.setImgStr(str);
        return img;
    }
    
    private int getRandomIntColor()
    {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb)
        {
            color = color << 8;
            color = color | c;
        }
        return color;
    }
    
    private int[] getRandomRgb()
    {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++)
        {
            rgb[i] = random.nextInt(255);
        }
        return rgb;
    }
}
