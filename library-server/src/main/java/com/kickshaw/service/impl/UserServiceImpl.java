package com.kickshaw.service.impl;

import com.kickshaw.entity.User;
import com.kickshaw.mapper.UserMapper;
import com.kickshaw.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<User> listUsers(User user) {
        return userMapper.listUsers(user);
    }
}
