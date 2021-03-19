package com.kickshaw.controller;


import com.kickshaw.entity.Role;
import com.kickshaw.service.RoleService;
import com.kickshaw.utils.RedisUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-15
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/list")
    @RequiresPermissions("role:view")
    public List<Role> list(Role role) {
        String key = "role::" + role.hashCode();
        List<Role> roles = redisUtil.getCacheList(key);
        if (roles.isEmpty()) {
            roles = roleService.listRoles(role);
            if(!roles.isEmpty()) {
                redisUtil.setCacheList(key, roles);
            }
        }
        return roles;
    }

    @GetMapping("/id/{id}")
    @RequiresPermissions("role:view")
    public Role findOneById(@PathVariable Integer id) {
        String key = "role::" + id;
        if(redisUtil.getCacheObject(key) != null) {
            return redisUtil.getCacheObject(key);
        } else {
            Role role =  roleService.getById(id);
            redisUtil.setCacheObject(key, role);
            return role;
        }
    }

    @PostMapping("/save")
    @RequiresPermissions("role:create")
    public boolean add(@RequestBody Role role) {
        System.out.println(role);
        return roleService.save(role);
    }

    @PutMapping("/update")
    @RequiresPermissions("role:update")
    public boolean update(@RequestBody Role role) {
        redisUtil.deletePattern("role*");
        return roleService.updateById(role);
    }

    @DeleteMapping("/delete/{ids}")
    @RequiresPermissions("role:delete")
    public boolean delete(@PathVariable List<Integer> ids){
        System.out.println(ids);
        redisUtil.deletePattern("role*");
        return roleService.removeByIds(ids);
    }
}

