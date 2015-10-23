package com.sxj.science.service.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.DateUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.science.dao.export.IDocDao;
import com.sxj.science.dao.export.IProjectDao;
import com.sxj.science.dao.export.IScienceDao;
import com.sxj.science.entity.export.DocEntity;
import com.sxj.science.entity.export.ProjectEntity;
import com.sxj.science.entity.export.ScienceEntity;
import com.sxj.science.function.Increaser;
import com.sxj.science.model.result.OptimizedModel;
import com.sxj.science.model.result.OptimizedResult;
import com.sxj.science.model.statis.StatisticsItemModel;
import com.sxj.science.service.IScienceService;
import com.sxj.science.service.OptimizedResultReader;
import com.sxj.spring.modules.util.LocalFileUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class ScienceServiceImpl implements IScienceService
{
    
    @Autowired
    private IDocDao docDao;
    
    @Autowired
    private IProjectDao projectDao;
    
    @Autowired
    private IScienceDao scienceDao;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Value("${youhu.xialiaotongji}")
    private String countTempPath;
    
    @Value("${youhu.cailiaolingliao}")
    private String countTempPath2;
    
    @Value("${youhu.xialiaoyouhua}")
    private String countTempPath3;
    
    @Override
    public List<ScienceEntity> getScienceList(String[] itemIds)
            throws ServiceException
    {
        try
        {
            QueryCondition<DocEntity> query = new QueryCondition<>();
            query.addCondition("itemIds", itemIds);
            List<DocEntity> docList = docDao.query(query);
            List<String> docIds = new ArrayList<String>();
            if (docList != null && docList.size() > 0)
            {
                for (DocEntity doc : docList)
                {
                    docIds.add(doc.getId());
                }
            }
            QueryCondition<ScienceEntity> scienceQuery = new QueryCondition<>();
            if (docIds.size() > 0)
            {
                scienceQuery.addCondition("docIds",
                        docIds.toArray(new String[docIds.size()]));
            }
            return scienceDao.query(scienceQuery);
        }
        catch (Exception e)
        {
            SxjLogger.error("获取下料数据错误", e, this.getClass());
            throw new ServiceException("获取下料数据错误", e);
        }
    }
    
    @Override
    public OptimizedModel process(List<ScienceEntity> listValue,
            String projectId, String length, String interval)
            throws ServiceException
    {
        try
        {
            if (listValue == null || listValue.size() == 0)
            {
                throw new ServiceException("下料单数据为空");
            }
            Map<String, ScienceEntity> map = new HashMap<>();
            for (ScienceEntity scienceEntity : listValue)
            {
                String key = scienceEntity.getUsed() + scienceEntity.getName()
                        + scienceEntity.getType() + scienceEntity.getLength()
                        + scienceEntity.getRemark();
                if (map.containsKey(key))
                {
                    ScienceEntity value = map.get(key);
                    value.setQuantity(value.getQuantity()
                            + scienceEntity.getQuantity());
                    map.put(key, value);
                }
                else
                {
                    map.put(key, scienceEntity);
                }
            }
            return executeExe(map, projectId, length, interval);
        }
        catch (Exception e)
        {
            SxjLogger.error("优化下料数据错误", e, this.getClass());
            throw new ServiceException("优化下料数据错误", e);
        }
        
    }
    
    private OptimizedModel executeExe(Map<String, ScienceEntity> map,
            String projectId, String length, String interval)
    {
        //合并需要优化的项
        String filePath = "D:/sheji/CAD_FILE/";
        String fileName = "xdata.txt";
        File rootFile = new File(filePath);
        if (!rootFile.exists())
        {
            rootFile.mkdir();
        }
        File xdata = new File(filePath + fileName);
        BufferedWriter output = null;
        OptimizedModel model = new OptimizedModel();
        List<OptimizedResult> resultList = new ArrayList<OptimizedResult>();
        try
        {
            int index = 1;
            Map<String, List<ScienceEntity>> map2 = new HashMap<>();
            List<StatisticsItemModel> itemList = new ArrayList<>();
            for (String key : map.keySet())
            {
                ScienceEntity value = map.get(key);
                String key2 = value.getName() + value.getType();
                StatisticsItemModel item = new StatisticsItemModel();
                item.setNo(index);
                item.setLength(value.getLength());
                item.setName(value.getName());
                item.setQuantity(value.getQuantity());
                item.setRemark(value.getRemark());
                item.setType(value.getType());
                itemList.add(item);
                if (map2.containsKey(key2))
                {
                    List<ScienceEntity> list2 = map2.get(key2);
                    list2.add(value);
                    map2.put(key2, list2);
                }
                else
                {
                    List<ScienceEntity> list2 = new ArrayList<ScienceEntity>();
                    list2.add(value);
                    map2.put(key2, list2);
                }
                index++;
            }
            //写执行文件
            List<List<ScienceEntity>> excelData = new ArrayList<List<ScienceEntity>>();
            for (String key : map2.keySet())
            {
                if (!xdata.exists())
                {
                    xdata.createNewFile();
                }
                else
                {
                    LocalFileUtil.delete(xdata);
                }
                List<ScienceEntity> list = map2.get(key);
                excelData.add(list);
                String text = "";
                int index2 = 1;
                double quantity = 0d;
                for (ScienceEntity scienceEntity : list)
                {
                    if (index2 == 1)
                    {
                        text = scienceEntity.getName() + "("
                                + scienceEntity.getType() + ")\r\n";
                        text = text + length + "\r\n";
                        text = text + interval + "\r\n";
                        text = text + list.size() + "\r\n";
                    }
                    text = text + scienceEntity.getLength() + "\r\n";
                    text = text + scienceEntity.getQuantity() + "\r\n";
                    if (StringUtils.isNotEmpty(scienceEntity.getQuantity()))
                    {
                        quantity = quantity
                                + Double.parseDouble(scienceEntity.getQuantity());
                    }
                    if (list.size() == 16)
                    {
                        System.out.println("dfdfd");
                    }
                    if (scienceEntity.getLength().trim().equals("0")
                            || scienceEntity.getLength().trim().equals("0.0"))
                    {
                        System.out.println("dfdfd");
                    }
                    if (scienceEntity.getQuantity().trim().equals("0")
                            || scienceEntity.getQuantity().trim().equals("0.0"))
                    {
                        System.out.println("dfdfd");
                    }
                    index2++;
                }
                output = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(xdata), "GBK"));
                output.write(text);
                output.flush();
                output.close();
                Process p = java.lang.Runtime.getRuntime()
                        .exec("D:/sheji/execute.bat");
                OptimizedResult result = OptimizedResultReader.read(new FileInputStream(
                        filePath + "XABC.TXT"),
                        "GBK");
                result.setQuantity(quantity);
                resultList.add(result);
            }
            model.setResult(resultList);
            //生成材料统计单excel
            ProjectEntity project = projectDao.getProject(projectId);
            Context context = new Context();
            context.putVar("currentDate",
                    DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
            context.putVar("projectName", project.getName());
            context.putVar("excelData", excelData);
            context.putVar("itemList", itemList);
            String cltongjiPath = outExcel(countTempPath, context);
            model.setCltongjiPath(cltongjiPath);
            System.out.println(cltongjiPath);
            
            //生成材料领料单EXCLE
            context = new Context();
            context.putVar("currentDate",
                    DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
            context.putVar("projectName", project.getName());
            context.putVar("excelData", resultList);
            String cllingliaoPath = outExcel(countTempPath2, context);
            model.setCllingliaoPath(cllingliaoPath);
            System.out.println(cllingliaoPath);
            
            //生成型材下料优化单
            context = new Context();
            context.putVar("currentDate",
                    DateUtils.formatDate(new Date(), "YYYY-MM-dd"));
            context.putVar("projectName", project.getName());
            context.putVar("excelData", resultList);
            String xialiaoyouhua = outExcel(countTempPath3, context);
            model.setXlyouhuaPath(xialiaoyouhua);
            System.out.println(cllingliaoPath);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
        }
        finally
        {
            try
            {
                output.close();
            }
            catch (IOException e)
            {
                SxjLogger.error(e.getMessage(), e, this.getClass());
            }
        }
        return model;
    }
    
    private String outExcel(String temPath, Context context) throws IOException
    {
        InputStream is = new FileInputStream(temPath);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Transformer transformer = TransformerFactory.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig()
                .getExpressionEvaluator();
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
            xlsArea.applyAt(new CellRef(xlsArea.getStartCellRef().getCellName()),
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
        
        //JxlsHelper.getInstance().processTemplate(is, os, context);
        byte[] data = os.toByteArray();
        ByteArrayInputStream input = new ByteArrayInputStream(data);
        return storageClientService.uploadFile(null, input, data.length, "XLS");
    }
}
