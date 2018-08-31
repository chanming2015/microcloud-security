package com.github.chanming2015.microcloud.security.entity;

import javax.persistence.Entity;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

/**
 * Description:
 * Create Date:2018年8月29日
 * @author XuMaoSen
 * Version:1.0.0
 */
@Entity
public class SystemRole extends BaseEntity
{
    private static final long serialVersionUID = -8303384761473305312L;
    private String name; // 名称
    private String description; // 描述

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ConfigAttribute getAttribute()
    {
        return new SecurityConfig(name);
    }
}
