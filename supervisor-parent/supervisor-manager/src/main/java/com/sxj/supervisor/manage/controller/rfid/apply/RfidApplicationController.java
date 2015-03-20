package com.sxj.supervisor.manage.controller.rfid.apply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.apply.RfidApplicationEntity;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.comet.RfidChannel;
import com.sxj.supervisor.model.rfid.app.RfidApplicationQuery;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/rfid/apply")
public class RfidApplicationController extends BaseController
{
    @Autowired
    private IRfidApplicationService sppService;
    
    /**
     * 获取申请单列表
     * 
     * @param map
     * @param query
     * @return
     * @throws WebException
     */
    @RequestMapping("appList")
    public String appList(ModelMap map, RfidApplicationQuery query)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            List<RfidApplicationEntity> list = sppService.query(query);
            PayStateEnum[] paystates = PayStateEnum.values();
            ReceiptStateEnum[] receiptStates = ReceiptStateEnum.values();
            RfidTypeEnum[] types = RfidTypeEnum.values();
            map.put("list", list);
            map.put("paystates", paystates);
            map.put("receiptStates", receiptStates);
            map.put("types", types);
            map.put("query", query);
            map.put("channelName", RfidChannel.RFID_APPLY_MESSAGE);
            if (StringUtils.isNotEmpty(query.getIsDelMes()))
            {
                CometServiceImpl.setCount(RfidChannel.RFID_APPLY_MESSAGE, 0l);
            }
           // registChannel(RfidChannel.RFID_APPLY_MESSAGE);
        }
        catch (Exception e)
        {
            SxjLogger.error("申请单查询错误", e, this.getClass());
            throw new WebException("申请单查询错误");
        }
        return "manage/rfid/apply/apply-list";
    }
    
    /**
     * 修改申请单
     * 
     * @param apply
     * @return
     * @throws WebException
     */
    @RequestMapping("edit_apply")
    public @ResponseBody Map<String, String> edit(RfidApplicationEntity apply)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            sppService.updateApp(apply);
            map.put("isOk", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("修改RFID申请单错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 删除申请单
     * 
     * @param apply
     * @return
     * @throws WebException
     */
    @RequestMapping("del_apply")
    public @ResponseBody Map<String, String> del(String id) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            sppService.delApp(id);
            map.put("isOk", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("修改RFID申请单错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 验证申请单是否存在
     * 
     * @param apply
     * @return
     * @throws WebException
     */
    @RequestMapping("checkApply")
    public @ResponseBody Map<String, String> checkApply(String id)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            RfidApplicationEntity apply = sppService.getApplicationInfo(id);
            if (apply == null)
            {
                throw new WebException("申请单不存在！");
            }
            if (apply.getDelstate())
            {
                throw new WebException("申请单不存在！");
            }
            map.put("isOk", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("验证RFID申请单错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
}
