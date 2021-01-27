package com.github.chanming2015.microcloud.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.chanming2015.microcloud.security.entity.SystemUser;
import com.github.chanming2015.microcloud.security.resp.RespUser;
import com.github.chanming2015.utils.pojo.DataResult;
import com.github.chanming2015.utils.sql.SpecParam;

/**
 * Description: 用户管理接口 <br/> 
 * Create Date:2018年8月30日  <br/> 
 * Version:1.0.0  <br/> 
 * @author XuMaoSen
 */
public interface SystemUserService
{
    List<Long> getRoleIds(String username);

    /**
     * Description: 用户管理　查询列表 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<Page<RespUser>> pageSystemUsers(SpecParam<SystemUser> specParam, Pageable pager);

    /**
     * Description: 用户管理　登录 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<RespUser> findByLoginname(String username);

    /**
     * Description: 用户管理　查询单个 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<SystemUser> findOne(Long userId);

    /**
     * Description: 用户管理　注册 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @param password 经过 AES/ECB/NoPadding 加密后的密文
     * @return
     */
    DataResult<RespUser> createSystemUser(String username, String password);

    /**
     * Description: 用户管理　修改 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<Boolean> updateSystemUser(SystemUser systemUser);

    /**
     * Description: 用户管理　修改状态 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<?> enableSystemUser(Long id, Boolean enable);

    /**
     * Description: 用户管理 修改密码 <br/> 
     * Create Date:2019年8月19日 <br/> 
     * @author XuMaoSen
     * @param oldPass 经过 AES/ECB/NoPadding 加密后的密文
     * @param newPass 经过 AES/ECB/NoPadding 加密后的密文
     * @return
     */
    DataResult<?> changeUserPass(Long userId, String oldPass, String newPass);

    /**
     * Description: 用户管理 强制修改密码 <br/> 
     * Create Date:2020年9月3日 <br/> 
     * @author XuMaoSen
     * @param newPass 经过 AES/ECB/NoPadding 加密后的密文
     * @return
     */
    DataResult<?> changeUserPassForce(Long userId, String newPass);
}
