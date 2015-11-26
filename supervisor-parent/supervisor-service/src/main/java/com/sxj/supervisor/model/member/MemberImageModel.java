package com.sxj.supervisor.model.member;

import java.io.Serializable;
import java.util.List;

import com.sxj.supervisor.entity.member.CertificateEntity;
import com.sxj.supervisor.entity.member.CertificateLevelEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;

public class MemberImageModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6711275881452168102L;
    
    private MemberImageEntity memberImage;
    
    private List<CertificateLevelEntity> list;
    
    private List<CertificateEntity> clist;
    
    public List<CertificateEntity> getClist()
    {
        return clist;
    }
    
    public void setClist(List<CertificateEntity> clist)
    {
        this.clist = clist;
    }
    
    public MemberImageEntity getMemberImage()
    {
        return memberImage;
    }
    
    public void setMemberImage(MemberImageEntity memberImage)
    {
        this.memberImage = memberImage;
    }
    
    public List<CertificateLevelEntity> getList()
    {
        return list;
    }
    
    public void setList(List<CertificateLevelEntity> list)
    {
        this.list = list;
    }
}
