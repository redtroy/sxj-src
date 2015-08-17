package com.sxj.file.common.jpeg;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;

public class JPEGFrame
{
    public final static byte JPEG_COLOR_GRAY = 1;
    
    public final static byte JPEG_COLOR_RGB = 2;
    
    public final static byte JPEG_COLOR_YCbCr = 3;
    
    public final static byte JPEG_COLOR_CMYK = 4;
    
    public byte precision = 8;
    
    public byte colorMode = JPEGFrame.JPEG_COLOR_YCbCr;
    
    public byte componentCount = 0;
    
    public short width = 0, height = 0;
    
    public JPEGScan components;
    
    public JPEGFrame()
    {
        components = new JPEGScan();
    }
    
    public void addComponent(byte componentID, byte sampleFactors,
            byte quantizationTableID)
    {
        byte sampleHorizontalFactor = (byte) (sampleFactors >> 4);
        byte sampleVerticalFactor = (byte) (sampleFactors & 0x0f);
        components.addComponent(componentID,
                sampleHorizontalFactor,
                sampleVerticalFactor,
                quantizationTableID);
    }
    
    public void setPrecision(byte data)
    {
        precision = data;
    }
    
    public void setScanLines(short data)
    {
        height = data;
    }
    
    public void setSamplesPerLine(short data)
    {
        width = data;
    }
    
    public void setColorMode(byte data)
    {
        colorMode = data;
    }
    
    public void setComponentCount(byte data)
    {
        componentCount = data;
    }
    
    public byte getComponentCount()
    {
        return componentCount;
    }
    
    public void setHuffmanTables(byte componentID, JPEGHuffmanTable ACTable,
            JPEGHuffmanTable DCTable)
    {
        JPEGComponent comp = (JPEGComponent) components.getComponentByID(componentID);
        comp.setACTable(ACTable);
        comp.setDCTable(DCTable);
    }
}
