package org.science.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
import org.junit.Test;
import org.jxls.area.Area;
import org.jxls.builder.AreaBuilder;
import org.jxls.builder.xls.XlsCommentAreaBuilder;
import org.jxls.common.CellRef;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.formula.FastFormulaProcessor;
import org.jxls.formula.StandardFormulaProcessor;
import org.jxls.transform.Transformer;
import org.jxls.util.TransformerFactory;
import org.science.service.pojo.ExportMemberModel;

import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.function.Increaser;
import com.sxj.spring.modules.util.ClassLoaderUtil;

public class JxlsWriterTest
{
    
    public void testWriter() throws IOException
    {
        InputStream is = ClassLoaderUtil
                .getResourceAsStream("classpath:下料统计模板.xls");
        FileOutputStream fos = new FileOutputStream(new File("D:\\demo.xls"));
        Context context = new Context();
        
        context.putVar("currentDate",
                DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
        context.putVar("projectName", "测试项目");
        List<List<ScienceEntity>> excelData = new ArrayList<>();
        List<ScienceEntity> entites = new ArrayList<>();
        for (int i = 0; i < 500; i++)
        {
            ScienceEntity entity = new ScienceEntity();
            entity.setName("测试" + i);
            entity.setQuantity(String.valueOf(i));
            entity.setRemark("abc" + i);
            entites.add(entity);
        }
        excelData.add(entites);
        context.putVar("excelData", excelData);
        Transformer transformer = TransformerFactory.createTransformer(is, fos);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer
                .getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> functionMap = new HashMap<>();
        functionMap.put("increaser", new Increaser());
        evaluator.getJexlEngine().setFunctions(functionMap);
        AreaBuilder areaBuilder = new XlsCommentAreaBuilder();
        areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        boolean processFormulas = true;
        boolean useFastFormulaProcessor = true;
        for (Area xlsArea : xlsAreaList)
        {
            xlsArea.applyAt(
                    new CellRef(xlsArea.getStartCellRef().getCellName()),
                    context);
            if (processFormulas)
            {
                if (useFastFormulaProcessor)
                {
                    xlsArea.setFormulaProcessor(new FastFormulaProcessor());
                }
                else
                {
                    xlsArea.setFormulaProcessor(new StandardFormulaProcessor());
                }
                xlsArea.processFormulas();
            }
        }
        
        transformer.write();
        
        //        JxlsHelper.getInstance().processTemplate(is, fos, context);
        //        fos.close();
    }
    
    @Test
    public void testMass() throws IOException
    {
        InputStream is = ClassLoaderUtil
                .getResourceAsStream("classpath:memberInfo.xls");
        FileOutputStream fos = new FileOutputStream(new File("D:\\demo.xls"));
        Context context = new Context();
        
        List<ExportMemberModel> entites = new ArrayList<>();
        for (int i = 0; i < 500; i++)
        {
            ExportMemberModel model = new ExportMemberModel();
            model.setMemberNo(String.valueOf(i));
            model.setName("测试名称" + i);
            entites.add(model);
        }
        context.putVar("excelData", entites);
        Transformer transformer = TransformerFactory.createTransformer(is, fos);
        //        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer
        //                .getTransformationConfig().getExpressionEvaluator();
        //        Map<String, Object> functionMap = new HashMap<>();
        //        functionMap.put("increaser", new Increaser());
        //        evaluator.getJexlEngine().setFunctions(functionMap);
        AreaBuilder areaBuilder = new XlsCommentAreaBuilder();
        areaBuilder.setTransformer(transformer);
        List<Area> xlsAreaList = areaBuilder.build();
        boolean processFormulas = true;
        boolean useFastFormulaProcessor = true;
        for (Area xlsArea : xlsAreaList)
        {
            xlsArea.applyAt(
                    new CellRef(xlsArea.getStartCellRef().getCellName()),
                    context);
            if (processFormulas)
            {
                if (useFastFormulaProcessor)
                {
                    xlsArea.setFormulaProcessor(new FastFormulaProcessor());
                }
                else
                {
                    xlsArea.setFormulaProcessor(new StandardFormulaProcessor());
                }
                xlsArea.processFormulas();
            }
        }
        
        transformer.write();
        
        //        JxlsHelper.getInstance().processTemplate(is, fos, context);
        //        fos.close();
    }
    
}
