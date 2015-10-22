package org.science.service;

import java.util.ArrayList;
import java.util.List;

import com.sxj.science.entity.export.DocEntity;

public class DemoDocModel
{
    private List<DocEntity> docs = new ArrayList<>();
    
    private DocEntity doc = new DocEntity();
    
    public List<DocEntity> getDocs()
    {
        return docs;
    }
    
    public void setDocs(List<DocEntity> docs)
    {
        this.docs = docs;
    }
    
    public DocEntity getDoc()
    {
        return doc;
    }
    
    public void setDoc(DocEntity doc)
    {
        this.doc = doc;
    }
    
    @Override
    public String toString()
    {
        return this.getDoc().getColor();
    }
}
