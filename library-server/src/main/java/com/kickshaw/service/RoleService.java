package com.kickshaw.service;

import com.kickshaw.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-15
 */
public interface RoleService extends IService<Role> {
    List<Role> listRoles(Role role);
}
