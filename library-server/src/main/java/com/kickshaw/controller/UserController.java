package com.kickshaw.controller;


import com.kickshaw.entity.User;
import com.kickshaw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
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

    @GetMapping("/list")
    public List<User> list(User user) {
        return userService.listUsers(user);
    }

    @GetMapping("/id/{id}")
    public User findOneById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody User user) {
        System.err.println(user);
        userService.save(user);
    }

    @PutMapping("/update")
    public void update(@RequestBody User user) {
        System.err.println("updateUser result：" + userService.updateById(user));
    }

    @DeleteMapping("/delete/{ids}")
    public void delete(@PathVariable Integer[] ids){
        userService.removeByIds(Arrays.asList(ids));
    }
}
