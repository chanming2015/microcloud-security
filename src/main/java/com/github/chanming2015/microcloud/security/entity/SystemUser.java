package com.github.chanming2015.microcloud.security.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

/**
 * Description: 系统用户 <br/> 
 * Create Date:2018年8月29日  <br/> 
 * Version:1.0.0  <br/> 
 * @author XuMaoSen
 */
@Entity
public class SystemUser extends BaseEntity
{
    private static final long serialVersionUID = -1819900863679785803L;
    @Column(unique = true, updatable = false, nullable = false)
    private String loginname; // 用户名
    @Column(nullable = false)
    private String password; // 密码，数据库中保存密文
    @Column(updatable = false, nullable = false)
    private String secretkey; // 密钥

    @OneToMany
    @JoinTable(name = "system_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<SystemRole> roles; // 角色集合

    public String getLoginname()
    {
        return loginname;
    }

    public void setLoginname(String loginname)
    {
        this.loginname = loginname;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSecretkey()
    {
        return secretkey;
    }

    public void setSecretkey(String secretkey)
    {
        this.secretkey = secretkey;
    }

    public Set<SystemRole> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<SystemRole> roles)
    {
        this.roles = roles;
    }
}
