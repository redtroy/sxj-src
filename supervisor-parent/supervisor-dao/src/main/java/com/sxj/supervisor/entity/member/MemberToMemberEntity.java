package com.sxj.supervisor.entity.member;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.supervisor.dao.member.IMemberToMemberDao;
import com.sxj.supervisor.validator.hibernate.AddGroup;

@Entity(mapper = IMemberToMemberDao.class)
@Table(name = "M_MEMBER_TO_MEMBER")
public class MemberToMemberEntity implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6262879563710307299L;
    
    /**
     * 标识
     **/
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 主键标识
     **/
    @Column(name = "MEMBER_NO")
    private String memberNo;
    
    /**
     * 型材厂编号
     **/
    @Column(name = "PARENT_NO")
    private String parentNo;
    
    /**
     * 型材厂名称
     **/
    @Column(name = "PARENT_NAME")
    @Length(max = 50, message = "型材厂名称长度过长", groups = { AddGroup.class })
    private String parentName;
    
    /**
     * 6,代理商；7，经销商
     **/
    @Column(name = "MEMBER_TYPE")
    private Integer memberType;
    
    /**
     * 名称
     **/
    @Column(name = "MEMBER_NAME")
    @Length(max = 50, message = "会员名称长度过长", groups = { AddGroup.class })
    private String memberName;
    
    /**
     * 代理商或经销商联系人名称
     **/
    @Column(name = "CONTACTS")
    @Length(max = 100, message = "会员联系人长度过长", groups = { AddGroup.class })
    private String contacts;
    
    /**
     * 代理商或经销商联系电话
     **/
    @Column(name = "TEL_NUM")
    @Length(max = 100, message = "会员联系电话长度过长", groups = { AddGroup.class })
    private String telNum;
    
    /**
     * 创建时间
     **/
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    /**
     * 备注
     **/
    @Column(name = "REMARK")
    private String remark;
    
    /**
     * 序号
     **/
    @Column(name = "ORDER")
    private Integer order;
    
    /**
     * 型材厂联系人名称
     **/
    @Column(name = "PARENT_CONTACTS")
    @Length(max = 100, message = "型材厂联系人长度过长", groups = { AddGroup.class })
    private String parentContacts;
    
    /**
     * 型材厂联系人电话
     **/
    @Column(name = "PARENT_TEL_NUM")
    @Length(max = 100, message = "型材厂联系电话长度过长", groups = { AddGroup.class })
    private String parentTelNum;
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getMemberNo()
    {
        return memberNo;
    }
    
    public void setMemberNo(String memberNo)
    {
        this.memberNo = memberNo;
    }
    
    public String getParentNo()
    {
        return parentNo;
    }
    
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }
    
    public String getParentName()
    {
        return parentName;
    }
    
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }
    
    public Integer getMemberType()
    {
        return memberType;
    }
    
    public void setMemberType(Integer memberType)
    {
        this.memberType = memberType;
    }
    
    public String getMemberName()
    {
        return memberName;
    }
    
    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }
    
    public String getContacts()
    {
        return contacts;
    }
    
    public void setContacts(String contacts)
    {
        this.contacts = contacts;
    }
    
    public String getTelNum()
    {
        return telNum;
    }
    
    public void setTelNum(String telNum)
    {
        this.telNum = telNum;
    }
    
    public String getParentContacts()
    {
        return parentContacts;
    }
    
    public void setParentContacts(String parentContacts)
    {
        this.parentContacts = parentContacts;
    }
    
    public String getParentTelNum()
    {
        return parentTelNum;
    }
    
    public void setParentTelNum(String parentTelNum)
    {
        this.parentTelNum = parentTelNum;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Integer getOrder()
    {
        return order;
    }
    
    public void setOrder(Integer order)
    {
        this.order = order;
    }
    
}
