package com.sxj.supervisor.manage.controller.member;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.supervisor.entity.member.AccountEntity;
import com.sxj.supervisor.entity.member.MemberEntity;
import com.sxj.supervisor.entity.member.MemberImageEntity;
import com.sxj.supervisor.entity.member.MemberToMemberEntity;
import com.sxj.supervisor.entity.system.AreaEntity;
import com.sxj.supervisor.enu.member.LevelEnum;
import com.sxj.supervisor.enu.member.MemberCheckStateEnum;
import com.sxj.supervisor.enu.member.MemberStatesEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.supervisor.model.comet.MessageChannel;
import com.sxj.supervisor.model.member.MemberImageModel;
import com.sxj.supervisor.model.member.MemberQuery;
import com.sxj.supervisor.service.member.IAccountService;
import com.sxj.supervisor.service.member.IMemberImageService;
import com.sxj.supervisor.service.member.IMemberService;
import com.sxj.supervisor.service.member.IMemberToMemberService;
import com.sxj.supervisor.service.system.IAreaService;
import com.sxj.supervisor.validator.hibernate.UpdateGroup;
import com.sxj.util.Constraints;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.FileUtil;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController
{
    
    @Autowired
    private IMemberService memberService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private RedisTopics topics;
    
    @Autowired
    private IMemberImageService imageService;
    
    @Autowired
    private IMemberToMemberService mtmservice;
    
    /**
     * 会员管理列表
     * 
     * @param map
     * @return
     */
    @RequestMapping("memberList")
    public String memberList(MemberQuery query, ModelMap map)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            if (StringUtils.isNotEmpty(query.getArea()))
            {
                String areaId = query.getArea();
                areaId = "32:江苏省," + areaId;
                query.setArea(areaId);
            }
            if (query.getCheckState() != null && query.getCheckState() == 1)
            {
                query.setCheckState(1);
                query.setFlag("1");
            }
            else if (query.getCheckState() != null
                    && query.getCheckState() == 3)
            {
                query.setCheckState(1);
                query.setFlag("0");
            }
            MemberTypeEnum[] types = MemberTypeEnum.values();
            MemberCheckStateEnum[] checkStates = MemberCheckStateEnum.values();
            MemberStatesEnum[] states = MemberStatesEnum.values();
            List<AreaEntity> cityList = areaService.getChildrenAreas("32");
            List<MemberEntity> list = memberService.queryMembers(query);
            map.put("types", types);
            map.put("checkStates", checkStates);
            map.put("states", states);
            map.put("memberList", list);
            map.put("cityList", cityList);
            map.put("query", query);
            map.put("channelName", MessageChannel.MEMBER_MESSAGE);
            map.put("channelNamePerfect", MessageChannel.MEMBER_PERFECT_MESSAGE);
            map.put("channelNamechangeImage",
                    MessageChannel.MEMBER_IMAGECHANGE_MESSAGE);
            if (StringUtils.isNotEmpty(query.getIsDelMes()))
            {
                CometServiceImpl.setCount(MessageChannel.MEMBER_MESSAGE, 0l);
            }
            if (StringUtils.isNotEmpty(query.getIsDelMesPerfect()))
            {
                CometServiceImpl.setCount(MessageChannel.MEMBER_PERFECT_MESSAGE,
                        0l);
                CometServiceImpl.clear(MessageChannel.MEMBER_PERFECT_MESSAGE_SET);
            }
            if (StringUtils.isNotEmpty(query.getIsDelChangeImage()))
            {
                CometServiceImpl.setCount(MessageChannel.MEMBER_IMAGECHANGE_MESSAGE,
                        0l);
                CometServiceImpl.clear(MessageChannel.MEMBER_IMAGECHANGE_MESSAGE_SET);
                memberService.ChangeImageFlagClear();
            }
            // registChannel(MessageChannel.MEMBER_MESSAGE);
            // registChannel(MessageChannel.MEMBER_PERFECT_MESSAGE);
            return "manage/member/member";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new WebException("查询会员信息错误");
        }
        
    }
    
    /**
     * 检查FLAG
     * 
     * @param id
     * @return
     */
    @RequestMapping("checkFlag")
    public @ResponseBody Map<String, Object> checkFlag(String id)
            throws WebException
    {
        try
        {
            Map<String, Object> map = new HashMap<String, Object>();
            String flag = "";
            if (!memberService.getMember(id).getFlag())
            {
                flag = "false";
            }
            map.put("flag", flag);
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new WebException("查询会员信息错误");
        }
    }
    
    /**
     * 
     */
    @RequestMapping("info")
    public String info(String id, ModelMap map) throws WebException
    {
        try
        {
            MemberEntity member = memberService.getMember(id);
            MemberTypeEnum[] types = MemberTypeEnum.values();
            MemberCheckStateEnum[] checkStates = MemberCheckStateEnum.values();
            MemberStatesEnum[] states = MemberStatesEnum.values();
            LevelEnum[] level = LevelEnum.values();
            List<AreaEntity> cityList = areaService.getChildrenAreas("32");
            // 获取证书
            List<MemberImageEntity> ImageList = imageService.getImages(member.getMemberNo(),
                    "1");
            // 获取上一版本证书
            List<MemberImageEntity> oldImageList = imageService.historyImage(member.getMemberNo());
            List<MemberImageEntity> newImagList = new ArrayList<MemberImageEntity>();
            List<MemberImageEntity> delList = new ArrayList<MemberImageEntity>();
            // 获取新的图片集合
            int newNum = 0;
            int delNum = 0;
            if (ImageList.size() > 0 && oldImageList.size() > 0)
            {
                for (MemberImageEntity image1 : ImageList)
                {
                    boolean flag = true;
                    for (MemberImageEntity image2 : oldImageList)
                    {
                        if (image1.getImage().equals(image2.getImage()))
                        {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                    {
                        newNum++;
                        newImagList.add(image1);
                    }
                }
                
                for (MemberImageEntity image1 : oldImageList)
                {
                    boolean flag = true;
                    for (MemberImageEntity image2 : ImageList)
                    {
                        if (image1.getImage().equals(image2.getImage()))
                        {
                            flag = false;
                            break;
                        }
                    }
                    if (flag)
                    {
                        delList.add(image1);
                        delNum++;
                    }
                }
            }
            if (oldImageList.size() < 1)
            {
                newImagList = ImageList;
            }
            /*
             * if (relist.size() > 0)e {
             * 
             * }
             */
            List<MemberToMemberEntity> relist = mtmservice.queryInfo(member.getMemberNo());
            map.put("relist", relist);
            // map.put("delzizhi", delzizhi);
            map.put("oldNum", oldImageList.size());// 原有证书数目
            map.put("newNum", newImagList.size());// 原有证书数目
            map.put("delNum", delList.size());// 删除图片数目
            map.put("num", ImageList.size());
            map.put("ImageList", ImageList);
            map.put("newImagList", newImagList);
            map.put("delList", delList);
            map.put("cityList", cityList);
            map.put("types", types);
            map.put("checkStates", checkStates);
            map.put("states", states);
            map.put("level", level);
            map.put("member", member);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new WebException("查询会员信息错误");
        }
        return "manage/member/memberInfo";
    }
    
    @RequestMapping("delEmage")
    public @ResponseBody Map<String, String> delEmage(String image)
            throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            imageService.delImageByImagePath(image);
            map.put("ok", "ok");
            return map;
            
        }
        catch (Exception e)
        {
            SxjLogger.error("删除图片出错", e, this.getClass());
            throw new WebException("删除图片出错");
        }
    }
    
    /**
     * 修改3C证书，资质证书
     * 
     * @return
     * @throws WebException
     */
    @RequestMapping("editImage")
    public @ResponseBody Map<String, String> editImage(String[] memberNo,
            String[] image, String[] certificateNo, String[] remark)
            throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            List<MemberImageEntity> list = new ArrayList<MemberImageEntity>();
            for (int i = 0; i < memberNo.length; i++)
            {
                MemberImageEntity mi = new MemberImageEntity();
                mi.setMemberNo(memberNo[i]);
                mi.setImage(image[i]);
                mi.setCertificateNo(certificateNo[i]);
                mi.setRemark(remark[i]);
                list.add(mi);
            }
            imageService.ManageAddImage(list);
            map.put("isok", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("修改3C证书，资质证书错误", e, this.getClass());
            throw new WebException("修改3C证书，资质证书错误");
        }
    }
    
    /**
     * 新修改资质证书
     * 
     * @return
     * @throws WebException
     */
    @RequestMapping("newEditImage")
    public @ResponseBody Map<String, String> newEditImage(String memberId,
            String images) throws WebException
    {
        try
        {
            Map<String, String> map = new HashMap<String, String>();
            imageService.websiteAddImage(memberService.getMember(memberId)
                    .getMemberNo(), images);
            map.put("isok", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("修改资质证书错误", e, this.getClass());
            throw new WebException("修改资质证书错误");
        }
    }
    
    @RequestMapping("editRelevanceMember")
    public @ResponseBody Map<String, String> editRelevanceMember(
            String[] memberNo, String[] parentNo, String[] memberName,
            String[] contacts, String[] telNum, String[] memberType,
            String[] remark, String memberId, String delId) throws WebException
    {
        try
        {
            MemberEntity member = memberService.getMember(memberId);
            Map<String, String> map = new HashMap<String, String>();
            List<MemberToMemberEntity> list = new ArrayList<MemberToMemberEntity>();
            if (StringUtils.isNotEmpty(delId))
            {
                String delIds[] = delId.split(",");
                for (int i = 0; i < delIds.length; i++)
                {
                    mtmservice.delInfo(delIds[i]);
                }
            }
            if (null != memberNo)
            {
                for (int i = 0; i < memberNo.length; i++)
                {
                    MemberToMemberEntity re = new MemberToMemberEntity();
                    re.setMemberNo(memberNo[i]);
                    re.setMemberName(memberName[i]);
                    re.setContacts(contacts[i]);
                    re.setTelNum(telNum[i]);
                    
                    re.setParentNo(member.getMemberNo());
                    re.setParentName(member.getName());
                    re.setParentContacts(member.getContacts());
                    re.setParentTelNum(member.getTelNum());
                    re.setMemberType(Integer.valueOf(memberType[i]));
                    re.setRemark(remark[i]);
                    mtmservice.addMemberToMember(re);
                }
            }
            map.put("isok", "ok");
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询会员信息错误", e, this.getClass());
            throw new WebException("查询会员信息错误");
        }
    }
    
    /**
     * 初始化密码
     * 
     * @param query
     * @return
     */
    @RequestMapping("initializePwd")
    public @ResponseBody Map<String, Object> initializePwd(String id)
            throws WebException
    {
        try
        {
            String password = memberService.initializePwd(id);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("isOK", "ok");
            map.put("password", password);
            return map;
        }
        catch (Exception e)
        {
            SxjLogger.error("初始化密码失败", e, this.getClass());
            throw new WebException("初始化密码失败");
        }
    }
    
    /**
     * 修改会员资料
     * 
     * @param id
     * @return
     * @throws WebException
     */
    @RequestMapping("editMember")
    public @ResponseBody Map<String, String> editMember(
            @Validated({ UpdateGroup.class }) MemberEntity member,
            BindingResult result) throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            if (member.getRemark() == null)
            {
                member.setRemark("");
            }
            // getValidError(result);
            memberService.modifyMember(member, false);
            map.put("isOK", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("修改会员信息错误", e, this.getClass());
            throw new WebException(e.getMessage());
        }
        return map;
    }
    
    /**
     * 修改账户状态
     * 
     * @param id
     * @return
     */
    @RequestMapping("editState")
    public @ResponseBody Map<String, String> editState(String id, Integer state)
            throws WebException
    {
        try
        {
            memberService.editState(id, state);
            Map<String, String> map = new HashMap<String, String>();
            map.put("isOK", "ok");
            return map;
        }
        catch (Exception e)
        {
            throw new WebException(e);
        }
        
    }
    
    /**
     * 修改审核状态
     * 
     * @param id
     * @return
     */
    @RequestMapping("editCheckState")
    public @ResponseBody Map<String, String> editCheckState(String id,
            Integer state) throws WebException
    {
        try
        {
            memberService.editCheckState(id, state);
            Map<String, String> map = new HashMap<String, String>();
            map.put("isOK", "ok");
            topics.getTopic(Constraints.WEBSITE_CHANNEL_NAME)
                    .publish("editCheckState," + id);
            return map;
        }
        catch (Exception e)
        {
            throw new WebException(e);
        }
        
    }
    
    /**
     * 验证会员是否注册过
     */
    @RequestMapping("check_member")
    public @ResponseBody Map<String, String> check_member(String name, String id)
    {
        Map<String, String> map = new HashMap<String, String>();
        MemberEntity member = memberService.getMemberByName(name);
        if (member == null || member.getId().equals(id))
        {
            return null;
        }
        else
        {
            map.put("text", "该会员已存在！");
            return map;
        }
    }
    
    public void setMemberService(IMemberService memberService)
    {
        this.memberService = memberService;
    }
    
    public void setAreaService(IAreaService areaService)
    {
        this.areaService = areaService;
    }
    
    public void setTopics(RedisTopics topics)
    {
        this.topics = topics;
    }
    
    /**
     * 导出用户信息
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("exportExcel")
    public void exportExcel(HttpServletRequest request,
            HttpServletResponse response, MemberQuery query) throws Exception
    {
        List<MemberEntity> list = memberService.queryMembers(query);
        File file = null;
        PrintWriter out = null;
        WritableCellFormat wcf = null;
        try
        {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            file = new File("excel");// 地址
            file.mkdirs();// 创建目录
            file = new File(file, "会员信息.xls");
            WritableWorkbook wwb;
            wwb = Workbook.createWorkbook(new FileOutputStream(file));
            // 设置字体;
            WritableFont bold = new WritableFont(WritableFont.createFont("宋体"),
                    14, WritableFont.BOLD);
            wcf = new WritableCellFormat(bold);
            WritableSheet ws = wwb.createSheet("会员信息", 0);
            String[] columns = { "会员ID", "会员名称", "会员类型", "城市", "地址", "联系人",
                    "联系电话", "法定代表人", "注册资本", "成立日期", "资质等级", "审核状态", "账户状态",
                    "注册时间", "认证时间","第一次认证时间", "市场专员", "备注" };
            for (int i = 0; i < columns.length; i++)
            {
                ws.setColumnView(i, 20);
                ws.addCell(new Label(i, 0, columns[i], wcf));
            }
            for (int i = 0; i < list.size(); i++)
            {
                MemberEntity member = list.get(i);
                ws.addCell(new Label(0, i + 1, member.getMemberNo()));
                ws.addCell(new Label(1, i + 1, member.getName()));
                ws.addCell(new Label(2, i + 1, member.getType().getName()));
                ws.addCell(new Label(3, i + 1, strArea(member.getArea())));
                ws.addCell(new Label(4, i + 1, member.getAddress()));
                ws.addCell(new Label(5, i + 1, member.getContacts()));
                ws.addCell(new Label(6, i + 1, member.getPhoneNo()));
                
                ws.addCell(new Label(7, i + 1, member.getLegalRep()));
                ws.addCell(new Label(8, i + 1,
                        member.getRegisteredCapital() == null ? ""
                                : member.getRegisteredCapital().toString()));
                ws.addCell(new Label(9, i + 1,
                        DateTimeUtils.formatPageDate(member.getFoundedDate())));
                ws.addCell(new Label(10, i + 1,
                        (member.getLevel() == null) ? "" : member.getLevel()
                                .getName()));
                ws.addCell(new Label(11, i + 1, member.getCheckState()
                        .getName()));
                ws.addCell(new Label(12, i + 1, member.getState().getName()));
                ws.addCell(new Label(13, i + 1,
                        DateTimeUtils.formatPageDate(member.getRegDate())));
                ws.addCell(new Label(14, i + 1,
                        DateTimeUtils.formatPageDate(member.getAuthorDate())));
                ws.addCell(new Label(15, i + 1,  DateTimeUtils.formatPageDate(member.getFoundedDate())));
                ws.addCell(new Label(16, i + 1, member.getMarketers()));
                ws.addCell(new Label(17, i + 1, member.getRemark()));
            }
            wwb.write();
            wwb.close();
            int rsRows = ws.getRows();
            System.err.println(rsRows
                    + "----------------------------------------------------------");
            BufferedInputStream br = new BufferedInputStream(
                    new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            
            response.reset(); // 非常重要
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="
                    + new String(file.getName().getBytes(), "iso-8859-1"));
            
            OutputStream outStream = response.getOutputStream();
            while ((len = br.read(buf)) > 0)
                outStream.write(buf, 0, len);
            br.close();
            outStream.close();
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            out.print("<script>alert('导出用户信息失败、请重试!');</script>");
        }
        finally
        {
            if (file.exists())
            {// 下载完毕删除文件
             // file.delete();
            }
            if (out != null)
            {
                out.flush();
                out.close();
            }
        }
    }
    
    @RequestMapping("downloadPfx")
    public void createPfx(String memberNo, String accountId,
            HttpServletResponse response) throws WebException
    {
        try
        {
            MemberEntity member = memberService.memberInfo(memberNo);
            if (member == null)
            {
                return;
            }
            AccountEntity account = accountService.getAccount(accountId);
            if (account == null)
            {
                return;
            }
            String employeeName = member.getName() + "_"
                    + account.getAccountName() + "_employee";
            String employeePath = memberService.createPfx(member, account);
            ServletOutputStream output = response.getOutputStream();
            response.addHeader("Content-Disposition", "attachment;filename="
                    + employeeName + ".pfx");
            response.setContentType("application/pfx");
            byte[] bytes = FileUtil.readBytes(employeePath + employeeName
                    + ".pfx");
            output.write(bytes);
            output.flush();
            output.close();
        }
        catch (Exception e)
        {
            SxjLogger.error("生成用户证书失败", e, this.getClass());
            throw new WebException("生成用户证书失败", e);
        }
        
    }
    
    public String strArea(String area)
    {
        if (StringUtils.isEmpty(area))
        {
            return "";
        }
        String[] str = area.split(",");
        String[] str1 = str[0].split(":");
        String[] str2 = str[1].split(":");
        String str3 = str1[1] + str2[1];
        return str3;
    }
    
    @RequestMapping("getMemberImage")
    public @ResponseBody Map<String, MemberImageModel> getMemberImage(
            String imageId) throws WebException
    {
        Map<String, MemberImageModel> map = new HashMap<String, MemberImageModel>();
        try
        {
            MemberImageModel model = imageService.getMemberImageByImageId(imageId);
            map.put("model", model);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改会员信息错误", e, this.getClass());
            throw new WebException(e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("addMemberImage")
    public @ResponseBody Map<String, String> addMemberImage(
            MemberImageEntity memberImage, String[] levelids)
            throws WebException
    {
        Map<String, String> map = new HashMap<String, String>();
        try
        {
            imageService.addMemberImage(memberImage, levelids);
            map.put("isok", "ok");
        }
        catch (Exception e)
        {
            SxjLogger.error("修改会员信息错误", e, this.getClass());
            throw new WebException(e.getMessage());
        }
        return map;
    }
    
}
