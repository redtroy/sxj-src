package com.sxj.birt.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BirtReportController
{
    
    // 10104
    @RequestMapping("/orders/{orderId}.html")
    public ModelAndView customerReport(@PathVariable("orderId") String orderId)
    {
        
        Map<String, Object> modelData = new HashMap<String, Object>();
        modelData.put("order", orderId);
        return new ModelAndView("orderDetails", modelData);
    }
    
    @RequestMapping("/masterReport")
    public ModelAndView masterReport()
    {
        Map<String, Object> modelData = new HashMap<String, Object>();
        return new ModelAndView("masterReport", modelData);
    }
    
}
