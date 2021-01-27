package com.github.chanming2015.microcloud.security.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 * Description: 系统角色 <br/> 
 * Create Date:2018年8月29日  <br/> 
 * Version:1.0.0  <br/> 
 * @author XuMaoSen
 */
@Entity
public class SystemRole extends BaseEntity
{
    private static final long serialVersionUID = -8303384761473305312L;
    @Column(updatable = false, nullable = false)
    private String name; // 名称
    private String description; // 描述

    @OneToMany
    @JoinTable(name = "system_role_function", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "function_id", referencedColumnName = "id")})
    private Set<SystemFunction> funcs; // API集合

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

    public Set<SystemFunction> getFuncs()
    {
        return funcs;
    }

    public void setFuncs(Set<SystemFunction> funcs)
    {
        this.funcs = funcs;
    }
}
