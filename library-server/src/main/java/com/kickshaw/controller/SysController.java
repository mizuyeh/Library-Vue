package com.kickshaw.controller;

import com.kickshaw.entity.User;
import com.kickshaw.service.SysService;
import com.kickshaw.service.UserService;
import com.kickshaw.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @date 2021/3/16
 */
@RestController
public class SysController {
    @Autowired
    private SysService sysService;
    @Autowired
    private UserService userService;

    /**
     * 注册(用户名，密码)
     * @param user
     * @return
     */
    @PostMapping("/register")
    public JsonData<String> register(@RequestBody User user) {
        System.out.println("register: " + user);
        return sysService.register(user.getUserName(), user.getPassword());
    }

    /**
     * 登录(用户名，密码)
     * @param user
     * @return
     */
    @PostMapping("/login")
    public JsonData<String> login(@RequestBody User user) {
        return sysService.login(user.getUserName(), user.getPassword());
    }

    /**
     * 处理非法请求
     */
    @GetMapping("/unauthorized")
    public JsonData unauthorized() {
        return new JsonData(401, "Token失效请重新登录!");
    }
}
