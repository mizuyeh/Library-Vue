package com.kickshaw.controller;


import com.kickshaw.entity.Role;
import com.kickshaw.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/list")
    public List<Role> list(Role role) {
        return roleService.listRoles(role);
    }
}

