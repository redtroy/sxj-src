package com.supervisor.sms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Test;
import org.springframework.core.io.Resource;

import com.sxj.spring.modules.util.ClassLoaderUtil;

public class CaptchaHackTest
{
    
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void test() throws MalformedURLException, IOException
    {
        Resource resource = ClassLoaderUtil
                .getResource("classpath:captcha.png");
        ImageFilter filter = new ImageFilter(ImageIO.read(resource.getURL()));
        filter.scale(3.0f);
        BufferedImage image = filter.lineGrey();
        image = new ImageFilter(image).grayFilter();
        //        BufferedImage image = filter.removeBackgroud();
        //        image = new ImageFilter(image).median();
        //        image = new ImageFilter(image).median();
        //        image = new ImageFilter(image).median();
        //        image = new ImageFilter(image).median();
        //        image = new ImageFilter(image).median();
        //        BufferedImage scale = filter.scale(3.0f);
        //        BufferedImage sharp = new ImageFilter(scale).sharp();
        //        BufferedImage image = new ImageFilter(sharp).removeBackgroud();
        //        BufferedImage changeGrey = new ImageFilter(image).median();
        //        BufferedImage removeBackgroud = filter.removeBackgroud();
        //        BufferedImage image = filter.sharp();
        //        filter.changeGrey();
        ImageIO.write(image,
                "png",
                new FileOutputStream(new File("D:\\a.png")));
    }
    
}
