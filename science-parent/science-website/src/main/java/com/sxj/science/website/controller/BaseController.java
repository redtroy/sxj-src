package com.sxj.science.website.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.sxj.util.exception.SystemException;
import com.sxj.util.logger.SxjLogger;

public class BaseController
{
    
    public static final String LOGIN = "redirect:/to_login.htm";
    
    public static final String INDEX = "site/index";
    
    public static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        //        binder.registerCustomEditor(Date.class, new CustomDateEditor(
        //                dateFormat, false));
        //        binder.registerCustomEditor(PersonnelCompanyEnum.class,
        //                new EnumPropertyEditorSupport<PersonnelCompanyEnum>(
        //                        PersonnelCompanyEnum.class));
        //        binder.registerCustomEditor(InvestItemStateEnum.class,
        //                new EnumPropertyEditorSupport<InvestItemStateEnum>(
        //                        InvestItemStateEnum.class));
        //        binder.registerCustomEditor(CustomerLevelEnum.class,
        //                new EnumPropertyEditorSupport<CustomerLevelEnum>(
        //                        CustomerLevelEnum.class));
        //        binder.registerCustomEditor(ContractTypeEnum.class,
        //                new EnumPropertyEditorSupport<ContractTypeEnum>(
        //                        ContractTypeEnum.class));
        //        binder.registerCustomEditor(RecordStateEnum.class,
        //                new EnumPropertyEditorSupport<RecordStateEnum>(
        //                        RecordStateEnum.class));
        //        binder.registerCustomEditor(MemberTypeEnum.class,
        //                new EnumPropertyEditorSupport<MemberTypeEnum>(
        //                        MemberTypeEnum.class));
        //        binder.registerCustomEditor(ContractWindowTypeEnum.class,
        //                new EnumPropertyEditorSupport<ContractWindowTypeEnum>(
        //                        ContractWindowTypeEnum.class));
        //        // RFIT枚举
        //        binder.registerCustomEditor(RfidTypeEnum.class,
        //                new EnumPropertyEditorSupport<RfidTypeEnum>(RfidTypeEnum.class));
        //        binder.registerCustomEditor(PayStateEnum.class,
        //                new EnumPropertyEditorSupport<PayStateEnum>(PayStateEnum.class));
        //        binder.registerCustomEditor(ReceiptStateEnum.class,
        //                new EnumPropertyEditorSupport<ReceiptStateEnum>(
        //                        ReceiptStateEnum.class));
        //        
        //        binder.registerCustomEditor(AssociationTypesEnum.class,
        //                new EnumPropertyEditorSupport<AssociationTypesEnum>(
        //                        AssociationTypesEnum.class));
        //        binder.registerCustomEditor(AuditStateEnum.class,
        //                new EnumPropertyEditorSupport<AuditStateEnum>(
        //                        AuditStateEnum.class));
        //        binder.registerCustomEditor(RfidTypeEnum.class,
        //                new EnumPropertyEditorSupport<RfidTypeEnum>(RfidTypeEnum.class));
        //        binder.registerCustomEditor(WindowTypeEnum.class,
        //                new EnumPropertyEditorSupport<WindowTypeEnum>(
        //                        WindowTypeEnum.class));
        //        binder.registerCustomEditor(String.class,
        //                new TrimStringPropertyEditorSupport());
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
    
    protected String getLoginInfo()
    {
        //        Subject subject = SecurityUtils.getSubject();
        //        if (subject.getPrincipal() == null)
        //        {
        //            return null;
        //        }
        //        PersonnelEntity user = (String) subject.getPrincipal();
        //        return user;
        return "";
        
    }
    
}
