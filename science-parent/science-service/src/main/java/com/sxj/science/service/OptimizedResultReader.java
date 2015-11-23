package com.sxj.science.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.sxj.science.model.result.LayOff;
import com.sxj.science.model.result.MaterialParam;
import com.sxj.science.model.result.OptimizedResult;
import com.sxj.science.model.result.PartSpecification;
import com.sxj.spring.modules.util.StringUtils;

public class OptimizedResultReader
{
    private static final byte NAME_FLAG = 1;
    
    private static final byte SPEC_FLAG = 2;
    
    private static final byte LAYOFF_FLAG = 4;
    
    private static final String EOF = "EOF";
    
    private static final String BR = "BR";
    
    private static void skip(BufferedReader reader, int rows)
            throws IOException
    {
        for (int i = 0; i < rows; i++)
        {
            readNextLine(reader);
        }
    }
    
    public static OptimizedResult read(InputStream input, String charset)
            throws IOException
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(input,
                    charset));
            OptimizedResult result = new OptimizedResult();
            skip(br, 1);
            String line = readNextLine(br);
            parseSectionName(result, line);
            line = readNextLine(br);
            parseMaterialParam(result, line);
            line = readNextLine(br);
            parseAggregateResult(result, line);
            readNextLine(br);
            String specs = readNextLine(br);
            String nums = readNextLine(br);
            line = parsePartSpecifications(result, specs, nums, br);
            while (!line.equals(EOF))
            {
                parseLayOffs(result, line);
                line = readNextLine(br);
            }
            return result;
        }
        catch (IOException e)
        {
            throw new OptimizedException(e);
        }
        finally
        {
            input.close();
        }
    }
    
    private static final String l0 = "＃方式";
    
    private static final String l1 = "余";
    
    private static final String l2 = "需";
    
    private static final String l3 = "根";
    
    private static void parseLayOffs(OptimizedResult result, String line)
    {
        String[] s1 = line.split(l1);
        LayOff layoff = new LayOff();
        layoff.setMaterialNum(Long.parseLong(s1[1].substring(s1[1].indexOf(l2) + 1,
                s1[1].indexOf(l3))
                .trim()));
        layoff.setLeft(Double.parseDouble(s1[1].substring(0, s1[1].indexOf(l2))
                .trim()));
        parseLayOffPartSepcifications(layoff, s1[0].substring(l0.length())
                .trim());
        layoff.getPartStr();
        result.getLayOffs().add(layoff);
    }
    
    private static final String o1 = "×";
    
    private static void parseLayOffPartSepcifications(LayOff layoff, String line)
    {
        String[] split = trimEmpty(line.split(" "));
        for (String s : split)
        {
            if (s.indexOf(o1) < 0)
                continue;
            String[] split2 = s.split(o1);
            layoff.getPartSpecifications().add(new PartSpecification(
                    Double.parseDouble(split2[0]), Long.parseLong(split2[1])));
        }
    }
    
    private static final String p0 = "规  格:";
    
    private static final String p1 = "数  量:";
    
    private static String parsePartSpecifications(OptimizedResult result,
            String specsString, String numsString, BufferedReader br)
            throws IOException
    {
        if (specsString.indexOf(p0) >= 0 && numsString.indexOf(p1) >= 0)
        {
            String[] specs = trimEmpty(specsString.split(":")[1].split(" "));
            String[] nums = trimEmpty(numsString.split(":")[1].split(" "));
            int i = 0;
            for (String spec : specs)
            {
                if (StringUtils.isNotEmpty(spec))
                    result.getPartSpecifications().add(new PartSpecification(
                            Double.parseDouble(spec), Long.parseLong(nums[i])));
                
                i++;
            }
            specsString = readNextLine(br);
            if (specsString.indexOf(p0) >= 0)
            {
                numsString = readNextLine(br);
                parsePartSpecifications(result, specsString, numsString, br);
            }
            else
                return specsString;
            
        }
        return readNextLine(br);
    }
    
    private static String[] trimEmpty(String[] src)
    {
        List<String> dest = new ArrayList<>();
        for (String s : src)
            if (StringUtils.isNotEmpty(s))
                dest.add(s);
        return dest.toArray(new String[] {});
    }
    
    private static String readNextLine(BufferedReader reader)
            throws IOException
    {
        return readNextLine(reader, 4);
    }
    
    private static String readNextLine(BufferedReader reader, int minLength)
            throws IOException
    {
        String line;
        while ((line = reader.readLine()) != null)
        {
            if (StringUtils.isEmpty(line))
                continue;
            if (line.length() <= minLength)
                continue;
            return line;
        }
        return EOF;
    }
    
    private static void parseSectionName(OptimizedResult result, String line)
    {
        String[] content = line.split(":");
        String name = content[1].trim();
        int index = name.indexOf("(");
        if (index != -1)
        {
            result.setSectionName(name.substring(0, index));
            result.setSectionType(name.substring(index + 1, name.length() - 1));
        }
        else
        {
            result.setSectionName(content[1].trim());
        }
        
    }
    
    private static final String m0 = "料缝";
    
    private static void parseMaterialParam(OptimizedResult result, String line)
    {
        String content = line.split(":")[1].trim();
        String[] split = content.split("=");
        MaterialParam param = new MaterialParam();
        param.setLength(Double.parseDouble(split[1].split(m0)[0].trim()));
        param.setKerf(Double.parseDouble(split[2].trim()));
        result.setParam(param);
    }
    
    private static final String t1 = "原材料总根数";
    
    private static final String t2 = " 根";
    
    private static final String t3 = "材料利用率";
    
    private static final String t4 = "%";
    
    private static void parseAggregateResult(OptimizedResult result, String line)
    {
        String num = line.substring(0 + t1.length() + 1, line.indexOf(t2))
                .trim();
        String usage = line.substring(line.indexOf(t3) + t3.length(),
                line.indexOf(t4)).trim();
        result.setMaterialNum(Long.parseLong(num));
        result.setUsagePercent(Double.parseDouble(usage) / 100);
    }
    
    public static void main(String[] args)
    {
        String name = "窗扇角码(HL-GR50528B)";
        int index = name.indexOf("(");
        if (index != -1)
        {
            System.out.println(name.substring(0, index));
            System.out.println(name.substring(index + 1, name.length() - 1));
        }
    }
    
}
