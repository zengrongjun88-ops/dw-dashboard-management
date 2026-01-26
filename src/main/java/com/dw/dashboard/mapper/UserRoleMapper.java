package com.dw.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dw.dashboard.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联Mapper接口
 *
 * @author DW Team
 * @since 2026-01-26
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
