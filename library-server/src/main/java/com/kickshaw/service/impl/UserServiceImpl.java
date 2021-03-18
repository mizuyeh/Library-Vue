package com.kickshaw.service.impl;

import com.kickshaw.entity.Resource;
import com.kickshaw.entity.Role;
import com.kickshaw.entity.User;
import com.kickshaw.mapper.UserMapper;
import com.kickshaw.service.ResourceService;
import com.kickshaw.service.RoleService;
import com.kickshaw.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kickshaw.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    @Override
    public List<User> listUsers(User user) {
        return userMapper.listUsers(user);
    }

    @Override
    public User getByName(String username) {
        return userMapper.getByName(username);
    }

    @Override
    public Role getRole(String username) {
        User user = getByName(username);
        if(user.getRoleId() == null) {
            return null;
        }
        return roleService.getById(user.getRoleId());
    }

    @Override
    public Set<String> getPermissions(String username) {
        User user = getByName(username);
        Role role = roleService.getById(user.getRoleId());
        List<Integer> resourceIdsList = StringUtil.string2List(role.getResourceIds());
        Set<String> permissions = new HashSet<>();
        for (Integer resourceId : resourceIdsList) {
            Resource resource = resourceService.getById(resourceId);
            permissions.add(resource.getPermission());
        }
        return permissions;
    }

}
