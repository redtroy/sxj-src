package com.sxj.supervisor.manage.controller.projectmessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.supervisor.manage.controller.BaseController;
import com.sxj.util.exception.WebException;

@RequestMapping("projectMessage")
@Controller
public class ProjectMessageManagerController extends BaseController
{
    
    @RequestMapping("uploadPage")
    public String uploadPage() throws WebException
    {
        return "manage/projectManage/projectinfopub";
    }
}
