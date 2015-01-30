package com.sxj.supervisor.manage.controller.rfid.ref.logistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.comet.RfidChannel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.rfid.ref.LogisticsRefQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.rfid.ref.ILogisticsRefService;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

/** 
 * 物流标签管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/rfid/logisticsRef")
public class LogisticsRefController extends BaseController
{
    
    @Autowired
    private ILogisticsRefService refService;
    
    @Autowired
    private IContractService contractService;
    
    @RequestMapping("lable_list")
    public String lable_list(ModelMap map, LogisticsRefQuery query,
            String removeMessge) throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            AssociationTypesEnum[] types = AssociationTypesEnum.values();
            AuditStateEnum[] states = AuditStateEnum.values();
            RfidTypeEnum[] rfidtypes = RfidTypeEnum.values();
            List<LogisticsRefEntity> list = refService.query(query);
            map.put("types", types);
            map.put("states", states);
            map.put("rfidtypes", rfidtypes);
            map.put("query", query);
            map.put("list", list);
            map.put("channelName",
                    RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF);
            registChannel(RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF);
            if ("true".equals(removeMessge))
            {
                CometServiceImpl.setCount(RfidChannel.RFID_MANAGER_LOGISTICS_MESSGAGE_REF,
                        0l);
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error("查询错误", e, this.getClass());
            throw new WebException("查询错误");
        }
        return "manage/rfid/logisticsref/gysrfid-link";
    }
    
    @RequestMapping("del")
    public @ResponseBody Map<String, String> del(String id) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            contractService.deleteLogisticsRef(id);
            map.put("isOk", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("删除错误", e, this.getClass());
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 审核状态 更改
     * 
     * @param ref
     * @return
     * @throws WebException
     */
    @RequestMapping("review")
    public @ResponseBody Map<String, String> review(LogisticsRefEntity ref)
            throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            refService.update(ref);
            map.put("isOk", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("审核状态更改错误", e, this.getClass());
            throw new WebException("审核状态更改错误");
        }
    }
    
    /**
     * 查询批次申请
     */
    @RequestMapping("query_contract")
    public String query_contract(String id, String contractNo, String rfidNo,
            ModelMap map) throws WebException
    {
        try
        {
            ContractBatchModel model = contractService.getBatchByRfid(rfidNo);
            map.put("model", model);
            map.put("contractNo", contractNo);
            map.put("id", id);
        }
        catch (Exception e)
        {
            SxjLogger.error("批次合同查询错误", e, this.getClass());
            throw new WebException("批次合同查询错误");
        }
        return "manage/rfid/logisticsref/edit";
    }
    
    /**
     * 补损修改
     */
    @RequestMapping("edit_2")
    public @ResponseBody Map<String, String> edit_2(LogisticsRefEntity ref)
            throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            refService.update(ref);
            map.put("isOk", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("更新补损错误", e, this.getClass());
            throw new WebException("更新补损错误");
        }
    }
    
    /**
     * 批次修改
     */
    @RequestMapping("edit_1")
    public @ResponseBody Map<String, String> edit_1(ContractBatchModel model)
            throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            contractService.modifyBatch(model);
            map.put("isOk", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("批次修改错误", e, this.getClass());
            throw new WebException("批次修改错误");
        }
    }
}
