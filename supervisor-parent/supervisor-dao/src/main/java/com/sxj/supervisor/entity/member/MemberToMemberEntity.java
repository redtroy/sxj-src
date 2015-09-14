package com.sxj.supervisor.entity.member;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.supervisor.dao.member.IMemberToMemberDao;

@Entity(mapper = IMemberToMemberDao.class)
@Table(name = "M_MEMBER_TO_MEMBER")
public class MemberToMemberEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6262879563710307299L;
    
    /**
     * 主键标识
     **/
    @Id(column = "MEMBER_NO")
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
    private String parentName;
    
    /**
     * 6,代理商；7，经销商
     **/
    @Column(name = "MEMBER_TYPE")
    private Integer memberType;
    
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
    
}
