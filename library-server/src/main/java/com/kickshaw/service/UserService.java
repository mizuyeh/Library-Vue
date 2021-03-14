package com.kickshaw.service;

import com.kickshaw.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
