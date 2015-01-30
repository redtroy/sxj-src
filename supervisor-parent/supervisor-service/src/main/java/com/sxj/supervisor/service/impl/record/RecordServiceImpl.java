package com.sxj.supervisor.service.impl.record;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import third.rewrite.fastdfs.service.IStorageClientService;

import com.sxj.statemachine.StateMachineImpl;
import com.sxj.statemachine.exceptions.StateMachineException;
import com.sxj.supervisor.dao.contract.IContractBatchDao;
import com.sxj.supervisor.dao.contract.IContractDao;
import com.sxj.supervisor.dao.record.IRecordDao;
import com.sxj.supervisor.entity.contract.ContractBatchEntity;
import com.sxj.supervisor.entity.contract.ContractEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.record.RecordEntity;
import com.sxj.supervisor.enu.contract.ContractStateEnum;
import com.sxj.supervisor.enu.contract.ContractSureStateEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordConfirmStateEnum;
import com.sxj.supervisor.enu.record.RecordFlagEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.contract.ContractModel;
import com.sxj.supervisor.model.record.RecordQuery;
import com.sxj.supervisor.service.CometServiceImpl;
import com.sxj.supervisor.service.contract.IContractService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.record.IRecordService;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;

@Service
@Transactional
public class RecordServiceImpl implements IRecordService
{
    
    @Autowired
    private IRecordDao recordDao;
    
    @Autowired
    private IContractService contractService;
    
    @Autowired
    private IContractDao contractDao;
    
    @Autowired
    private IContractBatchDao batchDao;
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IStorageClientService storageClientService;
    
    @Autowired
    @Qualifier("recordStatefsm")
    private StateMachineImpl<RecordStateEnum> recordStatefsm;
    
    @Autowired
    @Qualifier("contractSureStatefsm")
    private StateMachineImpl<ContractSureStateEnum> contractSureStatefsm;
    
    @Autowired
    @Qualifier("recordConfirmStatefsm")
    private StateMachineImpl<RecordConfirmStateEnum> recordConfirmStatefsm;
    
    private IRecordService self;
    
    @Autowired
    private ApplicationContext context;
    
    @PostConstruct
    public void init()
    {
        self = context.getBean(IRecordService.class);
    }
    
    /**
     * 新增备案
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRecord(RecordEntity record) throws ServiceException
    {
        try
        {
            String year = new SimpleDateFormat("yy", Locale.CHINESE).format(Calendar.getInstance()
                    .getTime());
            String month = new SimpleDateFormat("MM", Locale.CHINESE).format(Calendar.getInstance()
                    .getTime());
            record.setDateNo("BA" + year + month);
            record.setAcceptState(0);
            record.setRecordState(0);
            recordDao.addRecord(record);
            
            CometServiceImpl.takeCount(MessageChannel.RECORD_MESSAGE);
            MessageChannel.initTopic().publish(MessageChannel.RECORD_MESSAGE);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增备案信息错误", e);
        }
        
    }
    
    /**
     * 更新备案
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void modifyRecord(RecordEntity record) throws ServiceException
    {
        try
        {
            Assert.hasText(record.getId());
            RecordEntity oldRe = getRecord(record.getId());
            Assert.notNull(oldRe);
            
            updateImages(record, oldRe);
            // 更改用户---修改备案状态
            if (oldRe.getContractType() != null
                    && oldRe.getContractType() != ContractTypeEnum.BIDDING)
            {
                MemberEntity member = memberService.memberInfo(record.getMemberIdB());
                Assert.notNull(member);
                switch (member.getType())
                {
                    case GLASSFACTORY:
                        record.setContractType(ContractTypeEnum.GLASS);
                        break;
                    case GENRESFACTORY:
                        record.setContractType(ContractTypeEnum.EXTRUSIONS);
                        break;
                    default:
                        break;
                }
                // if (member.getType() == MemberTypeEnum.glassFactory)
                // {
                //
                // }
                // else if (member.getType() == MemberTypeEnum.genresFactory)
                // {
                // record.setContractType(ContractTypeEnum.extrusions);
                // }
            }
            
            recordDao.updateRecord(record);
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("更新备案信息错误", e);
        }
    }
    
    private void updateImages(RecordEntity record, RecordEntity oldRe)
    {
        String[] oldPath = null;
        String[] nowPath = null;
        String oldImage = oldRe.getImgPath();
        oldPath = splitImages(oldImage);
        nowPath = splitImages(record.getImgPath());
        if (oldPath != null && oldPath.length > 0)
        {
            for (int i = 0; i < oldPath.length; i++)
            {
                if (StringUtils.isEmpty(oldPath[i]))
                {
                    for (int j = 0; j < nowPath.length; j++)
                    {
                        if (StringUtils.isEmpty(nowPath[j])
                                && !oldPath[i].equals(nowPath[j]))
                        {
                            storageClientService.deleteFile(oldPath[i]);
                        }
                    }
                }
            }
        }
    }
    
    private String[] splitImages(String oldImage)
    {
        if (StringUtils.isNotEmpty(oldImage))
        {
            return oldImage.split(",");
        }
        return null;
    }
    
    /**
     * 删除备案
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRecord(String id) throws ServiceException
    {
        try
        {
            RecordEntity re = recordDao.getRecord(id);
            recordDao.deleteRecord(id);
            // 如果是变更或者补损备案,删除后需要把状态重置已备案
            if (re.getType().getId() != 0)
            {
                ContractModel cm = contractService.getContractModelByContractNo(re.getContractNo());
                ContractEntity ce = cm.getContract();
                ce.setConfirmState(ContractSureStateEnum.FILINGS);// 已备案
                ce.setState(ContractStateEnum.APPROVAL);// 已审核
                contractDao.updateContract(ce);
                // 变更所有备案状态为已备案
                RecordQuery query = new RecordQuery();
                query.setContractNo(re.getContractNo());
                List<RecordEntity> reList = this.queryRecord(query);
                for (RecordEntity recordEntity : reList)
                {
                    recordEntity.setConfirmState(RecordConfirmStateEnum.HASRECORD);
                    recordDao.updateRecord(recordEntity);
                }
            }
            
        }
        catch (Exception e)
        {
            throw new ServiceException("查询备案信息错误", e);
        }
    }
    
    /**
     * 查询备案
     */
    @Override
    @Transactional(readOnly = true)
    public RecordEntity getRecord(String id)
    {
        RecordEntity record = recordDao.getRecord(id);
        return record;
    }
    
    /**
     * 条件查询备案
     */
    @Override
    @Transactional(readOnly = true)
    public List<RecordEntity> queryRecord(RecordQuery query)
            throws ServiceException
    {
        try
        {
            if (query == null)
            {
                return null;
            }
            QueryCondition<RecordEntity> condition = new QueryCondition<RecordEntity>();
            condition.addCondition("recordNo", query.getRecordNo());// 备案号
            condition.addCondition("memberId", query.getMemberId());// 会员Id
            condition.addCondition("applyId", query.getApplyId());// 申请会员ID
            condition.addCondition("contractType", query.getContractType());// 合同类型
            condition.addCondition("contractNo", query.getContractNo());// 合同号
            condition.addCondition("recordType", query.getRecordType());// 备案类型
            condition.addCondition("state", query.getState());// 备案状态
            condition.addCondition("startApplyDate", query.getStartApplyDate());// 开始申请时间
            condition.addCondition("endApplyDate", query.getEndApplyDate());// 结束申请时间
            condition.addCondition("startAcceptDate",
                    query.getStartAcceptDate());// 开始受理时间
            condition.addCondition("endAcceptDate", query.getEndAcceptDate());// 结束受理时间
            condition.addCondition("contractPepole", query.getContractPepole());
            condition.addCondition("memberIdA", query.getMemberIdA());// 结束受理时间
            condition.addCondition("memberIdB", query.getMemberIdB());// 结束受理时间
            condition.addCondition("confirmState", query.getConfirmState());// 确认状态
            condition.addCondition("sort", query.getSort());// 排序
            condition.addCondition("sortColumn", query.getSortColumn());// 排序字段
            condition.addCondition("flag", query.getFlag());// 备案方
            // condition.addCondition("type", query.getType());// 备案类型
            
            condition.setPage(query);
            List<RecordEntity> recordList = recordDao.queryRecord(condition);
            query.setPage(condition);
            return recordList;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询备案信息错误", e);
        }
        
    }
    
    /**
     * 绑定合同
     */
    @Override
    @Transactional
    public void bindingContract(String contractNo, String refContractNo,
            String recordNo, String recordNo2, String recordIdA,
            String recordIdB) throws ServiceException
    {
        try
        {
            RecordEntity record = recordDao.getRecord(recordIdA);
            RecordEntity record2 = recordDao.getRecord(recordIdB);
            if (record != null)
            {
                record.setRefContractNo(refContractNo);
                record.setContractNo(contractNo);
                recordStatefsm.setCurrentState(record.getState());
                recordStatefsm.fire(record.getState().toString(), record);
                // record.setState(recordStatefsm.getCurrentState());
                recordDao.updateRecord(record);
            }
            if (record2 != null)
            {
                record2.setRefContractNo(refContractNo);
                record2.setContractNo(contractNo);
                recordStatefsm.setCurrentState(record2.getState());
                recordStatefsm.fire(record2.getState().toString(), record2);
                // record2.setState(recordStatefsm.getCurrentState());
                recordDao.updateRecord(record2);
            }
            // 插入合同
            ContractModel ce = contractService.getContractModelByContractNo(contractNo);
            if (ce != null)
            {
                ContractEntity contract = ce.getContract();
                contract.setRecordNo(recordNo + "," + recordNo2);
                contract.setRefContractNo(refContractNo);
                contractDao.updateContract(contract);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("绑定备案错误", e);
        }
    }
    
    @Override
    public RecordEntity getRecordByNo(String no) throws ServiceException
    {
        try
        {
            if (StringUtils.isEmpty(no))
            {
                return null;
            }
            RecordQuery query = new RecordQuery();
            query.setRecordNo(no);
            List<RecordEntity> list = queryRecord(query);
            if (!CollectionUtils.isEmpty(list))
            {
                return list.get(0);
            }
            return null;
        }
        catch (Exception e)
        {
            throw new ServiceException("", e);
        }
    }
    
    public void batchModifyConfimState(String contractNo, MemberTypeEnum memType)
            throws StateMachineException
    {
        RecordQuery recordQuery = new RecordQuery();
        recordQuery.setContractNo(contractNo);
        List<RecordEntity> recordList = queryRecord(recordQuery);
        for (RecordEntity re : recordList)
        {
            // RecordEntity rEntity = new RecordEntity();
            // rEntity.setId(re.getId());
            recordConfirmStatefsm.setCurrentState(re.getConfirmState());
            recordConfirmStatefsm.fire(re.getConfirmState().toString()
                    + memType.toString() + re.getContractType().toString(), re);
            // re.setConfirmState(recordConfirmStatefsm.getCurrentState());
            // rEntity.setRecordDate(new Date());// 备案时间
            
            // recordConfirmStatefsm.fire(event, object);
            
            // if (re.getContractType().getId() != 0) {
            // if (con.getConfirmState().getId() == 0) {
            //
            // } else {
            // if (re.getFlag().getId() == 1) {
            // rEntity.setRecordState(1);
            // rEntity.setRecordDate(new Date());// 备案时间
            // }
            // if (re.getType().getId() == 1) {
            // rEntity.setState(RecordStateEnum.change);
            // if (re.getRecordState() == 0) {
            // rEntity.setRecordState(1);
            // rEntity.setRecordDate(new Date());// 备案时间
            // }
            // } else if (re.getType().getId() == 2) {
            // rEntity.setState(RecordStateEnum.supplement);
            // if (re.getRecordState() == 0) {
            // rEntity.setRecordState(1);
            // rEntity.setRecordDate(new Date());// 备案时间
            // }
            // } else {
            // rEntity.setState(RecordStateEnum.Binding);
            // if (re.getRecordState() == 0) {
            // rEntity.setRecordState(1);
            // rEntity.setRecordDate(new Date());// 备案时间
            // }
            // }
            // }
            // } else {
            // // rEntity.setConfirmState(RecordConfirmStateEnum.hasRecord);
            // // rEntity.setRecordDate(new Date());// 备案时间
            //
            // }
            recordDao.updateRecord(re);
        }
    }
    
    @Override
    @Transactional
    public void modifyState(String contractId, MemberTypeEnum memType)
            throws ServiceException
    {
        try
        {
            Assert.hasText(contractId);
            ContractModel conModel = contractService.getContract(contractId);
            Assert.notNull(conModel);
            ContractEntity con = conModel.getContract();
            contractSureStatefsm.setCurrentState(con.getConfirmState());
            contractSureStatefsm.fire(con.getConfirmState().toString()
                    + memType.toString() + con.getType().toString(), con);
            // con.setConfirmState(contractSureStatefsm.getCurrentState());
            contractDao.updateContract(con);
            // 更改合同关联所有备案状态
            self.batchModifyConfimState(con.getContractNo(), memType);
            
            // ContractEntity newCon = new ContractEntity();
            // newCon.setId(con.getId());
            // if (con.getType().getId() != 0) {
            // if (con.getConfirmState().getId() == 0) {
            // if (state.getId() == 2) {
            // newCon.setConfirmState(ContractSureStateEnum.aaffirm);
            // } else if (state.getId() == 3) {
            // newCon.setConfirmState(ContractSureStateEnum.baffirm);
            // }
            // } else {
            // newCon.setConfirmState(ContractSureStateEnum.filings);
            // newCon.setRecordDate(new Date());
            // }
            // } else {
            // newCon.setConfirmState(ContractSureStateEnum.filings);
            // newCon.setRecordDate(new Date());
            // }
            // contractDao.updateContract(newCon);
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("确认合同错误", e);
        }
        
    }
    
    /**
     * 获取批次
     * 
     * @param recordId
     * @return
     */
    @Override
    @Transactional
    public List<ContractBatchEntity> getBatch(String recordId)
    {
        try
        {
            RecordEntity re = recordDao.getRecord(recordId);
            List<ContractBatchEntity> batchList = batchDao.getBacthsByContractNo(re.getContractNo());
            return batchList;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询批次信息错误", e);
        }
    }
    
    /**
     * 获取批次
     * 
     * @param recordId
     * @return
     */
    @Override
    @Transactional
    public List<ContractBatchEntity> getBatchList(String recordId)
    {
        try
        {
            RecordEntity re = recordDao.getRecord(recordId);
            QueryCondition<ContractBatchEntity> query = new QueryCondition<ContractBatchEntity>();
            query.addCondition("contractId", re.getContractNo());
            List<ContractBatchEntity> batchList = batchDao.queryBacths(query);
            if (batchList != null)
            {
                return batchList;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            throw new ServiceException("查询批次信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public String getRfid(String batchId)
    {
        try
        {
            ContractBatchEntity batch = batchDao.getBatch(batchId);
            return batch.getRfidNo();
        }
        catch (Exception e)
        {
            throw new ServiceException("查询rfid信息错误", e);
        }
    }
    
    @Override
    @Transactional
    public void saveRecord(RecordEntity record) throws ServiceException
    {
        try
        {
            record.setDateNo("BA"
                    + DateTimeUtils.formateDate2Str(new Date(), "yyMM"));
            record.setApplyDate(new Date());// 申請時間
            record.setAcceptDate(null);
            record.setAcceptState(0);
            record.setRecordState(0);
            record.setRecordDate(null);
            record.setId(null);
            recordDao.addRecord(record);
            // 更改合同关联所有的备案状态
            String contractNo = record.getContractNo();
            ContractModel cm = contractService.getContractModelByContractNo(contractNo);
            RecordQuery query = new RecordQuery();
            query.setContractNo(contractNo);
            List<RecordEntity> list = queryRecord(query);
            for (RecordEntity recordEntity : list)
            {
                recordEntity.setConfirmState(RecordConfirmStateEnum.ACCEPTED);
                recordDao.updateRecord(recordEntity);
            }
            if (cm != null)
            {
                // 变更合同状态
                ContractEntity ce = cm.getContract();
                ContractEntity centity = new ContractEntity();
                centity.setId(ce.getId());
                centity.setState(ContractStateEnum.NOAPPROVAL);
                centity.setConfirmState(ContractSureStateEnum.NOAFFIRM);
                contractDao.updateContract(centity);
            }
            CometServiceImpl.takeCount(MessageChannel.RECORD_MESSAGE);
            MessageChannel.initTopic().publish(MessageChannel.RECORD_MESSAGE);
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("更新备案错误", e);
        }
    }
    
    /**
     * 同步合同与备案信息
     */
    @Override
    @Transactional
    public void updateRecordAndContract(RecordEntity record,
            ContractEntity contract) throws ServiceException
    {
        try
        {
            // if (record != null) {
            // if (record.getContractType().getId() == 0) {
            // recordDao.updateRecord(record);
            // ContractModel cm = contractService
            // .getContractModelByContractNo(record
            // .getContractNo());
            // contract = cm.getContract();
            // contract.setMemberNameA(record.getMemberNameA());
            // contractDao.updateContract(contract);
            // } else {
            // recordDao.updateRecord(record);
            // ContractModel cm = contractService
            // .getContractModelByContractNo(record
            // .getContractNo());
            // contract = cm.getContract();
            // contract.setMemberIdA(record.getMemberIdA());
            // contract.setMemberIdB(record.getMemberIdB());
            // contract.setMemberNameA(record.getMemberNameA());
            // contract.setMemberNameB(record.getMemberNameB());
            // contract.setType(record.getContractType());
            // contract.setRefContractNo(record.getRefContractNo());
            // contractDao.updateContract(contract);
            // // 更新所有备案信息
            // QueryCondition<RecordEntity> query = new
            // QueryCondition<RecordEntity>();
            // query.addCondition("contractNo", record.getContractNo());
            // query.addCondition("applyId", record.getApplyId());
            // List<RecordEntity> recordList = recordDao
            // .queryRecord(query);
            // for (RecordEntity recordEntity : recordList) {
            // if (recordEntity.getContractType().getId() != 0) {
            // recordEntity.setMemberIdA(record.getMemberIdA());
            // recordEntity.setMemberIdB(record.getMemberIdB());
            // recordEntity
            // .setMemberNameA(record.getMemberNameA());
            // recordEntity
            // .setMemberNameB(record.getMemberNameB());
            // }
            // recordDao.updateRecord(recordEntity);
            // }
            // }
            // }
        }
        catch (Exception e)
        {
            throw new ServiceException("更新合同备案出错!", e);
        }
    }
    
    /**
     * 获取当前的用户的备案号
     */
    @Override
    public String getRecordNo(String contractNo, MemberEntity menber)
    {
        try
        {
            String recordNo = "";
            ContractModel cm = contractService.getContractModelByContractNo(contractNo);
            Assert.notNull(cm);
            String records = cm.getContract().getRecordNo();
            Assert.hasText(records);
            String[] recordArr = records.split(",");
            
            QueryCondition<RecordEntity> condition = new QueryCondition<RecordEntity>();
            condition.addCondition("recordNos", recordArr);// 备案号
            List<RecordEntity> recordList = recordDao.queryRecord(condition);
            
            for (RecordEntity recordEntity : recordList)
            {
                if ((menber.getType() == MemberTypeEnum.DAWP && recordEntity.getFlag() == RecordFlagEnum.A)
                        || recordEntity.getFlag() == RecordFlagEnum.B)
                {
                    recordNo = recordEntity.getRecordNo();
                }
            }
            return recordNo;
        }
        catch (ServiceException e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException(e.getMessage());
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取备案号错误", e);
        }
        
    }
}
