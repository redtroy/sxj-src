package com.sxj.finance.entity.member;

import java.io.Serializable;

import com.sxj.finance.dao.member.IMemberInfoDao;
import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;

/**
 * 基本信息
 * 
 * @author dujinxin
 *
 */
@Entity(mapper = IMemberInfoDao.class)
@Table(name = "M_MEMBER_INFO")
public class MemberInfoEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2262265581991391325L;
    
    /**
     * 
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 
     */
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;
    
    /**
     * 性别
     */
    @Column(name = "SEX")
    private Boolean sex;
    
    /**
     * 年龄
     */
    @Column(name = "AGE")
    private Integer age;
    
    /**
     * 家庭住址
     */
    @Column(name = "HOME_ADDRESS")
    private String homeAddress;
    
    /**
     * 联系电话
     */
    @Column(name = "TEL_NUM")
    private String telNum;
    
    /**
     * 身份证号
     */
    @Column(name = "CARD_NUM")
    private String cardNum;
    
    /**
     * 电子邮箱
     */
    @Column(name = "EMAIL")
    private String email;
    
    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;
    
    /**
     * 营业执照编码
     */
    @Column(name = "B_LICENCE")
    private String bLicence;
    
    /**
     * 注册资本
     */
    @Column(name = "REGISTERED")
    private Double registered;
    
    /**
     * 经营主体
     */
    @Column(name = "MANAGE")
    private String manage;
    
    /**
     * 经营范围
     */
    @Column(name = "MANAGE_RANGE")
    private String manageRange;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Boolean getSex()
    {
        return sex;
    }
    
    public void setSex(Boolean sex)
    {
        this.sex = sex;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public String getHomeAddress()
    {
        return homeAddress;
    }
    
    public void setHomeAddress(String homeAddress)
    {
        this.homeAddress = homeAddress;
    }
    
    public String getTelNum()
    {
        return telNum;
    }
    
    public void setTelNum(String telNum)
    {
        this.telNum = telNum;
    }
    
    public String getCardNum()
    {
        return cardNum;
    }
    
    public void setCardNum(String cardNum)
    {
        this.cardNum = cardNum;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getbLicence()
    {
        return bLicence;
    }
    
    public void setbLicence(String bLicence)
    {
        this.bLicence = bLicence;
    }
    
    public Double getRegistered()
    {
        return registered;
    }
    
    public void setRegistered(Double registered)
    {
        this.registered = registered;
    }
    
    public String getManage()
    {
        return manage;
    }
    
    public void setManage(String manage)
    {
        this.manage = manage;
    }
    
    public String getManageRange()
    {
        return manageRange;
    }
    
    public void setManageRange(String manageRange)
    {
        this.manageRange = manageRange;
    }
    
}
