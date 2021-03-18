package com.kickshaw.service;

import com.kickshaw.entity.Role;
import com.kickshaw.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-14
 */
public interface UserService extends IService<User> {
    List<User> listUsers(User user);

    User getByName(String username);
    /**
     * 根据用户名找对应角色
     * @Param [username]
     * @Return com.kickshaw.entity.Role
     */
    Role getRole(String username);

    /**
     * 根据用户名找权限
     * @Param [username]
     * @Return java.util.List<java.lang.String>
     */
    Set<String> getPermissions(String username);
}
