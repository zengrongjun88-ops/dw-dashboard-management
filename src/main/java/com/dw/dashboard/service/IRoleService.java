package com.dw.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dw.dashboard.entity.Role;

/**
 * 角色服务接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
public interface IRoleService extends IService<Role> {

    /**
     * 根据角色编码查询角色
     *
     * @param roleCode 角色编码
     * @return 角色实体
     */
    Role getByRoleCode(String roleCode);

}
