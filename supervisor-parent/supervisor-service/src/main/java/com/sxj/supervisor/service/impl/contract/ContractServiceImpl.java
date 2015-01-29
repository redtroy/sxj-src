package com.sxj.supervisor.service.impl.contract;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.statemachine.StateMachineImpl;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.contract.IContractItemDao;
import com.sxj.supervisor.dao.contract.IContractModifyBatchDao;
import com.sxj.supervisor.dao.contract.IContractModifyDao;
import com.sxj.supervisor.dao.contract.IContractModifyItemDao;
import com.sxj.supervisor.dao.contract.IContractReplenishBatchDao;
import com.sxj.supervisor.dao.contract.IContractReplenishDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.dao.rfid.ref.ILogisticsRefDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.contract.ContractItemEntity;
import com.sxj.supervisor.entity.contract.ModifyBatchEntity;
import com.sxj.supervisor.entity.contract.ModifyContractEntity;
import com.sxj.supervisor.entity.contract.ModifyItemEntity;
import com.sxj.supervisor.entity.contract.ReplenishBatchEntity;
import com.sxj.supervisor.entity.contract.ReplenishContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.entity.rfid.logistics.LogisticsRfidEntity;
import com.sxj.supervisor.entity.rfid.ref.LogisticsRefEntity;
import com.sxj.supervisor.entity.rfid.window.WindowRfidEntity;
import com.sxj.supervisor.entity.rfid.windowref.WindowRefEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.rfid.RfidStateEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.supervisor.enu.rfid.windowref.LinkStateEnum;
import com.sxj.supervisor.model.contract.BatchItemModel;
import com.sxj.supervisor.model.contract.ContractBatchModel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.contract.ContractModifyModel;
import com.sxj.supervisor.model.contract.ContractQuery;
import com.sxj.supervisor.model.contract.ContractReplenishModel;
import com.sxj.supervisor.model.contract.ModifyBatchModel;
import com.sxj.supervisor.model.contract.ReplenishBatchModel;
import com.sxj.supervisor.model.rfid.RfidLog;
import com.sxj.supervisor.model.rfid.logistics.LogisticsRfidQuery;
import com.sxj.supervisor.model.rfid.ref.LogisticsRefQuery;
import com.sxj.supervisor.model.rfid.window.WindowRfidQuery;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.supervisor.service.rfid.app.IRfidApplicationService;
import com.sxj.supervisor.service.rfid.logistics.ILogisticsRfidService;
import com.sxj.supervisor.service.rfid.ref.ILogisticsRefService;
import com.sxj.supervisor.service.rfid.window.IWindowRfidService;
import com.sxj.supervisor.service.rfid.windowRef.IWindowRfidRefService;
import com.sxj.supervisor.service.util.JsonMapperUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

/**
 * 合同管理业务类
 * 
 * @author Ann
 *
 */
@Service
@Transactional
public class ContractServiceImpl implements IContractService
{
    /**
     * 合同DAO
     */
    @Autowired
    private IContractDao contractDao;
    
    /** 
     * 批次DAO
     */
    @Autowired
    private IContractBatchDao contractBatchDao;
    
    /**
     * 变更合同批次DAO
     */
    @Autowired
    private IContractModifyBatchDao contractBatchHisDao;
    
    /**
     * 合同产品条目
     */
    @Autowired
    private IContractItemDao contractItemDao;
    
    /**
     * 变更合同产品条目
     */
    @Autowired
    private IContractModifyItemDao contractModifyItemDao;
    
    /**
     * 变更合同主体
     */
    @Autowired
    private IContractModifyDao contractModifyDao;
    
    /**
     * 变更合同批次
     */
    @Autowired
    private IContractModifyBatchDao contractModifyBatchDao;
    
    /**
     * 补损合同主体
     */
    @Autowired
    private IContractReplenishDao contractReplenishDao;
    
    /**
     * 补损合同批次
     */
    @Autowired
    private IContractReplenishBatchDao contractReplenishBatchDao;
    
    /**
     * 备案Dao
     */
    @Autowired
    private IRecordDao recordDao;
    
    /**
     * 备案service
     */
    @Autowired
    private IRecordService recordService;
    
    @Autowired
    private ILogisticsRfidService logisticsRfidService;
    
    @Autowired
    private ILogisticsRefService logisticsRefService;
    
    @Autowired
    private IWindowRfidService windowRfidService;
    
    @Autowired
    private IWindowRfidRefService windowRefService;
    
    @Autowired
    private IRfidApplicationService appRfidService;
    
    private IContractService self;
    
    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private ILogisticsRefDao refDao;
    
    @Autowired
    @Qualifier("recordStatefsm")
    private StateMachineImpl<RecordStateEnum> recordStatefsm;
    
    @Autowired
    @Qualifier("contractStatefsm")
    private StateMachineImpl<ContractStateEnum> contractStatefsm;
    
    @PostConstruct
    public void init()
    {
        self = context.getBean(IContractService.class);
    }
    
    /**
     * 新增合同
     */
    @Override
    @Transactional
    public void addContract(ContractEntity contract,
            List<ContractItemEntity> itemList, String recordId)
            throws ServiceException
    {
        try
        {
            Assert.notNull(contract);
            RecordEntity record = recordDao.getRecord(recordId);
            if (StringUtils.isNotEmpty(record.getContractNo())
                    && record.getState().equals(RecordStateEnum.BINDING))
            {
                throw new ServiceException("合同已经生成,不能重复生成");
            }
            Assert.notEmpty(itemList, "生成合同出错!!");
            float itemQuantity = 0f;
            List<ContractItemEntity> newList = new ArrayList<ContractItemEntity>();
            for (int i = 0; i < itemList.size(); i++)
            {
                ContractItemEntity ci = itemList.get(i);
                ci.setId(StringUtils.getUUID());
                itemQuantity = itemQuantity + ci.getQuantity();
                newList.add(ci);
            }
            // 拼装实体
            contract.setRecordDate(record.getAcceptDate());
            contract.setRecordNo(record.getRecordNo());// 备案号
            contract.setType(record.getContractType());
            contract.setImgPath(record.getImgPath());
            contract.setState(ContractStateEnum.NOAPPROVAL);
            contract.setConfirmState(ContractSureStateEnum.NOAFFIRM);
            contract.setCreateDate(new Date());
            String year = new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance()
                    .getTime());
            String month = new SimpleDateFormat("MM", Locale.CHINESE).format(Calendar.getInstance()
                    .getTime());
            contract.setDateNo("CT" + year + month);
            contract.setUseQuantity(0f);
            contract.setItemQuantity(itemQuantity);
            contractDao.addContract(contract);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("items", newList);
            map.put("contractNo", contract.getContractNo());
            contractItemDao.addItem(map);// 新增条目
            // contractDao.updateContract(contract);
            record.setContractNo(contract.getContractNo());
            recordStatefsm.setCurrentState(record.getState());
            recordStatefsm.fire(record.getState().toString(), record);
            // record.setState(RecordStateEnum.Binding);
            recordDao.updateRecord(record);
            
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增合同出错:" + e.getMessage(), e);
        }
    }
    
    /**
     * 修改合同
     */
    @Override
    @Transactional
    public void modifyContract(ContractModel contract) throws ServiceException
    {
        try
        {
            List<ContractItemEntity> insertList = new ArrayList<ContractItemEntity>();
            Float itemQuantity = 0f;
            for (ContractItemEntity contractItemEntity : contract.getItemList())
            {
                if (StringUtils.isNotEmpty(contractItemEntity.getId()))
                {
                    ContractItemEntity ci = contractItemDao.getItems(contractItemEntity.getId());
                    if (ci.getUpdateState() != null && ci.getUpdateState() != 0)
                    {
                        contractItemEntity.setUpdateState(1);
                        contractItemEntity.setId(null);
                    }
                    
                }
                contractItemEntity.setId(StringUtils.getUUID());
                itemQuantity = itemQuantity + contractItemEntity.getQuantity();
                insertList.add(contractItemEntity);
            }
            // 条目
            if (contract.getItemList() != null)
            {
                List<ContractItemEntity> item = contractItemDao.queryItems(contract.getContract()
                        .getContractNo());
                if (item != null)
                {
                    String ids = "";
                    for (ContractItemEntity contractItemEntity : item)
                    {
                        if (ids == "")
                        {
                            ids += contractItemEntity.getId();
                        }
                        else
                        {
                            ids += "," + contractItemEntity.getId();
                        }
                    }
                    // 删除条目
                    contractItemDao.deleteItems(ids.split(","));
                }
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("items", insertList);
                map.put("contractNo", contract.getContract().getContractNo());
                contractItemDao.addItem(map);
            }
            
            // 主体
            if (contract.getContract() != null)
            {
                ContractEntity ce = contractDao.getContract(contract.getContract()
                        .getId());
                String[] arr = ce.getRecordNo().split(",");
                String recordNo = contract.getContract().getRecordNo();
                if (recordNo != null && recordNo.length() > 0)
                {
                    String[] recordNoArr = recordNo.split(",");
                    boolean flag = Arrays.equals(arr, recordNoArr);
                    if (!flag)
                    {
                        // 解绑备案号
                        for (String str : arr)
                        {
                            RecordEntity re = recordService.getRecordByNo(str.trim());
                            re.setContractNo("");
                            re.setState(RecordStateEnum.NOBINDING);
                            recordDao.updateRecord(re);
                        }
                        // 更新备案号
                        for (String str : recordNoArr)
                        {
                            RecordEntity re = recordService.getRecordByNo(str);
                            re.setContractNo(contract.getContract()
                                    .getContractNo());
                            re.setState(RecordStateEnum.BINDING);
                            recordDao.updateRecord(re);
                        }
                    }
                }
                contract.getContract().setItemQuantity(itemQuantity);
                contractDao.updateContract(contract.getContract());
            }
            
            // 批次
            if (contract.getBatchList() != null)
            {
                List<ContractBatchEntity> cbelist = new ArrayList<ContractBatchEntity>();
                for (int i = 0; i < contract.getBatchList().size(); i++)
                {
                    ContractBatchModel cbm = contract.getBatchList().get(i);
                    ContractBatchEntity cbe = cbm.getBatch();
                    if (cbm.getBatchItems() != null)
                    {
                        cbe.setBatchItems(JsonMapper.nonEmptyMapper()
                                .toJson(cbm.getBatchItems()));// 转json
                    }
                    cbelist.add(cbe);
                }
                contractBatchDao.updateBatchs(cbelist);
            }
            // 变更记录
            if (contract.getModifyList() != null)
            {
                List<ModifyContractEntity> mceList = new ArrayList<ModifyContractEntity>();// 变更记录主体
                for (int i = 0; i < contract.getModifyList().size(); i++)
                {
                    ContractModifyModel cmm = contract.getModifyList().get(i);
                    if (cmm.getModifyContract() != null)
                    {
                        mceList.add(cmm.getModifyContract());
                    }
                    if (cmm.getModifyItemList() != null)
                    {
                        contractModifyItemDao.updateItems(cmm.getModifyItemList());
                    }
                    List<ModifyBatchEntity> mbeList = new ArrayList<ModifyBatchEntity>();
                    if (cmm.getModifyBatchList() != null)
                    {
                        for (int j = 0; j < cmm.getModifyBatchList().size(); j++)
                        {
                            ModifyBatchModel mbm = cmm.getModifyBatchList()
                                    .get(j);
                            if (mbm.getModifyBatchItems() != null)
                            {
                                mbm.getModifyBatch()
                                        .setBatchItems(JsonMapper.nonEmptyMapper()
                                                .toJson(mbm.getModifyBatchItems()));
                            }
                            mbeList.add(mbm.getModifyBatch());
                        }
                        contractModifyBatchDao.updateItems(mbeList);
                    }
                }
                contractModifyDao.updateModify(mceList);
            }
            // 补损记录
            if (contract.getReplenishList() != null)
            {
                List<ReplenishContractEntity> mceList = new ArrayList<ReplenishContractEntity>();// 补损记录主体
                for (int i = 0; i < contract.getReplenishList().size(); i++)
                {
                    ContractReplenishModel crm = contract.getReplenishList()
                            .get(i);
                    if (crm.getReplenishContract() != null)
                    {
                        mceList.add(crm.getReplenishContract());
                    }
                    List<ReplenishBatchEntity> rbeList = new ArrayList<ReplenishBatchEntity>();
                    if (crm.getBatchItems() != null)
                    {
                        for (int j = 0; j < crm.getBatchItems().size(); j++)
                        {
                            ReplenishBatchModel rbm = crm.getBatchItems()
                                    .get(j);
                            if (rbm.getReplenishBatch() != null)
                            {
                                rbm.getReplenishBatch()
                                        .setBatchItems(JsonMapper.nonEmptyMapper()
                                                .toJson(rbm.getReplenishBatchItems()));
                            }
                            rbeList.add(rbm.getReplenishBatch());
                        }
                        contractReplenishBatchDao.updateReplenishBatch(rbeList);
                    }
                }
                contractReplenishDao.updateReplenish(mceList);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("修改合同出错", e);
        }
    }
    
    /**
     * 获取合同
     */
    @Override
    @Transactional
    public ContractModel getContract(String id) throws ServiceException
    {
        try
        {
            ContractModel contractModel = new ContractModel();
            ContractEntity contract = contractDao.getContract(id);// 合同主体
            Assert.notNull(contract, "合同主体不能为NULL！！！");
            contractModel.setContract(contract);
            List<ContractItemEntity> itemList = contractItemDao.queryItems(contract.getContractNo());
            // 产品条目
            getItems(contractModel, itemList);
            QueryCondition<ContractBatchEntity> batchCondition = new QueryCondition<ContractBatchEntity>();
            batchCondition.addCondition("contractId", contract.getContractNo());// 补损备案
            List<ContractBatchEntity> batchList = contractBatchDao.queryBacths(batchCondition);
            // 批次信息
            getBatches(contractModel, batchList);
            // 变更信息
            getContractModifies(contractModel, contract);
            // 补损合同
            getReplenishes(contractModel, contract);
            return contractModel;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询合同信息错误", e);
        }
    }
    
    private void getItems(ContractModel contractModel,
            List<ContractItemEntity> itemList)
    {
        if (!CollectionUtils.isEmpty(itemList))
        {
            contractModel.setItemList(itemList);
        }
    }
    
    private void getReplenishes(ContractModel contractModel,
            ContractEntity contract) throws IOException, JsonParseException,
            JsonMappingException
    {
        String replenishRecordIds = this.recordIdArr(contract.getContractNo(),
                "2");// 获取变更备案
        if (!StringUtils.isEmpty(replenishRecordIds))
        {
            
            QueryCondition<ReplenishContractEntity> replenishCondition = new QueryCondition<ReplenishContractEntity>();
            replenishCondition.addCondition("recordIds", replenishRecordIds);// 补损备案ID
            List<ReplenishContractEntity> replenishList = contractReplenishDao.queryReplenish(replenishCondition);
            if (!CollectionUtils.isEmpty(replenishList))
            {
                String[] recordIds = projectRecordIds(replenishList);
                QueryCondition<ReplenishBatchEntity> query = new QueryCondition<ReplenishBatchEntity>();
                query.addCondition("recordIdArray", recordIds);
                List<ReplenishBatchEntity> replenishBatchList = contractReplenishBatchDao.queryReplenishBatch(query);
                if (!CollectionUtils.isEmpty(replenishBatchList))
                {
                    for (ReplenishContractEntity replenish : replenishList)
                    {
                        ContractReplenishModel contractReplenishModel = new ContractReplenishModel();
                        for (ReplenishBatchEntity batch : replenishBatchList)
                        {
                            ReplenishBatchModel replenishBatchModel = new ReplenishBatchModel();
                            replenishBatchModel.setReplenishBatch(batch);
                            if (StringUtils.isNotEmpty(batch.getBatchItems()))
                            {
                                List<BatchItemModel> batchItemModelList = JsonMapper.nonEmptyMapper()
                                        .getMapper()
                                        .readValue(batch.getBatchItems(),
                                                new TypeReference<List<BatchItemModel>>()
                                                {
                                                });
                                replenishBatchModel.setReplenishBatchItems(batchItemModelList);
                            }
                            contractReplenishModel.getBatchItems()
                                    .add(replenishBatchModel);
                        }
                        contractReplenishModel.setReplenishContract(replenish);
                        contractModel.getReplenishList()
                                .add(contractReplenishModel);
                    }
                }
                
            }
        }
        else
        {
            contractModel.setReplenishList(null);
        }
    }
    
    private String[] projectRecordIds(
            List<ReplenishContractEntity> replenishList)
    {
        String[] recordIds = new String[replenishList.size()];
        for (int i = 0; i < replenishList.size(); i++)
        {
            recordIds[i] = replenishList.get(i).getId();
        }
        return recordIds;
    }
    
    private void getContractModifies(ContractModel contractModel,
            ContractEntity contract) throws IOException, JsonParseException,
            JsonMappingException
    {
        String modifyRecordIds = this.recordIdArr(contract.getContractNo(), "1");// 获取变更备案
        if (!StringUtils.isEmpty(modifyRecordIds))
        {
            // 变更合同主体
            QueryCondition<ModifyBatchEntity> modifyCondition = new QueryCondition<ModifyBatchEntity>();
            modifyCondition.addCondition("recordIds", modifyRecordIds.trim());// 变更备案ID
            modifyCondition.addCondition("contractId", contract.getContractNo());
            List<ModifyContractEntity> modifyList = contractModifyDao.queryModify(modifyCondition);
            
            if (!CollectionUtils.isEmpty(modifyList))
            {
                String[] modifyIds = projectModifyIds(modifyList);
                List<ModifyItemEntity> modifyItems = contractModifyItemDao.queryItemsByModifyIds(modifyIds);
                QueryCondition<ModifyBatchEntity> query = new QueryCondition<ModifyBatchEntity>();
                query.addCondition("modifyIds", modifyIds);
                List<ModifyBatchEntity> modifyBatches = contractModifyBatchDao.queryBacths(query);
                
                for (ModifyContractEntity modify : modifyList)
                {
                    ContractModifyModel cmm = new ContractModifyModel();
                    ModifyBatchModel modifyBatchModel = new ModifyBatchModel();
                    getModifyItems(modifyItems, modify, cmm);
                    getModifyBatches(modifyBatches,
                            modify,
                            cmm,
                            modifyBatchModel);
                    cmm.setModifyContract(modify);
                    contractModel.getModifyList().add(cmm);
                }
                
            }
            else
            {
                contractModel.setModifyList(null);
            }
            
        }
    }
    
    private void getModifyBatches(List<ModifyBatchEntity> modifyBatches,
            ModifyContractEntity modify, ContractModifyModel cmm,
            ModifyBatchModel modifyBatchModel) throws IOException,
            JsonParseException, JsonMappingException
    {
        for (ModifyBatchEntity batch : modifyBatches)
        {
            if (batch.getModifyId().equals(modify.getId()))
            {
                List<BatchItemModel> batchItems = null;
                batchItems = JsonMapper.nonEmptyMapper()
                        .getMapper()
                        .readValue(batch.getBatchItems(),
                                new TypeReference<List<BatchItemModel>>()
                                {
                                });
                modifyBatchModel.setModifyBatchItems(batchItems);
                modifyBatchModel.setModifyBatch(batch);
                cmm.getModifyBatchList().add(modifyBatchModel);
            }
        }
    }
    
    private void getModifyItems(List<ModifyItemEntity> modifyItems,
            ModifyContractEntity modify, ContractModifyModel cmm)
    {
        for (ModifyItemEntity item : modifyItems)
        {
            if (item.getModifyId().equals(modify.getId()))
                cmm.getModifyItemList().add(item);
        }
    }
    
    private String[] projectModifyIds(List<ModifyContractEntity> modifyList)
    {
        String[] modifyIds = new String[modifyList.size()];
        for (int i = 0; i < modifyList.size(); i++)
        {
            modifyIds[i] = modifyList.get(i).getId();
        }
        return modifyIds;
    }
    
    private void getBatches(ContractModel contractModel,
            List<ContractBatchEntity> batchList) throws IOException,
            JsonParseException, JsonMappingException
    {
        if (!CollectionUtils.isEmpty(batchList))
        {
            List<ContractBatchModel> batchModelList = contractModel.getBatchList();
            for (ContractBatchEntity batch : batchList)
            {
                ContractBatchModel batchModel = new ContractBatchModel();
                batchModel.setBatch(batch);
                List<BatchItemModel> beanList = null;
                beanList = JsonMapper.nonEmptyMapper()
                        .getMapper()
                        .readValue(batch.getBatchItems(),
                                new TypeReference<List<BatchItemModel>>()
                                {
                                });
                batchModel.setBatchItems(beanList);
                batchModelList.add(batchModel);
            }
            // contractModel.setBatchList(batchModelList);
        }
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ContractEntity getContractEntityByNo(String contractNo)
            throws ServiceException
    {
        try
        {
            if (StringUtils.isEmpty(contractNo))
            {
                return null;
            }
            QueryCondition<ContractEntity> condition = new QueryCondition<ContractEntity>();
            condition.addCondition("contractNo", contractNo);// 合同号
            List<ContractEntity> contractList = contractDao.queryContract(condition);
            if (contractList == null || contractList.size() == 0)
            {
                return null;
            }
            return contractList.get(0);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询合同实体信息错误", e);
        }
    }
    
    /**
     * 获取备案ID
     * 
     * @param contractId
     * @param type
     *            变更备案:1/补损备案:2
     * @return
     */
    public String recordIdArr(String contractNo, String type)
    {
        QueryCondition<RecordEntity> qc = new QueryCondition<RecordEntity>();
        qc.addCondition("contractNo", contractNo);// 合同号
        qc.addCondition("recordType", type);// 备案状态
        List<RecordEntity> record = recordDao.queryRecord(qc);
        String recordIds = "";
        if (record != null && record.size() > 0)
        {
            for (Iterator<RecordEntity> iterator = record.iterator(); iterator.hasNext();)
            {
                RecordEntity recordEntity = (RecordEntity) iterator.next();
                recordIds += "'" + recordEntity.getRecordNo() + "',";
            }
            recordIds = recordIds.substring(0, recordIds.length() - 1);
            return recordIds;
        }
        else
        {
            return "";
        }
        
    }
    
    /**
     * 查询合同列表
     */
    @Override
    @Transactional(readOnly = true)
    public List<ContractModel> queryContracts(ContractQuery query)
            throws ServiceException
    {
        try
        {
            if (query == null)
            {
                return null;
            }
            QueryCondition<ContractEntity> condition = new QueryCondition<ContractEntity>();
            condition.addCondition("keyword", query.getKeyword());
            condition.addCondition("contractNo", query.getContractNo());// 合同号
            condition.addCondition("recordNo", query.getRecordNo());// 备案号
            condition.addCondition("memberId", query.getMemberId());// 签订会员ＩＤ
            condition.addCondition("memberName", query.getMemberName());// 签订会员名称
            condition.addCondition("contractType", query.getContractType());// 合同类型
            condition.addCondition("refContractNo", query.getRefContractNo());// 关联合同号
            condition.addCondition("startCreateDate",
                    query.getStartCreateDate());// 开始签订时间
            condition.addCondition("endCreateDate", query.getEndCreateDate());// 结束签订合同号
            condition.addCondition("startRecordDate",
                    query.getStartRecordDate());// 开始备案时间
            condition.addCondition("endRecordDate", query.getEndRecordDate());// 结束备案时间
            condition.addCondition("confirmState", query.getConfirmState());// 确认状态
            condition.addCondition("state", query.getState());// 合同状态
            condition.addCondition("memberIdB", query.getMemberIdB());// 签订会员ＩＤ
            condition.addCondition("engAddress", query.getEngAddress());// 工程地址
            condition.setPage(query);
            
            List<ContractEntity> contractList = contractDao.queryContract(condition);
            query.setPage(condition);
            List<ContractModel> contractModelList = new ArrayList<ContractModel>();
            for (ContractEntity contractEntity : contractList)
            {
                // JsonMapper.nonEmptyMapper().fromJson(contractEntity.getStateLog(),
                // StateLogModel.class);//备案记录
                ContractModel cm = new ContractModel();
                cm.setContract(contractEntity);
                contractModelList.add(cm);
            }
            return contractModelList;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询合同信息错误", e);
        }
        
    }
    
    /**
     * 删除合同
     */
    @Override
    public void deleteContract(String id) throws ServiceException
    {
        try
        {
            ContractEntity ce = new ContractEntity();
            ce.setId(id);
            ce.setDeleteState(true);
            contractDao.updateContract(ce);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("删除合同出错", e);
        }
    }
    
    /**
     * 变更合同
     */
    @Override
    @Transactional
    public void changeContract(String recordId, ContractModifyModel model,
            List<ContractItemEntity> itemList, String contractIds,
            String changeIds, String contractBatchIds, String changeBatchIds)
            throws ServiceException
    {
        try
        {
            ModifyContractEntity mec = model.getModifyContract();
            if (itemList != null)
            {
                contractItemDao.updateItem(itemList);
            }
            if (mec != null)
            {
                contractModifyDao.addModify(mec);
                if (mec.getId() != null)
                {
                    // 变更条目
                    List<ModifyItemEntity> mieList = new ArrayList<ModifyItemEntity>();
                    if (!CollectionUtils.isEmpty(model.getModifyItemList()))
                    {
                        for (Iterator<ModifyItemEntity> iterator = model.getModifyItemList()
                                .iterator(); iterator.hasNext();)
                        {
                            ModifyItemEntity mie = iterator.next();
                            mie.setModifyId(mec.getId());
                            mie.setUpdateState(0);
                            mieList.add(mie);
                        }
                        contractModifyItemDao.addItems(mieList);
                    }
                    // 变更批次
                    List<ModifyBatchModel> mbmList = model.getModifyBatchList();
                    List<ModifyBatchEntity> mbeList = new ArrayList<ModifyBatchEntity>();
                    if (!CollectionUtils.isEmpty(mbmList))
                    {
                        for (ModifyBatchModel modifyBatchEntity : mbmList)
                        {
                            ModifyBatchEntity mbe = modifyBatchEntity.getModifyBatch();
                            String json = JsonMapper.nonEmptyMapper()
                                    .toJson(modifyBatchEntity.getModifyBatchItems());
                            mbe.setBatchItems(json);
                            mbe.setReplenishState(0);
                            mbe.setUpdateState(0);
                            mbe.setPayState(0);
                            mbe.setWarehouseState(0);
                            mbe.setModifyId(mec.getId());
                            mbeList.add(mbe);
                        }
                        contractModifyBatchDao.addBatchs(mbeList);
                    }
                    
                }
            }
            RecordEntity re = new RecordEntity();
            re.setId(recordId);
            re.setState(RecordStateEnum.BINDING);
            recordDao.updateRecord(re);
            // 更新变更信息
            if (StringUtils.isNotEmpty(contractIds))
            {
                contractIds = contractIds.substring(0, contractIds.length() - 1);
                String[] contractIdArr = contractIds.split(",");
                List<ContractItemEntity> cilist = new ArrayList<ContractItemEntity>();
                for (String string : contractIdArr)
                {
                    ContractItemEntity ci = new ContractItemEntity();
                    ci.setId(string);
                    ci.setUpdateState(1);
                    cilist.add(ci);
                }
                contractItemDao.updateItem(cilist);
            }
            if (StringUtils.isNotEmpty(changeIds))
            {
                changeIds = changeIds.substring(0, changeIds.length() - 1);
                String[] changeIdsArr = changeIds.split(",");
                List<ModifyItemEntity> milist = new ArrayList<ModifyItemEntity>();
                for (String string : changeIdsArr)
                {
                    ModifyItemEntity ci = new ModifyItemEntity();
                    ci.setId(string);
                    ci.setUpdateState(1);
                    milist.add(ci);
                }
                contractModifyItemDao.updateItems(milist);
            }
            // 批次变更状态
            if (StringUtils.isNotEmpty(changeBatchIds))
            {// 变更批次
                changeBatchIds = changeBatchIds.substring(0,
                        changeBatchIds.length() - 1);
                String[] batchId1sArr = changeBatchIds.split(",");
                List<ModifyBatchEntity> milist = new ArrayList<ModifyBatchEntity>();
                for (String string : batchId1sArr)
                {
                    ModifyBatchEntity mb = new ModifyBatchEntity();
                    mb.setId(string);
                    mb.setUpdateState(1);
                    milist.add(mb);
                }
                contractModifyBatchDao.updateItems(milist);
            }
            // 批次变更状态
            if (StringUtils.isNotEmpty(contractBatchIds))
            {// 合同批次
                contractBatchIds = contractBatchIds.substring(0,
                        contractBatchIds.length() - 1);
                String[] batchIdArr = contractBatchIds.split(",");
                List<ContractBatchEntity> cblist = new ArrayList<ContractBatchEntity>();
                for (String string : batchIdArr)
                {
                    ContractBatchEntity cb = new ContractBatchEntity();
                    cb.setId(string);
                    cb.setUpdateState(1);
                    cblist.add(cb);
                }
                contractBatchDao.updateBatchs(cblist);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("变更合同信息错误", e);
        }
    }
    
    /**
     * 补损合同
     */
    @Override
    @Transactional
    public void suppContract(String recordId, String contractId,
            List<ReplenishBatchModel> batchList,
            ReplenishContractEntity replenishContract) throws ServiceException
    {
        try
        {
            Assert.notEmpty(batchList);
            Assert.notNull(replenishContract);
            RecordEntity record = recordDao.getRecord(recordId);
            Assert.hasText(record.getRfidNo());
            ContractModel cm = this.getContractModelByContractNo(contractId);
            Assert.notNull(cm);
            // 更新补损状态
            String[] rfidNoArr = record.getRfidNo().split(",");
            for (String rfidNo : rfidNoArr)
            {
                ContractBatchEntity batch = contractBatchDao.getBacthsByRfid(rfidNo);
                if (batch != null)
                {
                    if (batch.getType() == 1)
                    {
                        ContractBatchEntity cb = new ContractBatchEntity();
                        cb.setId(batch.getId());
                        cb.setReplenishState(1);
                        contractBatchDao.updateBatch(batch);
                    }
                    else if (batch.getType() == 2)
                    {
                        ModifyBatchEntity mb = new ModifyBatchEntity();
                        mb.setId(batch.getId());
                        mb.setReplenishState(1);
                        contractModifyBatchDao.updateBatch(mb);
                    }
                    else if (batch.getType() == 3)
                    {
                        ReplenishBatchEntity rb = new ReplenishBatchEntity();
                        rb.setId(batch.getId());
                        rb.setReplenishState(1);
                        contractReplenishBatchDao.updateBatch(rb);
                    }
                }
            }
            contractReplenishDao.addReplenish(replenishContract);
            // 补损批次
            List<ReplenishBatchEntity> list = new ArrayList<ReplenishBatchEntity>();
            for (ReplenishBatchModel replenishBatchModel : batchList)
            {
                String json = JsonMapper.nonEmptyMapper()
                        .toJson(replenishBatchModel.getReplenishBatchItems());
                ReplenishBatchEntity rb = replenishBatchModel.getReplenishBatch();
                rb.setBatchItems(json);
                rb.setRfidNo(record.getRfidNo());// 添加补损RFID到批次
                rb.setReplenishState(0);
                rb.setPayState(0);
                rb.setWarehouseState(0);
                rb.setReplenishId(replenishContract.getId());
                rb.setNoType(contractId + "-");
                list.add(rb);
            }
            contractReplenishBatchDao.addReplenishBatch(list);
            
            record.setState(RecordStateEnum.BINDING);
            recordDao.updateRecord(record);
            // 更新合同有效批次条目
            ContractEntity contract = cm.getContract();
            contract.setEffectiveBatch(contract.getEffectiveBatch() + 1);
            contractDao.updateContract(contract);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("补损合同信息错误", e);
        }
    }
    
    /**
     * 确认合同
     */
    @Override
    @Transactional
    public void confirmContract(String contractId) throws ServiceException
    {
        try
        {
            Assert.hasText(contractId, "合同号不能为空");
            ContractEntity centity = contractDao.getContract(contractId);
            Assert.notNull(centity, "该合同不存在");
            contractStatefsm.setCurrentState(centity.getState());
            contractStatefsm.fire(centity.getState().toString(), centity);
            // centity.setConfirmState(ContractSureStateEnum.noaffirm);
            // contractDao.updateContract(ce);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("审核合同错误", e);
        }
    }
    
    @Override
    @Transactional
    public void checkContract(String contractId) throws ServiceException
    {
        try
        {
            Assert.hasText(contractId, "合同号不能为空");
            ContractEntity centity = contractDao.getContract(contractId);
            Assert.notNull(centity, "该合同不存在");
            contractStatefsm.setCurrentState(centity.getState());
            contractStatefsm.fire(centity.getState().toString(), centity);
            // centity.setConfirmState(ContractSureStateEnum.noaffirm);
            // contractDao.updateContract(centity);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("审核合同错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public ContractModel getContractModelByContractNo(String contractNo)
    {
        try
        {
            if (StringUtils.isEmpty(contractNo))
            {
                return null;
            }
            ContractQuery query = new ContractQuery();
            query.setContractNo(contractNo);
            List<ContractModel> res = queryContracts(query);
            if (res != null && res.size() > 0)
            {
                ContractModel cm = getContract(res.get(0).getContract().getId());
                return cm;
            }
            return null;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取合同信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public ContractBatchModel getContractBatch(String refContractNo,
            String rfidNo, Integer type)
    {
        try
        {
            List<ContractEntity> contractList = getContractByRefContractNo(refContractNo);
            ContractBatchModel batchModel = new ContractBatchModel();
            ContractBatchEntity batch = contractBatchDao.getBacthsByRfid(rfidNo);
            if (batch == null)
            {
                return null;
            }
            if (contractList == null || contractList.size() == 0)
            {
                return null;
            }
            for (ContractEntity contractEntity : contractList)
            {
                if (contractEntity == null)
                {
                    continue;
                }
                if (contractEntity.getType().getId() == type)
                {
                    batch.setContractId(contractEntity.getContractNo());
                }
            }
            List<BatchItemModel> beanList = null;
            beanList = JsonMapper.nonEmptyMapper()
                    .getMapper()
                    .readValue(batch.getBatchItems(),
                            new TypeReference<List<BatchItemModel>>()
                            {
                            });
            batchModel.setBatchItems(beanList);
            batchModel.setBatch(batch);
            return batchModel;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取合同信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ModifyBatchModel> getContractModifyBatch(String contractNo,
            String rfid)
    {
        try
        {
            QueryCondition<ModifyBatchEntity> condition = new QueryCondition<ModifyBatchEntity>();
            condition.addCondition("contractId", contractNo);// 合同号
            condition.addCondition("rfidNo", rfid);// 备案号
            List<ModifyBatchEntity> batchList = contractModifyBatchDao.queryBacths(condition);
            List<ModifyBatchModel> newBatchModelLIst = new ArrayList<ModifyBatchModel>();
            if (batchList != null && batchList.size() > 0)
            {
                for (int i = 0; i < batchList.size(); i++)
                {
                    ModifyBatchEntity batch = batchList.get(i);
                    ModifyBatchModel batchModel = new ModifyBatchModel();
                    batchModel.setModifyBatch(batch);
                    List<BatchItemModel> beanList = null;
                    beanList = JsonMapper.nonEmptyMapper()
                            .getMapper()
                            .readValue(batch.getBatchItems(),
                                    new TypeReference<List<BatchItemModel>>()
                                    {
                                    });
                    batchModel.setModifyBatchItems(beanList);
                    newBatchModelLIst.add(batchModel);
                }
            }
            return newBatchModelLIst;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取修改批次信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReplenishBatchModel> getContractReplenishBatch(
            String contractNo, String rfid)
    {
        try
        {
            QueryCondition<ReplenishBatchEntity> condition = new QueryCondition<ReplenishBatchEntity>();
            condition.addCondition("contractId", contractNo);// 合同号
            condition.addCondition("rfidNo", rfid);// 备案号
            List<ReplenishBatchEntity> batchList = contractReplenishBatchDao.queryReplenishBatch(condition);
            List<ReplenishBatchModel> newBatchModelLIst = new ArrayList<ReplenishBatchModel>();
            if (batchList != null && batchList.size() > 0)
            {
                for (int i = 0; i < batchList.size(); i++)
                {
                    ReplenishBatchEntity batch = batchList.get(i);
                    ReplenishBatchModel batchModel = new ReplenishBatchModel();
                    batchModel.setReplenishBatch(batch);
                    List<BatchItemModel> beanList = null;
                    beanList = JsonMapper.nonEmptyMapper()
                            .getMapper()
                            .readValue(batch.getBatchItems(),
                                    new TypeReference<List<BatchItemModel>>()
                                    {
                                    });
                    batchModel.setReplenishBatchItems(beanList);
                    newBatchModelLIst.add(batchModel);
                }
            }
            return newBatchModelLIst;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取补损批次信息错误", e);
        }
    }
    
    @Override
    @Transactional
    public void modifyBatch(ContractBatchModel model) throws ServiceException
    {
        try
        {
            String batchItems = JsonMapper.nonEmptyMapper()
                    .toJson(model.getBatchItems());
            ContractBatchEntity batch = model.getBatch();
            batch.setBatchItems(batchItems);
            List<ContractBatchEntity> list = new ArrayList<ContractBatchEntity>();
            list.add(batch);
            contractBatchDao.updateBatchs(list);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("批次修改错误", e);
        }
        
    }
    
    /**
     * 启用rfid(新增批次)
     */
    @Override
    @Transactional
    public void addBatch(ContractBatchModel model, String id,
            MemberEntity member) throws ServiceException
    {
        try
        {
            if (model == null)
            {
                throw new ServiceException("系统错误");
            }
            ContractBatchEntity batch = model.getBatch();
            // 判断是否能启用
            int oldBatchCount = 0;
            ContractModel contract = getContractModelByContractNo(batch.getContractId());
            if (contract == null)
            {
                throw new ServiceException("系统错误");
            }
            if (contract.getBatchList() != null)
            {
                oldBatchCount = contract.getBatchList().size();
            }
            if (contract.getContract().getBatchCount() <= oldBatchCount)
            {
                throw new ServiceException("本合同批次已经全部启用完毕！");
            }
            
            // 执行启用
            String batchItems = null;
            if (model.getBatchItems() != null)
            {
                batchItems = JsonMapper.nonEmptyMapper()
                        .toJson(model.getBatchItems());
            }
            batch.setWarehouseState(0);
            batch.setBatchItems(batchItems);
            batch.setUpdateState(0);
            batch.setReplenishState(0);
            batch.setPayState(0);
            List<ContractBatchEntity> list = new ArrayList<ContractBatchEntity>();
            list.add(batch);
            contractBatchDao.addBatchs(list);
            
            // 更新当前合同的有效批次
            ContractEntity contractEn = contract.getContract();
            contractEn.setEffectiveBatch(contractEn.getEffectiveBatch() + 1);
            contractDao.updateContract(contractEn);
            
            LogisticsRfidEntity logistics = new LogisticsRfidEntity();
            logistics.setId(id);
            logistics.setContractNo(batch.getContractId());
            logistics.setRfidState(RfidStateEnum.USED);
            logistics.setBatchNo(batch.getBatchNo());
            logistics.setIsLossBatch(false);
            logisticsRfidService.updateLogistics(logistics);
            
            // 申请关联
            LogisticsRefEntity ref = new LogisticsRefEntity();
            ref.setRfidNo(batch.getRfidNo());
            ref.setMemberNo(member.getMemberNo());
            ref.setMemberName(member.getName());
            if (member.getType().getId() == 1)
            {
                ref.setRfidType(RfidTypeEnum.GLASS);
            }
            else if (member.getType().getId() == 2)
            {
                ref.setRfidType(RfidTypeEnum.EXTRUSIONS);
            }
            ref.setType(AssociationTypesEnum.APPLY);
            ref.setBatchNo(batch.getBatchNo());
            ref.setApplyDate(new Date());
            ref.setContractNo(batch.getContractId());
            ref.setState(AuditStateEnum.NO_APPROVAL);
            logisticsRefService.add(ref);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("启用rfid错误", e);
        }
        
    }
    
    /**
     * 补损物流rfid
     */
    @Override
    @Transactional
    public void updateRfidLoss(String rfidNo, String contractNo,
            String batchNo, MemberEntity member, String newRfid)
            throws ServiceException
    {
        try
        {
            // ContractBatchModel batchModel = getBatchByRfid(rfidNo);
            // if (list == null || list.size() == 0) {
            // throw new ServiceException("该RFID没有对应的批次！");
            // }
            // ContractBatchEntity batchEntity = batchModel.getBatch();
            // if (batchEntity == null) {
            // throw new ServiceException("该RFID没有对应的批次！");
            // }
            // List<BatchItemModel> batchItems = batchModel.getBatchItems();
            // if (batchItems == null || batchItems.size() == 0) {
            // throw new ServiceException("该RFID没有对应的批次条目信息！");
            // }
            List<ContractBatchEntity> list = contractBatchDao.getAllBacthsByRfid(rfidNo);
            if (list == null || list.size() == 0)
            {
                throw new ServiceException("该RFID没有对应的批次！");
            }
            
            for (ContractBatchEntity contractBatch : list)
            {
                if (contractBatch == null)
                {
                    continue;
                }
                if (contractBatch.getType() == 1)
                {
                    contractBatch.setRfidNo(newRfid);
                    contractBatchDao.updateBatch(contractBatch);
                }
                else if (contractBatch.getType() == 2)
                {
                    ModifyBatchEntity modifyBatch = new ModifyBatchEntity();
                    contractBatch.setRfidNo(newRfid);
                    modifyBatch.setId(contractBatch.getId());
                    modifyBatch.setRfidNo(newRfid);
                    contractModifyBatchDao.updateBatch(modifyBatch);
                }
                else if (contractBatch.getType() == 3)
                {
                    ReplenishBatchEntity replenishBatch = new ReplenishBatchEntity();
                    replenishBatch.setId(contractBatch.getId());
                    replenishBatch.setNewRfidNo(newRfid);
                    contractReplenishBatchDao.updateBatch(replenishBatch);
                }
            }
            // 更新旧的RFID
            LogisticsRfidEntity logistics = logisticsRfidService.getLogisticsByNo(rfidNo);
            if (logistics.getRfidState().equals(RfidStateEnum.DAMAGED))
            {
                throw new ServiceException("此RFID已经被补损，不能再次补损");
            }
            logistics.setRfidState(RfidStateEnum.DAMAGED);
            logistics.setReplenishNo(newRfid);
            logisticsRfidService.updateLogistics(logistics);
            // 更新新的RFID
            LogisticsRfidEntity newLogisRfid = logisticsRfidService.getLogisticsByNo(newRfid);
            if (newLogisRfid == null)
            {
                throw new ServiceException("新RFID信息不存在");
            }
            if (!newLogisRfid.getRfidState().equals(RfidStateEnum.UN_USED))
            {
                throw new ServiceException("新RFID状态不是未使用状态，不能用于补损");
            }
            newLogisRfid.setContractNo(contractNo);
            newLogisRfid.setRfidState(RfidStateEnum.USED);
            newLogisRfid.setBatchNo(batchNo);
            newLogisRfid.setIsLossBatch(logistics.getIsLossBatch());
            logisticsRfidService.updateLogistics(newLogisRfid);
            
            // 申请关联
            LogisticsRefEntity ref = new LogisticsRefEntity();
            ref.setRfidNo(newRfid);
            ref.setMemberNo(member.getMemberNo());
            ref.setMemberName(member.getName());
            if (member.getType().equals(MemberTypeEnum.GLASSFACTORY))
            {
                ref.setRfidType(RfidTypeEnum.GLASS);
            }
            else if (member.getType().equals(MemberTypeEnum.GENRESFACTORY))
            {
                ref.setRfidType(RfidTypeEnum.EXTRUSIONS);
            }
            ref.setType(AssociationTypesEnum.RFID_ADD);
            ref.setBatchNo(batchNo);
            ref.setApplyDate(new Date());
            ref.setContractNo(contractNo);
            ref.setState(AuditStateEnum.NO_APPROVAL);
            ref.setReplenishRfid(rfidNo);
            logisticsRefService.add(ref);
            
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("RFID标签补损错误", e);
        }
    }
    
    /**
     * 采购合同补损
     */
    @Override
    @Transactional
    public void updateContractLoss(String rfidNos, String contractNo,
            String batchId, MemberEntity member, String newRfid)
            throws ServiceException
    {
        try
        {
            // member = null;
            if (StringUtils.isEmpty(rfidNos))
            {
                throw new ServiceException("rfid编号不能为空");
            }
            ReplenishBatchEntity batch = contractReplenishBatchDao.getBatch(batchId);
            if (batch == null)
            {
                throw new ServiceException("补损批次不存在");
            }
            batch.setNewRfidNo(newRfid);
            contractReplenishBatchDao.updateBatch(batch);
            //
            // 更新旧的RFID
            // String[] rfidList = rfidNos.split(",");
            // for (int i = 0; i < rfidList.length; i++) {
            // if (StringUtils.isEmpty(rfidList[i])) {
            // continue;
            // }
            // LogisticsRfidEntity logistics = logisticsRfidService
            // .getLogisticsByNo(rfidList[i]);
            // if (logistics.getRfidState().equals(RfidStateEnum.damaged)) {
            // throw new ServiceException("此RFID已经被补损，不能再次补损");
            // }
            // logistics.setReplenishNo(newRfid);
            // logisticsRfidService.updateLogistics(logistics);
            // }
            // 申请关联
            LogisticsRefEntity ref = new LogisticsRefEntity();
            ref.setRfidNo(newRfid);
            ref.setMemberNo(member.getMemberNo());
            ref.setMemberName(member.getName());
            if (member.getType().equals(MemberTypeEnum.GLASSFACTORY))
            {
                ref.setRfidType(RfidTypeEnum.GLASS);
            }
            else if (member.getType().equals(MemberTypeEnum.GENRESFACTORY))
            {
                ref.setRfidType(RfidTypeEnum.EXTRUSIONS);
            }
            ref.setType(AssociationTypesEnum.CONTRACTOR_ADD);
            ref.setBatchNo(batch.getBatchNo() + "");
            ref.setApplyDate(new Date());
            ref.setContractNo(contractNo);
            ref.setState(AuditStateEnum.NO_APPROVAL);
            ref.setReplenishRfid(rfidNos);
            logisticsRefService.add(ref);
            
            // 更新新的RFID
            LogisticsRfidEntity newLogisRfid = logisticsRfidService.getLogisticsByNo(newRfid);
            if (newLogisRfid == null)
            {
                throw new ServiceException("新RFID信息不存在");
            }
            if (!newLogisRfid.getRfidState().equals(RfidStateEnum.UN_USED))
            {
                throw new ServiceException("新RFID状态不是未使用状态，不能用于补损");
            }
            newLogisRfid.setContractNo(contractNo);
            newLogisRfid.setRfidState(RfidStateEnum.USED);
            newLogisRfid.setBatchNo(batch.getBatchNo() + "");
            newLogisRfid.setIsLossBatch(true);
            logisticsRfidService.updateLogistics(newLogisRfid);
            
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("采购合同补损错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public String getReplenish(String contractNo)
    {
        QueryCondition<ReplenishContractEntity> query = new QueryCondition<ReplenishContractEntity>();
        query.addCondition("contractId", contractNo);
        List<ReplenishContractEntity> replenishList = contractReplenishDao.queryReplenish(query);
        if (replenishList.size() > 0 && replenishList != null)
        {
            ReplenishContractEntity replenish = replenishList.get(0);
            if (replenish.getId() != null)
            {
                QueryCondition<ReplenishBatchEntity> query2 = new QueryCondition<ReplenishBatchEntity>();
                query2.addCondition("recordIds", replenish.getId());
                List<ReplenishBatchEntity> replenisBatch = contractReplenishBatchDao.queryReplenishBatch(query2);
                return replenisBatch.get(0).getNewRfidNo();
            }
        }
        return null;
    }
    
    @Override
    @Transactional
    public int getContractByZhaobiaoContractNo(String contractNo,
            String memberId)
    {
        try
        {
            ContractQuery query = new ContractQuery();
            query.setContractNo(contractNo);
            query.setMemberIdB(memberId);
            query.setContractType("0");
            List<ContractModel> res = queryContracts(query);
            return res.size();
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取合同信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContractEntity> getContractByRefContractNo(String refContractNo)
            throws ServiceException
    {
        try
        {
            ContractQuery query = new ContractQuery();
            query.setRefContractNo(refContractNo);
            List<ContractModel> list = queryContracts(query);
            List<ContractEntity> ContractList = new ArrayList<ContractEntity>();
            for (ContractModel contractModel : list)
            {
                ContractList.add(contractModel.getContract());
            }
            return ContractList;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取合同信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ContractItemEntity> getContractItem(String contractNo)
            throws ServiceException
    {
        try
        {
            List<ContractItemEntity> itemList = contractItemDao.queryItems(contractNo);// 产品条目
            return itemList;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取合同信息错误", e);
        }
    }
    
    @Override
    @Transactional
    public void startWindowRfid(Integer startNum, String refContractNo,
            String minRfid, String maxRfid, String gRfid, String lRfid,
            WindowTypeEnum windowType) throws ServiceException
    {
        try
        {
            ContractModel refContract = getContractModelByContractNo(refContractNo);
            if (refContract == null)
            {
                throw new ServiceException("招标合同不存在");
            }
            List<ContractItemEntity> items = refContract.getItemList();
            if (items == null || items.size() == 0)
            {
                throw new ServiceException("招标合同条目不存在");
            }
            float itemQuantity = refContract.getContract().getItemQuantity();
            float useQuantity = refContract.getContract().getUseQuantity();
            long count = (long) itemQuantity;
            long useCount = (long) useQuantity;
            windowRfidService.startWindowRfid(count,
                    useCount,
                    refContractNo,
                    startNum,
                    gRfid,
                    lRfid,
                    windowType);
            // windowRfidService.startWindowRfid(count, useCount, refContractNo,
            // minRfid, maxRfid, gRfid, lRfid, windowType);
            Map<String, Object> map = new HashMap<>();
            map.put("contractNo", refContractNo);
            map.put("usequantity", (float) startNum + useQuantity);
            map.put("oldUseQuantity", useQuantity);
            int index = contractDao.updateContractRfid(map);
            if (index <= 0)
            {
                throw new ServiceException("请求超时，请重新启用");
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage(), e);
        }
        
    }
    
    /**
     * 跟据rfid 获取补损批次
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<ReplenishBatchEntity> getReplenishByNewRfid(String batchNo,
            String newRfid)
    {
        try
        {
            if (StringUtils.isEmpty(newRfid))
            {
                return null;
            }
            QueryCondition<ReplenishBatchEntity> query = new QueryCondition<ReplenishBatchEntity>();
            query.addCondition("newRfid", newRfid);
            query.addCondition("batchNo", batchNo);
            List<ReplenishBatchEntity> batchList = contractReplenishBatchDao.queryReplenishBatch(query);
            return batchList;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        
    }
    
    /**
     * 根据RFID获取批次
     */
    @Override
    public ContractBatchModel getBatchByRfid(String rfidNo)
            throws ServiceException, SQLException
    {
        ContractBatchModel batchModel = new ContractBatchModel();
        ContractBatchEntity batch = contractBatchDao.getBacthsByRfid(rfidNo);
        LogisticsRfidQuery query = new LogisticsRfidQuery();
        query.setRfidNo(rfidNo);
        List<LogisticsRfidEntity> lr = logisticsRfidService.queryLogistics(query);
        if (batch != null)
        {
            if (lr != null && lr.size() > 0)
            {
                LogisticsRfidEntity logistics = lr.get(0);
                batch.setContractId(logistics.getContractNo());
            }
            batchModel.setBatch(batch);
            List<BatchItemModel> batchList = JsonMapperUtil.getBatchItems(batch.getBatchItems());
            batchModel.setBatchItems(batchList);
        }
        
        return batchModel;
    }
    
    /**
     * 根据RFID删除批次
     */
    @Override
    @Transactional
    public void deleteBatch(String rfidNo, String contractNo)
            throws ServiceException
    {
        try
        {
            QueryCondition<ContractBatchEntity> query = new QueryCondition<ContractBatchEntity>();
            query.addCondition("rfidNo", rfidNo);
            List<ContractBatchEntity> batchList = contractBatchDao.queryBacths(query);
            if (batchList != null && batchList.size() > 0)
            {
                ContractBatchEntity batch = batchList.get(0);
                if (batch.getUpdateState() != null
                        && batch.getUpdateState() == 1)
                {
                    throw new ServiceException("该批次已被变更,不能删除!");
                }
                if (batch.getReplenishState() != null
                        && batch.getReplenishState() == 1)
                {
                    throw new ServiceException("该批次已被补损,不能删除!");
                }
                QueryCondition<ContractBatchEntity> batchQuery = new QueryCondition<ContractBatchEntity>();
                batchQuery.addCondition("contractId", contractNo);
                batchQuery.addCondition("batchNo",
                        Integer.valueOf(batch.getBatchNo()) + 1);
                List<ContractBatchEntity> list = contractBatchDao.queryBacths(batchQuery);
                if (list != null && list.size() > 0)
                {
                    throw new ServiceException("该批次之后已存在新的批次，不能删除");
                }
                contractBatchDao.deleteBatchs(batch.getId());
            }
            else
            {
                throw new ServiceException("找不到该批次");
            }
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("删除批次失败!", e);
        }
    }
    
    @Override
    @Transactional
    public void deleteLogisticsRef(String id) throws ServiceException
    {
        try
        {
            LogisticsRefEntity ref = logisticsRefService.getRef(id);
            if (ref == null)
            {
                throw new ServiceException("物流RFID关联申请不存在");
            }
            if (ref.getState().equals(AuditStateEnum.APPROVAL))
            {
                throw new ServiceException("物流RFID关联已审核");
            }
            // 判断物流关联是否能回滚
            LogisticsRfidEntity rfid = logisticsRfidService.getLogisticsByNo(ref.getRfidNo());
            if (rfid != null)
            {
                if (rfid.getRfidState().equals(RfidStateEnum.DAMAGED))
                {
                    throw new ServiceException("该物流RFID已被补损，不能删除");
                }
                WindowRfidQuery query = new WindowRfidQuery();
                query.setRfid(ref.getRfidNo());
                List<WindowRfidEntity> winRfidList = windowRfidService.queryWindowRfid(query);
                if (winRfidList != null && winRfidList.size() > 0)
                {
                    throw new ServiceException("该物流RFID已被门窗标签关联！");
                }
                LogisticsRefQuery refQuery = new LogisticsRefQuery();
                refQuery.setReplenishRfid(ref.getRfidNo());
                List<LogisticsRefEntity> refList = logisticsRefService.query(refQuery);
                if (refList != null && refList.size() > 0)
                {
                    throw new ServiceException("该物流RFID已被采购合同补损过！");
                }
            }
            if (ref.getType().equals(AssociationTypesEnum.APPLY))
            {
                // 删除批次
                self.deleteBatch(ref.getRfidNo(), ref.getContractNo());
                ContractEntity contract = getContractEntityByNo(ref.getContractNo());
                contract.setEffectiveBatch(contract.getEffectiveBatch() - 1);
                contractDao.updateContract(contract);
                
                // 回滚RFID
                LogisticsRfidEntity l = logisticsRfidService.getLogisticsByNo(ref.getRfidNo());
                l.setRfidState(RfidStateEnum.UN_USED);
                l.setContractNo("");
                l.setBatchNo("");
                l.setIsLossBatch(false);
                logisticsRfidService.updateLogistics(l);
                
            }
            else if (ref.getType().equals(AssociationTypesEnum.RFID_ADD))
            {
                ContractBatchModel batchModel = getBatchByRfid(ref.getRfidNo());
                if (batchModel == null)
                {
                    throw new ServiceException("该RFID没有对应的批次！");
                }
                ContractBatchEntity batchEntity = batchModel.getBatch();
                if (batchEntity == null)
                {
                    throw new ServiceException("该RFID没有对应的批次！");
                }
                List<BatchItemModel> batchItems = batchModel.getBatchItems();
                if (batchItems == null || batchItems.size() == 0)
                {
                    throw new ServiceException("该RFID没有对应的批次条目信息！");
                }
                if (batchModel.getBatch().getType() == 1)
                {
                    batchEntity.setRfidNo(ref.getReplenishRfid());
                    contractBatchDao.updateBatch(batchEntity);
                }
                else if (batchModel.getBatch().getType() == 2)
                {
                    ModifyBatchEntity modifyBatch = new ModifyBatchEntity();
                    modifyBatch.setId(batchEntity.getId());
                    modifyBatch.setRfidNo(ref.getReplenishRfid());
                    contractModifyBatchDao.updateBatch(modifyBatch);
                }
                else if (batchModel.getBatch().getType() == 3)
                {
                    ReplenishBatchEntity replenishBatch = new ReplenishBatchEntity();
                    replenishBatch.setId(batchEntity.getId());
                    replenishBatch.setNewRfidNo(ref.getReplenishRfid());
                    contractReplenishBatchDao.updateBatch(replenishBatch);
                }
                
                // 更新被补损的RFID
                LogisticsRfidEntity replenishRfid = logisticsRfidService.getLogisticsByNo(ref.getReplenishRfid());
                LogisticsRfidEntity logistics = logisticsRfidService.getLogisticsByNo(ref.getRfidNo());
                if (replenishRfid == null)
                {
                    throw new ServiceException("被补损的RFID信息已不存在");
                }
                if (!replenishRfid.getRfidState().equals(RfidStateEnum.DAMAGED))
                {
                    throw new ServiceException("被补损的RFID已不是作废状态");
                }
                replenishRfid.setContractNo(ref.getContractNo());
                replenishRfid.setRfidState(RfidStateEnum.USED);
                replenishRfid.setReplenishNo("");
                replenishRfid.setBatchNo(batchModel.getBatch().getBatchNo());
                replenishRfid.setIsLossBatch(logistics.getIsLossBatch());
                RfidLog log = new RfidLog();
                log.setId(RfidStateEnum.DAMAGED.getId());
                log.setState(RfidStateEnum.DAMAGED.getName());
                replenishRfid.removeLog(log);
                logisticsRfidService.updateLogistics(replenishRfid);
                
                // 更新旧的RFID
                if (logistics.getRfidState().equals(RfidStateEnum.DAMAGED))
                {
                    throw new ServiceException("此RFID已经被补损，不能删除");
                }
                logistics.setRfidState(RfidStateEnum.UN_USED);
                logistics.setReplenishNo("");
                logistics.setContractNo("");
                logistics.setBatchNo("");
                logistics.setIsLossBatch(false);
                logisticsRfidService.updateLogistics(logistics);
            }
            else if (ref.getType().equals(AssociationTypesEnum.CONTRACTOR_ADD))
            {
                // 更新新的RFID
                LogisticsRfidEntity logisRfid = logisticsRfidService.getLogisticsByNo(ref.getRfidNo());
                if (logisRfid == null)
                {
                    throw new ServiceException("RFID信息不存在");
                }
                if (!logisRfid.getRfidState().equals(RfidStateEnum.USED))
                {
                    throw new ServiceException("RFID状态不是已使用状态");
                }
                logisRfid.setContractNo("");
                logisRfid.setRfidState(RfidStateEnum.UN_USED);
                logisRfid.setBatchNo("");
                logisRfid.setIsLossBatch(false);
                logisticsRfidService.updateLogistics(logisRfid);
                // 回滚批次
                List<ReplenishBatchEntity> batchList = getReplenishByNewRfid(ref.getBatchNo(),
                        ref.getRfidNo());
                if (batchList == null || batchList.size() == 0)
                {
                    throw new ServiceException("补损批次不存在");
                }
                for (ReplenishBatchEntity replenishBatch : batchList)
                {
                    if (replenishBatch == null)
                    {
                        continue;
                    }
                    replenishBatch.setNewRfidNo("");
                }
                contractReplenishBatchDao.updateReplenishBatch(batchList);
            }
            // 删除关联
            logisticsRefService.del(id);
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("删除物流RFID关联错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void deleteWindowRef(String id) throws ServiceException
    {
        try
        {
            WindowRefEntity ref = windowRefService.getWindowRfidRef(id);
            if (ref == null)
            {
                throw new ServiceException("门窗RFID关联申请不存在");
            }
            if (ref.getState().equals(AuditStateEnum.APPROVAL))
            {
                throw new ServiceException("门窗RFID关联已审核");
            }
            if (ref.getType().equals(LinkStateEnum.WINDOW_APPLY))
            {
                int startCount = 0;
                if (StringUtils.isNotEmpty(ref.getRfidNos()))
                {
                    String[] rfidNos = ref.getRfidNos().split(",");
                    // String maxRfidNo = ref.getMaxRfidNo();
                    WindowRfidQuery query = new WindowRfidQuery();
                    query.setRfidNos(rfidNos);
                    // query.setMinRfidNo(minRfidNo);
                    // query.setMaxRfidNo(maxRfidNo);
                    // query.setRfidState(RfidStateEnum.used.getId());
                    List<WindowRfidEntity> list = windowRfidService.queryWindowRfid(query);
                    if (list == null || list.size() == 0)
                    {
                        throw new ServiceException("门窗RFID标签不存在");
                    }
                    startCount = list.size();
                    for (Iterator<WindowRfidEntity> iterator = list.iterator(); iterator.hasNext();)
                    {
                        WindowRfidEntity windowRfid = iterator.next();
                        if (windowRfid == null)
                        {
                            continue;
                        }
                        if (!windowRfid.getRfidState()
                                .equals(RfidStateEnum.USED))
                        {
                            throw new ServiceException("编号为："
                                    + windowRfid.getRfidNo() + "的门窗RFID不是已使用状态");
                        }
                        windowRfid.setGlassRfid("");
                        windowRfid.setProfileRfid("");
                        windowRfid.setWindowType(null);
                        windowRfid.setRfidState(RfidStateEnum.UN_USED);
                    }
                    windowRfidService.batchUpdateWindowRfid(list.toArray(new WindowRfidEntity[list.size()]));
                    
                    // 将之前设置停用的剩余标签回滚到未停用，物流标签有值得除外
                    WindowRfidQuery query2 = new WindowRfidQuery();
                    query2.setRfidState(RfidStateEnum.DISABLE.getId());
                    query2.setContractNo(ref.getContractNo());
                    List<WindowRfidEntity> disableList = windowRfidService.queryWindowRfid(query2);
                    if (disableList != null && disableList.size() > 0)
                    {
                        for (Iterator<WindowRfidEntity> iterator = disableList.iterator(); iterator.hasNext();)
                        {
                            WindowRfidEntity disableRfid = iterator.next();
                            if (disableRfid == null)
                            {
                                continue;
                            }
                            if (StringUtils.isNotEmpty(disableRfid.getGlassRfid()))
                            {
                                iterator.remove();
                                continue;
                            }
                            disableRfid.setRfidState(RfidStateEnum.UN_USED);
                        }
                        windowRfidService.batchUpdateWindowRfid(disableList.toArray(new WindowRfidEntity[disableList.size()]));
                    }
                    // 回滚招标合同数量
                    ContractEntity contract = getContractEntityByNo(ref.getContractNo());
                    if (contract != null)
                    {
                        contract.setUseQuantity(contract.getUseQuantity()
                                - startCount);
                        contractDao.updateContract(contract);
                    }
                }
            }
            else if (ref.getType().equals(LinkStateEnum.RFID_LOSS))
            {
                WindowRfidEntity rfid = windowRfidService.getWindowRfidByNo(ref.getMinRfidNo());
                if (rfid == null)
                {
                    throw new ServiceException("补损的RFID不存在");
                }
                if (!rfid.getRfidState().equals(RfidStateEnum.USED))
                {
                    throw new ServiceException("补损的" + rfid.getRfidNo()
                            + "RFID不是已使用状态");
                }
                WindowRfidEntity replenishRfid = windowRfidService.getWindowRfidByNo(ref.getReplenishRfid());
                if (replenishRfid == null)
                {
                    throw new ServiceException("被补损的RFID不存在");
                }
                if (!replenishRfid.getRfidState().equals(RfidStateEnum.DAMAGED))
                {
                    throw new ServiceException("被补损的"
                            + replenishRfid.getRfidNo() + "RFID不是已破损状态");
                }
                // 还原被补损的RFID
                replenishRfid.setReplenishNo("");
                replenishRfid.setRfidState(RfidStateEnum.USED);
                RfidLog log = new RfidLog();
                log.setId(RfidStateEnum.DAMAGED.getId());
                log.setState(RfidStateEnum.DAMAGED.getName());
                replenishRfid.removeLog(log);
                windowRfidService.updateWindowRfid(replenishRfid);
                
                // 还原补损的RFID
                rfid.setRfidState(RfidStateEnum.UN_USED);
                rfid.setWindowType(null);
                rfid.setGlassRfid("");
                rfid.setProfileRfid("");
                windowRfidService.updateWindowRfid(rfid);
            }
            else if (ref.getType().equals(LinkStateEnum.WINDOW_LOSS))
            {
                String replenishRfids = ref.getReplenishRfid();
                WindowRfidQuery query = new WindowRfidQuery();
                query.setMinRfidNo(ref.getMinRfidNo());
                query.setMaxRfidNo(ref.getMaxRfidNo());
                query.setContractNo(ref.getContractNo());
                query.setRfidState(RfidStateEnum.UN_USED.getId());
                List<WindowRfidEntity> list = windowRfidService.queryWindowRfid(query);
                if (list == null || list.size() == 0)
                {
                    throw new ServiceException("补损的RFID不存在");
                }
                if (StringUtils.isNotEmpty(replenishRfids))
                {
                    String[] replenishRfidArr = replenishRfids.split(",");
                    if (list.size() != replenishRfidArr.length)
                    {
                        throw new ServiceException("补损的RFID数量与需要被补损的RFID数量不一致");
                    }
                    for (int i = 0; i < replenishRfidArr.length; i++)
                    {
                        WindowRfidEntity replenishRfid = windowRfidService.getWindowRfidByNo(replenishRfidArr[i]);
                        if (replenishRfid == null)
                        {
                            throw new ServiceException("编号为："
                                    + replenishRfidArr[i] + "的被补损RFID不存在");
                        }
                        if (!replenishRfid.getRfidState()
                                .equals(RfidStateEnum.DAMAGED))
                        {
                            throw new ServiceException("被补损的"
                                    + replenishRfid.getRfidNo()
                                    + "的RFID不是已破损状态");
                        }
                        WindowRfidEntity newRfid = list.get(i);
                        if (newRfid == null)
                        {
                            throw new ServiceException("补损的RFID不存在");
                        }
                        if (!newRfid.getRfidState().equals(RfidStateEnum.USED))
                        {
                            throw new ServiceException("补损的"
                                    + newRfid.getRfidNo() + "RFID不是已使用状态");
                        }
                        if (!newRfid.getContractNo()
                                .equals(replenishRfid.getContractNo()))
                        {
                            throw new ServiceException("编号为："
                                    + replenishRfidArr[i] + "的被补损RFID与编号为："
                                    + newRfid.getRfidNo() + "的新RFID招标合同不一致");
                        }
                        // 更新旧RFID
                        replenishRfid.setReplenishNo("");
                        replenishRfid.setRfidState(RfidStateEnum.USED);
                        windowRfidService.updateWindowRfid(replenishRfid);
                        
                        // 设置新RFID
                        newRfid.setGlassRfid(ref.getGlassBatchNo());
                        newRfid.setProfileRfid(ref.getProfileBatchNo());
                        newRfid.setWindowType(null);
                        newRfid.setRfidState(RfidStateEnum.UN_USED);
                        windowRfidService.updateWindowRfid(newRfid);
                    }
                }
            }
            // 删除关联
            windowRefService.deleteRef(id);
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("删除门窗RFID关联错误", e);
        }
        
    }
    
    /**
     * 获取待补损批次
     */
    @Override
    @Transactional(readOnly = true)
    public List<ReplenishBatchEntity> getReplenishBatch(String contractNo)
    {
        try
        {
            List<ReplenishBatchEntity> batchList = contractReplenishBatchDao.getReplenishBatch(contractNo);
            return batchList;
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询补损批次错误", e);
        }
        
    }
    
    /**
     * 更新批次支付狀態
     */
    @Override
    public void modifyBatchPayState(String contractNo, String rfidNo,
            String payNo)
    {
        try
        {
            ContractBatchEntity contractBatch = contractBatchDao.getBacthsByRfid(rfidNo);
            if (contractBatch != null)
            {
                if (contractBatch.getType() == 1)
                {
                    ContractBatchEntity cbe = new ContractBatchEntity();
                    cbe.setId(contractBatch.getId());
                    cbe.setPayState(1);
                    cbe.setPayNo(payNo);
                    contractBatchDao.updateBatch(cbe);
                }
                else if (contractBatch.getType() == 2)
                {
                    ModifyBatchEntity modifyBatch = new ModifyBatchEntity();
                    modifyBatch.setId(contractBatch.getId());
                    modifyBatch.setPayState(1);
                    modifyBatch.setPayNo(payNo);
                    contractModifyBatchDao.updateBatch(modifyBatch);
                }
                else if (contractBatch.getType() == 3)
                {
                    ReplenishBatchEntity replenishBatch = new ReplenishBatchEntity();
                    replenishBatch.setId(contractBatch.getId());
                    replenishBatch.setPayState(1);
                    replenishBatch.setPayNo(payNo);
                    contractReplenishBatchDao.updateBatch(replenishBatch);
                }
                // 更新合同支付批次条目
                ContractModel cm = this.getContractModelByContractNo(contractNo);
                ContractEntity ce = new ContractEntity();
                ce.setId(cm.getContract().getId());
                ce.setPayBatch(cm.getContract().getPayBatch() + 1);
                contractDao.updateContract(ce);
            }
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("更新批次错误", e);
        }
    }
    
    /**
     * 获取所有批次
     */
    @Override
    public List<ContractBatchModel> getBacthsByContractNo(String contractNo)
    {
        try
        {
            List<ContractBatchEntity> batchList = contractBatchDao.getBacthsByContractNo(contractNo);
            List<ContractBatchModel> cbList = new ArrayList<ContractBatchModel>();
            if (batchList != null && batchList.size() > 0)
            {
                for (ContractBatchEntity contractBatchEntity : batchList)
                {
                    ContractBatchModel cb = new ContractBatchModel();
                    cb.setBatch(contractBatchEntity);
                    List<BatchItemModel> itemList = JsonMapperUtil.getBatchItems(contractBatchEntity.getBatchItems());
                    cb.setBatchItems(itemList);
                    cbList.add(cb);
                }
            }
            return cbList;
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("更新批次错误", e);
        }
    }
    
}
