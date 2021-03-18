package com.kickshaw.mapper;

import com.kickshaw.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kickshaw
 * @since 2021-03-14
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> listUsers(User user);

    User getByName(String username);
}
