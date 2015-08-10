package com.sxj.util.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

public class WordToHtml
{
    //    public static void writeFile(String content, String path)
    //    {
    //        FileOutputStream fos = null;
    //        BufferedWriter bw = null;
    //        try
    //        {
    //            File file = new File(path);
    //            fos = new FileOutputStream(file);
    //            bw = new BufferedWriter(new OutputStreamWriter(fos, "GB2312"));
    //            bw.write(content);
    //        }
    //        catch (FileNotFoundException fnfe)
    //        {
    //            fnfe.printStackTrace();
    //        }
    //        catch (IOException ioe)
    //        {
    //            ioe.printStackTrace();
    //        }
    //        finally
    //        {
    //            try
    //            {
    //                if (bw != null)
    //                    bw.close();
    //                if (fos != null)
    //                    fos.close();
    //            }
    //            catch (IOException ie)
    //            {
    //            }
    //        }
    //    }
    
    public static String convert2Html(HWPFDocument wordDocument)
            throws TransformerException, IOException,
            ParserConfigurationException
    {
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager()
        {
            public String savePicture(byte[] content, PictureType pictureType,
                    String suggestedName, float widthInches, float heightInches)
            {
                return "test/" + suggestedName;
            }
        });
        wordToHtmlConverter.processDocument(wordDocument);
        //save pictures  
        //        List pics = wordDocument.getPicturesTable().getAllPictures();
        //        if (pics != null)
        //        {
        //            for (int i = 0; i < pics.size(); i++)
        //            {
        //                Picture pic = (Picture) pics.get(i);
        //                System.out.println();
        //                try
        //                {
        //                    pic.writeImageContent(new FileOutputStream("D:/test/"
        //                            + pic.suggestFullFileName()));
        //                }
        //                catch (FileNotFoundException e)
        //                {
        //                    e.printStackTrace();
        //                }
        //            }
        //        }
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        out.close();
        return new String(out.toByteArray(), "UTF-8");
        //writeFile(new String(out.toByteArray()), outPutFile);
    }
}
