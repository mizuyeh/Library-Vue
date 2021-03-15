package com.kickshaw.mapper;

import com.kickshaw.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-15
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> listRoles(Role role);
}
