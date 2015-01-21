package com.sxj.supervisor.service.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.supervisor.model.contract.BatchItemModel;

public class JsonMapperUtil
{
    private static final Logger logger = LoggerFactory.getLogger(JsonMapperUtil.class);
    
    public static List<BatchItemModel> getBatchItems(String json)
    {
        List<BatchItemModel> bacthList = new ArrayList<BatchItemModel>();
        try
        {
            bacthList = JsonMapper.nonEmptyMapper()
                    .getMapper()
                    .readValue(json, new TypeReference<List<BatchItemModel>>()
                    {
                    });
        }
        catch (Exception e)
        {
            logger.debug("", e);
        }
        return bacthList;
    }
}
