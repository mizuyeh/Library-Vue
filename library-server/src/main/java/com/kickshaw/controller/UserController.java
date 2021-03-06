package com.kickshaw.controller;


import com.kickshaw.entity.User;
import com.kickshaw.service.UserService;
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
 * @since 2021-03-14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/list")
    @RequiresPermissions("user:view")
    public List<User> list(User user) {
        String key = "user::" + user.hashCode();
        List<User> users = redisUtil.getCacheList(key);
        if (users.isEmpty()) {
            users = userService.listUsers(user);
            if(!users.isEmpty()) {
                redisUtil.setCacheList(key, users);
            }
        }
        return users;
    }

    @GetMapping("/id/{id}")
    @RequiresPermissions("user:view")
    public User findOneById(@PathVariable Integer id) {
        String key = "user::" + id;
        if(redisUtil.getCacheObject(key) != null) {
            return redisUtil.getCacheObject(key);
        } else {
            User user =  userService.getById(id);
            redisUtil.setCacheObject(key, user);
            return user;
        }
    }

    @PostMapping("/save")
    @RequiresPermissions("user:create")
    public boolean save(@RequestBody User user) {
        redisUtil.deletePattern("user*");
        System.err.println(user);
        return userService.save(user);
    }

    @PutMapping("/update")
    @RequiresPermissions("user:update")
    public boolean update(@RequestBody User user) {
        redisUtil.deletePattern("user*");
        return userService.updateById(user);
    }

    @DeleteMapping("/delete/{ids}")
    @RequiresPermissions("user:delete")
    public boolean delete(@PathVariable List<Integer> ids){
        redisUtil.deletePattern("user*");
        return userService.removeByIds(ids);
    }
}
