package com.github.chanming2015.microcloud.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Description: 对应资源URL  <br/> 
 * Create Date:2018年8月29日  <br/> 
 * Version:1.0.0  <br/> 
 * @author XuMaoSen
 */
@Entity
public class SystemFunction extends BaseEntity
{
    private static final long serialVersionUID = -2964909775391565832L;
    @Column(updatable = false, nullable = false)
    private String name; // 资源名称
    @Column(updatable = false, nullable = false)
    private String actionUrl; // 资源路径
    @Column(updatable = false)
    private String method;// 请求方式，为空代表所有请求方式

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getActionUrl()
    {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl)
    {
        this.actionUrl = actionUrl;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }
}
