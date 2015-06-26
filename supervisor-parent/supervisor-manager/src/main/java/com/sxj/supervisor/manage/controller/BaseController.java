package com.sxj.supervisor.manage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.sxj.supervisor.entity.system.SystemAccountEntity;
import com.sxj.supervisor.enu.contract.ContractWindowTypeEnum;
import com.sxj.supervisor.enu.member.LevelEnum;
import com.sxj.supervisor.enu.member.MemberTypeEnum;
import com.sxj.supervisor.enu.record.ContractTypeEnum;
import com.sxj.supervisor.enu.record.RecordStateEnum;
import com.sxj.supervisor.enu.record.RecordTypeEnum;
import com.sxj.supervisor.enu.rfid.RfidTypeEnum;
import com.sxj.supervisor.enu.rfid.apply.PayStateEnum;
import com.sxj.supervisor.enu.rfid.apply.ReceiptStateEnum;
import com.sxj.supervisor.enu.rfid.ref.AssociationTypesEnum;
import com.sxj.supervisor.enu.rfid.ref.AuditStateEnum;
import com.sxj.supervisor.enu.rfid.window.WindowTypeEnum;
import com.sxj.util.exception.SystemException;
import com.sxj.util.logger.SxjLogger;

public class BaseController
{
    
    public static final String LOGIN = "manage/login";
    
    public static final String INDEX = "manage/index";
    
    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
        binder.registerCustomEditor(MemberTypeEnum.class,
                new EnumPropertyEditorSupport<MemberTypeEnum>(
                        MemberTypeEnum.class));
        binder.registerCustomEditor(RecordTypeEnum.class,
                new EnumPropertyEditorSupport<RecordTypeEnum>(
                        RecordTypeEnum.class));
        binder.registerCustomEditor(ContractTypeEnum.class,
                new EnumPropertyEditorSupport<ContractTypeEnum>(
                        ContractTypeEnum.class));
        binder.registerCustomEditor(RecordStateEnum.class,
                new EnumPropertyEditorSupport<RecordStateEnum>(
                        RecordStateEnum.class));
        binder.registerCustomEditor(MemberTypeEnum.class,
                new EnumPropertyEditorSupport<MemberTypeEnum>(
                        MemberTypeEnum.class));
        binder.registerCustomEditor(ContractWindowTypeEnum.class,
                new EnumPropertyEditorSupport<ContractWindowTypeEnum>(
                        ContractWindowTypeEnum.class));
        // RFIT枚举
        binder.registerCustomEditor(RfidTypeEnum.class,
                new EnumPropertyEditorSupport<RfidTypeEnum>(RfidTypeEnum.class));
        binder.registerCustomEditor(PayStateEnum.class,
                new EnumPropertyEditorSupport<PayStateEnum>(PayStateEnum.class));
        binder.registerCustomEditor(ReceiptStateEnum.class,
                new EnumPropertyEditorSupport<ReceiptStateEnum>(
                        ReceiptStateEnum.class));
        
        binder.registerCustomEditor(AssociationTypesEnum.class,
                new EnumPropertyEditorSupport<AssociationTypesEnum>(
                        AssociationTypesEnum.class));
        binder.registerCustomEditor(AuditStateEnum.class,
                new EnumPropertyEditorSupport<AuditStateEnum>(
                        AuditStateEnum.class));
        binder.registerCustomEditor(RfidTypeEnum.class,
                new EnumPropertyEditorSupport<RfidTypeEnum>(RfidTypeEnum.class));
        binder.registerCustomEditor(WindowTypeEnum.class,
                new EnumPropertyEditorSupport<WindowTypeEnum>(
                        WindowTypeEnum.class));
        binder.registerCustomEditor(LevelEnum.class,
                new EnumPropertyEditorSupport<LevelEnum>(
                        LevelEnum.class));
        
        binder.registerCustomEditor(String.class,
                new TrimStringPropertyEditorSupport());
    }
    
    protected String getBasePath(HttpServletRequest request)
    {
        return request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/";
    }
    
    // static {
    // CometEngine engine = CometContext.getInstance().getEngine();
    // // 启动 Comet Server Thread
    // MessageThread cometServer = MessageThread.newInstance(engine);
    // RedisTopics redis = RedisTopics.create();
    // RTopic<String> topic1 = redis.getTopic("topic1");
    //
    // // cometServer.run();
    // // cometServer.setDaemon(true);
    // // cometServer.setDelay(3);
    // // cometServer.setPeriod(2);
    // // cometServer.schedule();
    // // MessageConnectListener lis = new MessageConnectListener();
    // engine.addConnectListener(new MessageConnectListener());
    // // MessageDropListener drop = new MessageDropListener();
    // engine.addDropListener(new MessageDropListener());
    //
    // }''
    
//    protected void registChannel(final String channelName)
//    {
//        if (!CometContext.getInstance().getAppModules().contains(channelName))
//        {
//            CometContext.getInstance().registChannel(channelName);// 注册应用的channel
//        }
//    }
    
    protected String getValidError(BindingResult result) throws SystemException
    {
        String message = "";
        if (result.hasErrors())
        {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError error : errors)
            {
                if (error == null)
                {
                    continue;
                }
                message = message + error.getDefaultMessage();
            }
            SxjLogger.error("Nested path [" + result.getNestedPath()
                    + "] has errors [" + message + "]", this.getClass());
            throw new SystemException(message);
        }
        return message;
    }
    
    protected SystemAccountEntity getLoginInfo(HttpSession session)
    {
        Object object = session.getAttribute("userinfo");
        if (object != null)
        {
            SystemAccountEntity user = (SystemAccountEntity) object;
            return user;
        }
        return null;
        
    }
    
}
