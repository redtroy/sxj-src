package com.sxj.supervisor.website.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 特殊字符拦截器
 */
public class CharacterFilter implements Filter
{
    
    private String[] characterParams = null;
    
    private boolean OK = true;
    
    /**
     * Default constructor. 
     */
    public CharacterFilter()
    {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * @see Filter#destroy()
     */
    public void destroy()
    {
    }
    
    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest servletrequest = (HttpServletRequest) request;
        HttpServletResponse servletresponse = (HttpServletResponse) response;
        boolean status = false;
        Enumeration params = request.getParameterNames();
        String param = "";
        String paramValue = "";
        servletresponse.setContentType("text/html");
        servletresponse.setCharacterEncoding("utf-8");
        while (params.hasMoreElements())
        {
            param = (String) params.nextElement();
            String[] values = request.getParameterValues(param);
            paramValue = "";
            if (OK)
            {
                for (int i = 0; i < values.length; i++)
                    paramValue = paramValue + values[i];
                for (int i = 0; i < characterParams.length; i++)
                    if (paramValue.indexOf(characterParams[i]) >= 0)
                    {
                        status = true;
                        break;
                    }
                if (status)
                    break;
            }
        }
        if (status)
        {
            String retUrl = servletrequest.getHeader("Referer");
            if (retUrl != null)
            {
                PrintWriter out = servletresponse.getWriter();
                out.print("<script language='javascript'>alert(\"您提交的相关数据含有非法字符。如：\\\"'\\\".\");window.location.href='"
                        + retUrl + "';</script>");
            }
        }
        else
        {
            chain.doFilter(request, response);
        }
    }
    
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException
    {
        if (fConfig.getInitParameter("characterParams").length() < 1)
        {
            OK = false;
        }
        else
        {
            this.characterParams = fConfig.getInitParameter("characterParams")
                    .split(",");
        }
    }
    
}
