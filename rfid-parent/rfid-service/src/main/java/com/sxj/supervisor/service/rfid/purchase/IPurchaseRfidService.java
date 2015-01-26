package com.sxj.supervisor.service.rfid.purchase;

import java.util.List;

import com.sxj.supervisor.entity.rfid.purchase.RfidPurchaseEntity;
import com.sxj.supervisor.enu.rfid.purchase.PayStateEnum;
import com.sxj.supervisor.model.rfid.purchase.PurchaseRfidQuery;
import com.sxj.util.exception.ServiceException;

public interface IPurchaseRfidService
{
    
    public static final Integer executeCount = 1000;
    
    public static final Integer threadCount = 15;
    
    /**
     * 根据条件高级查询
     * 
     * @param query
     * @return
     * @throws ServiceException
     */
    public List<RfidPurchaseEntity> queryPurchase(PurchaseRfidQuery query)
            throws ServiceException;
    
    /**
     * 更新
     * 
     * @param id
     * @throws ServiceException
     */
    public void updatePurchase(RfidPurchaseEntity purchase)
            throws ServiceException;
    
    /**
     * 更新支付状态
     * 
     * @param id
     * @throws ServiceException
     */
    public void updatePayState(String id, PayStateEnum state)
            throws ServiceException;
    
    /**
     * 删除
     * 
     * @param id
     * @throws ServiceException
     */
    public void deletePurchase(String id) throws ServiceException;
    
    /**
     * 获取采购单详情
     * 
     * @param id
     * @return
     * @throws ServiceException
     */
    public RfidPurchaseEntity getRfidPurchase(String id)
            throws ServiceException;
    
    /**
     * 确认收货
     * 
     * @param id
     * @throws ServiceException
     */
    public void confirmReceipt(String id) throws ServiceException;
    
    /**
     * 确认发货
     * 
     * @param id
     * @throws ServiceException
     */
    public void confirmDelivery(String id) throws ServiceException;
    
    /**
     * 添加采购单
     * 
     * @param purchase
     * @param applyId
     * @param hasNumber
     * @throws ServiceException
     */
    public void addPurchase(RfidPurchaseEntity purchase, String applyId,
            String hasNumber) throws ServiceException;
    
    /**
     * 导入
     * 
     * @param purchaseId
     * @throws ServiceException
     */
    public void importRfid(String purchaseId) throws ServiceException;
}
