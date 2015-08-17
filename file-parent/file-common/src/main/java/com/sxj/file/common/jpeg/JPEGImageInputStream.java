package com.sxj.file.common.jpeg;

import java.io.IOException;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageInputStreamImpl;

public class JPEGImageInputStream extends ImageInputStreamImpl
{
    private ImageInputStream in;
    
    byte marker;
    
    public JPEGImageInputStream(ImageInputStream in)
    {
        super();
        
        this.in = in;
    }
    
    public int read() throws IOException
    {
        setBitOffset(0);
        return in.read();
    }
    
    public int read(byte[] data, int offset, int len) throws IOException
    {
        setBitOffset(0);
        return in.read(data, offset, len);
    }
    
    /**
    * Pull a byte from the stream, this checks to see if the byte is 0xff
    * and if the next byte isn't 0x00 (stuffed byte) it errors out. If it's
    * 0x00 then it simply ignores the byte.
    *
    * @return the next byte in the buffer
    *
    * @throws IOException TODO
    * @throws BitStreamException TODO
    */
    private byte pullByte() throws IOException, JPEGMarkerFoundException
    {
        byte mybyte = readByte();
        // FIXME: handle multiple 0xff in a row
        if (mybyte == (byte) (0xff))
        {
            byte secondbyte = readByte();
            if (secondbyte != (byte) (0x00))
            {
                marker = secondbyte;
                throw new JPEGMarkerFoundException();
            }
        }
        return mybyte;
    }
    
    /**
    * This returns the marker that was last encountered.  This should only be
    * used if removeBit() throws a MarkerTagFound exception.
    *
    * @return marker as byte
    */
    public byte getMarker()
    {
        return marker;
    }
    
    /**
    * Removes a bit from the buffer. (Removes from the top of a queue). This
    * also checks for markers and throws MarkerTagFound exception if it does.
    * If MarkerTagFound is thrown you can use getMarker() method to get the
    * marker that caused the throw.
    *
    * @param l specifies how many bits you want to remove and add to the
    *        integer
    * @return the amount of bits specified by l as an integer
    *
    * @throws IOException TODO
    * @throws JPEGMarkerFoundException 
    * @throws BitStreamException TODO
    */
    public int readBit() throws IOException, JPEGMarkerFoundException
    {
        checkClosed();
        
        // Calc new bit offset here, readByte resets it.
        int newOffset = (bitOffset + 1) & 0x7;
        
        byte data = pullByte();
        
        if (bitOffset != 0)
        {
            seek(getStreamPosition() - 1);
            data = (byte) (data >> (8 - newOffset));
        }
        
        bitOffset = newOffset;
        return data & 0x1;
    }
    
    /**
    * This method skips over the the data and finds the next position
    * in the bit sequence with a X'FF' X'??' sequence.  Multiple X'FF
    * bytes in sequence are considered padding and interpreted as one
    * X'FF byte.
    *
    * @return the next marker byte in the stream
    * @throws IOException if the end of the stream is reached
    * unexpectedly
    */
    public byte findNextMarker() throws IOException
    {
        boolean marked0xff = false;
        byte byteinfo = JPEGMarker.X00;
        
        setBitOffset(0);
        while (true)
        {
            byteinfo = readByte();
            if (!marked0xff)
            {
                if (byteinfo == JPEGMarker.XFF)
                    marked0xff = true;
            }
            else
            {
                if (byteinfo == JPEGMarker.XFF)
                    // Ignore the value 0xff when it is immediately
                    // followed by another 0xff byte.
                    continue;
                else if (byteinfo == JPEGMarker.X00)
                    // The sequence 0xff 0x00 is used to encode the
                    // actual value 0xff.  So restart our search for a
                    // marker.
                    marked0xff = false;
                else
                    // One or more 0xff values were follwed by a
                    // non-0x00, non-0xff value so return this as the
                    // marker byte.
                    return byteinfo;
            }
        }
    }
}
