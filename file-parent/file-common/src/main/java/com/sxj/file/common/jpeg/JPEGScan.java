package com.sxj.file.common.jpeg;

import java.util.ArrayList;

public class JPEGScan
{
    private int maxHeight = 0, maxWidth = 0, maxV = 0, maxH = 0;
    
    private int numOfComponents = 0, numOfComponentBlocks = 0;
    
    private ArrayList components = new ArrayList();
    
    public JPEGScan()
    {
        // Nothing to do here.
    }
    
    public JPEGScan(int h, int w)
    {
        maxHeight = h;
        maxWidth = w;
    }
    
    private void recalculateDimensions()
    {
        JPEGComponent comp;
        
        // Compute the maximum H, maximum V factors defined in Annex A of the ISO
        // DIS 10918-1.
        for (int i = 0; i < components.size(); i++)
        {
            comp = (JPEGComponent) components.get(i);
            if (comp.factorH > maxH)
                maxH = comp.factorH;
            if (comp.factorV > maxV)
                maxV = comp.factorV;
        }
        
        for (int i = 0; i < components.size(); i++)
        {
            comp = (JPEGComponent) components.get(i);
            comp.maxH = maxH;
            comp.maxV = maxV;
        }
        
    }
    
    public void addComponent(byte id, byte factorHorizontal,
            byte factorVertical, byte quantizationID)
    {
        JPEGComponent component = new JPEGComponent(id, factorHorizontal,
                factorVertical, quantizationID);
        components.add((Object) component);
        recalculateDimensions();
        numOfComponents++;
        numOfComponentBlocks += factorHorizontal * factorVertical;
    }
    
    public JPEGComponent getComponentByID(byte id)
    {
        JPEGComponent comp = (JPEGComponent) components.get(0);
        for (int i = 0; i < components.size(); i++)
        {
            comp = (JPEGComponent) components.get(i);
            if (comp.component_id == id)
                break;
        }
        return (comp);
    }
    
    public JPEGComponent get(int id)
    {
        return ((JPEGComponent) components.get(id));
    }
    
    public int getX(byte id)
    {
        JPEGComponent comp = getComponentByID(id);
        return (comp.width);
    }
    
    public int getY(byte id)
    {
        JPEGComponent comp = getComponentByID(id);
        return (comp.height);
    }
    
    public int getMaxV()
    {
        return (maxV);
    }
    
    public int getMaxH()
    {
        return (maxH);
    }
    
    public void setWidth(int w)
    {
        maxWidth = w;
    }
    
    public void setHeight(int h)
    {
        maxHeight = h;
    }
    
    public int size()
    {
        return (numOfComponents);
    }
    
    public int sizeComponentBlocks()
    {
        return (numOfComponentBlocks);
    }
}
