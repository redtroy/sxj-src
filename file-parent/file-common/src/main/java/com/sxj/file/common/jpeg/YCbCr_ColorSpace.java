package com.sxj.file.common.jpeg;

import java.awt.color.ColorSpace;

public class YCbCr_ColorSpace extends ColorSpace
{
    public YCbCr_ColorSpace()
    {
        super(ColorSpace.TYPE_YCbCr, 3);
    }
    
    public float[] fromCIEXYZ(float[] data)
    {
        return (new float[data.length]);
    }
    
    public float[] toCIEXYZ(float[] data)
    {
        return (new float[data.length]);
    }
    
    public float[] fromRGB(float[] data)
    {
        return (new float[data.length]);
    }
    
    /* YCbCr to RGB range 0 to 1 */
    public float[] toRGB(float[] data)
    {
        float[] dest = new float[3];
        
        data[0] *= 255;
        data[1] *= 255;
        data[2] *= 255;
        
        dest[0] = (float) data[0] + (float) 1.402
                * ((float) data[2] - (float) 128);
        dest[1] = (float) data[0] - (float) 0.34414
                * ((float) data[1] - (float) 128) - (float) 0.71414
                * ((float) data[2] - (float) 128);
        dest[2] = (float) data[0] + (float) 1.772
                * ((float) data[1] - (float) 128);
        
        dest[0] /= 255;
        dest[1] /= 255;
        dest[2] /= 255;
        
        //dest[0] = ((float)1.164*((float)data[0]*(float)255 - (float)16) + (float)1.596*((float)data[2]*(float)255 - (float)128))/(float)255;
        //dest[1] = ((float)1.164*((float)data[0]*(float)255 - (float)16) - (float)0.813*((float)data[2]*(float)255 - (float)128) - (float)0.392*(data[1]*255 - 128))/(float)255;
        //dest[2] = ((float)1.164*((float)data[0]*(float)255 - (float)16) + (float)2.017*((float)data[1]*(float)255 - (float)128))/(float)255;
        
        //System.err.println("toRGB values received: 0: "+data[0]+" 1: "+data[1]+" 2: "+data[2]+" sent: 0: "+dest[0]+" 1: "+dest[1]+" 2: "+dest[2]);
        if (dest[0] < (float) 0)
            dest[0] = 0;
        if (dest[1] < (float) 0)
            dest[1] = 0;
        if (dest[2] < (float) 0)
            dest[2] = 0;
        
        if (dest[0] > (float) 1)
            dest[0] = 1;
        if (dest[1] > (float) 1)
            dest[1] = 1;
        if (dest[2] > (float) 1)
            dest[2] = 1;
        
        return (dest);
    }
    
    /* RGB to YCbCr range 0-255 */
    public static float[] toYCbCr(float[] data)
    {
        float[] dest = new float[3];
        //dest[0] = (float)0.257*data[0] + (float)0.504*data[1] + (float)0.098*data[2] + 16;
        //dest[1] = (float)-0.148*data[0] - (float)0.291*data[1] + (float)0.439*data[2] + 128;
        //dest[2] = (float)0.439*data[0] - (float)0.368*data[1] - (float)0.071*data[2] + 128;
        
        dest[0] = (float) ((0.299 * (float) data[0] + 0.587 * (float) data[1] + 0.114 * (float) data[2]));
        dest[1] = 128 + (float) ((-0.16874 * (float) data[0] - 0.33126
                * (float) data[1] + 0.5 * (float) data[2]));
        dest[2] = 128 + (float) ((0.5 * (float) data[0] - 0.41869
                * (float) data[1] - 0.08131 * (float) data[2]));
        
        return (dest);
        
    }
}
