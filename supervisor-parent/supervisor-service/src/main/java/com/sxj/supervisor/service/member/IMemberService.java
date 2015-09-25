package com.sxj.supervisor.service.member;

import java.util.List;

import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.RelevanceMember;
import com.sxj.supervisor.model.member.ExportMemberModel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.model.open.ApiModel;
import com.sxj.util.exception.ServiceException;

public interface IMemberService
{
    
    public void addMember(MemberEntity member) throws ServiceException;
    
    /**
     * 修改会员，前台调用传TRUE，后来传FALSE
     */
    public MemberEntity modifyMember(MemberEntity member, Boolean flag)
            throws ServiceException;
    
    public String initializePwd(String memberId) throws ServiceException;
    
    public MemberEntity getMember(String id) throws ServiceException;
    
    public MemberEntity getMemberByName(String name) throws ServiceException;
    
    public List<MemberEntity> queryMembers(MemberQuery query)
            throws ServiceException;
    
    public void removeMember(String id) throws ServiceException;
    
    public void editState(String id, Integer state) throws ServiceException;
    
    public void editCheckState(String id, Integer state)
            throws ServiceException;
    
    public MemberEntity memberInfo(String memberNo) throws ServiceException;
    
    public void editPwd(String id, String pwd) throws ServiceException;
    
    public String createvalidata(String phoneNo, String message)
            throws ServiceException;
    
    List<ApiModel> apiQueryMembers(String name, String type, String city)
            throws ServiceException;
    
    public String createPfx(MemberEntity member, AccountEntity account)
            throws ServiceException;
    
    /**
     * 更新前台会员
     * @param member
     * @return
     */
    public MemberEntity websiteModifyMember(MemberEntity member);
    
    /**
     * 前台会员查询
     */
    public MemberEntity getMemberNew(String id);
    
    /**
     * 查询关联企业
     * @param memberNo
     * @return
     */
    public List<RelevanceMember> getListRelevanceMember(String memberNo);
    
    /**
     * 跟新关联企业
     * @param memberNo
     * @param list
     * @return
     */
    public String addRelevanceMember(List<RelevanceMember> list);

	List<MemberEntity> queryWebsiteMembers(MemberQuery query)
			throws ServiceException;
    
    /**
     * 资质证书变动标记清0
     */
    public void ChangeImageFlagClear();

    public List<ExportMemberModel> queryExportMemberModel(MemberQuery query)
			throws ServiceException;
    
}
