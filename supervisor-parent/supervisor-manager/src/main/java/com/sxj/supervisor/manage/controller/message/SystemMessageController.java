package com.sxj.supervisor.manage.controller.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.message.SystemMessageInfoEntity;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.message.SystemMessageQuery;
import com.sxj.supervisor.service.message.ISystemMessageService;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/sysMessage")
public class SystemMessageController extends BaseController
{
    @Autowired
    private ISystemMessageService service;
    
    @RequestMapping("/query")
    public String query(SystemMessageQuery query, ModelMap map)
            throws WebException
    {
        try
        {
            query.setPagable(true);
            List<SystemMessageInfoEntity> list = service.querySystemInfoList(query);
            map.put("messageList", list);
            map.put("query", query);
            return "manage/message/systemMessage";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new WebException(e.getMessage(), e);
        }
        
    }
    
    @RequestMapping("/toedit")
    public String toEdit(String infoId, ModelMap map) throws WebException
    {
        if (StringUtils.isEmpty(infoId))
        {
            map.put("memberTypes", MemberTypeEnum.values());
            return "manage/message/edit-message";
        }
        else
        {
            SystemMessageInfoEntity info = service.getSystemInfo(infoId);
            map.put("info", info);
            map.put("memberTypes", MemberTypeEnum.values());
            return "manage/message/edit-message";
        }
        
    }
    
    @RequestMapping("/edit")
    public @ResponseBody Map<String, String> edit(String[] memberType,
            String[] memberNo, String[] memberName, String title,
            String editorValue) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            SystemMessageInfoEntity info = new SystemMessageInfoEntity();
            info.setMessage(editorValue);
            info.setTitle(title);
            info.setSendDate(new Date());
            List<MemberTypeEnum> types = new ArrayList<>();
            if (memberType != null && memberType.length > 0)
            {
                for (int i = 0; i < memberType.length; i++)
                {
                    MemberTypeEnum type = MemberTypeEnum.valueOf(memberType[i]);
                    types.add(type);
                }
            }
            service.addSystemInfo(memberNo, memberName, types, info);
            map.put("isOK", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
        
    }
}
