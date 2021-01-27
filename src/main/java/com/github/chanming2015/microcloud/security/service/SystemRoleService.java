package com.github.chanming2015.microcloud.security.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.chanming2015.microcloud.security.entity.SystemRole;
import com.github.chanming2015.microcloud.security.resp.RespRole;
import com.github.chanming2015.utils.pojo.DataResult;

/**
 * Description: 角色管理 <br/> 
 * Create Date:2018年9月6日  <br/> 
 * Version:1.0.0  <br/> 
 * @author XuMaoSen
 */
public interface SystemRoleService
{
    /**
     * Description: 角色管理　创建 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<RespRole> createSystemRole(String name, String description);

    /**
     * Description: 角色管理　查询列表 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<List<SystemRole>> findRoles(List<Long> roleIds);

    /**
     * Description: 角色管理　查询列表　分页 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<Page<RespRole>> pageSystemRoles(Pageable pager);

    /**
     * Description: 角色管理　查询单个角色 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<SystemRole> findOne(Long roleId);

    /**
     * Description: 角色管理　修改 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<SystemRole> updateSystemRole(SystemRole systemRole);

    /**
     * Description: 角色管理　查询角色的API列表 <br/> 
     * Create Date:2019年5月9日 <br/> 
     * @author XuMaoSen
     * @return
     */
    DataResult<List<RespRole>> findRoleFunctions();
}
