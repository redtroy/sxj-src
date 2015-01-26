package com.sxj.supervisor.entity.rfid.sale;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.rfid.sale.IRfidPriceDao;

/**
 * RFID价格管理
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IRfidPriceDao.class)
@Table(name = "R_RFID_PRICE")
public class RfidPriceEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -944462436194123104L;
    
    /**
     * ID
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 门窗RFID价格
     */
    @Column(name = "WINDOW_PRICE")
    private Double windowPrice;
    
    /**
     * 物流批次RFID价格
     */
    @Column(name = "LOGISTICS_PRICE")
    private Double logisticsPrice;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Double getWindowPrice()
    {
        return windowPrice;
    }
    
    public void setWindowPrice(Double windowPrice)
    {
        this.windowPrice = windowPrice;
    }
    
    public Double getLogisticsPrice()
    {
        return logisticsPrice;
    }
    
    public void setLogisticsPrice(Double logisticsPrice)
    {
        this.logisticsPrice = logisticsPrice;
    }
    
}
