package com.sxj.util.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFObjectData;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class PoiUtils
{
    
    public static String getValue(HSSFCell hssfCell)
    {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN)
        {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        }
        else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC)
        {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        }
        else
        {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
    
    /**
     * 读取xls文件内容
     * 
     * @return List<XlsDto>对象
     * @throws IOException
     *             输入/输出(i/o)异常
     * @throws ParserConfigurationException 
     * @throws TransformerException 
     */
    public static Map<Integer, List<String>> readXls(InputStream input)
            throws IOException, TransformerException,
            ParserConfigurationException
    {
        POIFSFileSystem is = new POIFSFileSystem(input);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        List<HSSFObjectData> emObjectList = hssfWorkbook.getAllEmbeddedObjects();
        //XlsDto xlsDto = null;
        Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
        // 循环工作表Sheet
        //for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++)
        // {
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        
        if (hssfSheet == null)
        {
            return map;
        }
        // 循环行Row
        for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++)
        {
            HSSFRow hssfRow = hssfSheet.getRow(rowNum);
            if (hssfRow == null)
            {
                continue;
            }
            List<String> list = new ArrayList<String>();
            for (int cellNum = 0; cellNum < hssfRow.getLastCellNum(); cellNum++)
            {
                HSSFCell xh = hssfRow.getCell(cellNum);
                if (xh == null)
                {
                    continue;
                }
                
                list.add(getValue(xh));
            }
            HSSFObjectData emObject = emObjectList.get(rowNum - 1);
            DirectoryNode dn = (DirectoryNode) emObject.getDirectory();
            HWPFDocument embeddedWordDocument = new HWPFDocument(dn);
            String html = WordToHtml.convert2Html(embeddedWordDocument);
            list.add(html);
            map.put(rowNum, list);
        }
        
        // }
        return map;
    }
    
    public static void main(String[] args) throws IOException
    {
        try
        {
            FileInputStream in = new FileInputStream("D:/市场信息模版.xls");
            PoiUtils.readXls(in);
        }
        catch (TransformerException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ParserConfigurationException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
