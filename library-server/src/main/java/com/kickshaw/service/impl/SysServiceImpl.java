package com.kickshaw.service.impl;

import com.kickshaw.entity.User;
import com.kickshaw.service.SysService;
import com.kickshaw.service.UserService;
import com.kickshaw.utils.JsonData;
import com.kickshaw.shiro.JwtUtil;
import com.kickshaw.shiro.Md5Util;
import com.kickshaw.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @date 2021/3/17
 */
@Service
public class SysServiceImpl implements SysService {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    /**
     * 用户注册(用户名，密码)
     *
     * @param userName 用户名
     * @param password 密码
     * @return token
     */
    @Override
    public JsonData<String> register(String userName, String password) {
        //检查用户名是否被占用
        User user = userService.getByName(userName);
        if(user != null) {
            return new JsonData<>( -1, "用户名被占用");
        }
        //添加用户信息
        user = new User();
        //设置用户名
        user.setUserName(userName);
        //密码加密后再保存
        String salt = Md5Util.salt();
        String md5Password = Md5Util.md5(password+salt);
        user.setPassword(md5Password);
        user.setSalt(salt);
        //添加到数据库
        boolean result = userService.save(user);
        //返回信息
        if(result) {
            //生成token给用户
            String token = getToken(user);
            return new JsonData<>(0,"注册成功", token);
        }else {
            return new JsonData<>( -1, "注册失败");
        }
    }

    /**
     * 用户登录(用户名，密码)
     *
     * @param userName 用户名
     * @param password 密码
     * @return token
     */
    @Override
    public JsonData<String> login(String userName, String password) {
        //处理比对密码
        User user = userService.getByName(userName);
        if(user != null) {
            String salt = user.getSalt();
            String md5Password = Md5Util.md5(password+salt);
            String dbPassword = user.getPassword();
            if(md5Password.equals(dbPassword)) {
                //生成token给用户
                String token = getToken(user);
                return new JsonData<>(0,"登录成功", token);
            }
        }
        return new JsonData<>( -1, "登录失败");
    }

    private String getToken(User user){
        // 生成token
        String token = JwtUtil.createToken(user);
        // 为了过期续签，将token存入redis，并设置超时时间
        redisUtil.setCacheObject(token, token, JwtUtil.getExpireTime(), TimeUnit.SECONDS);

        return token;
    }

    /**
     * 根据userName查找用户，自定义Realm中调用
     *
     * @param userName
     * @return User
     */
    @Override
    public User selectByuserName(String userName) {
        return userService.getByName(userName);
    }

    /**
     * 根据userName查找用户角色名，自定义Realm中调用
     *
     * @param userName
     * @return roles
     */
    @Override
    public String getRoleByUserName(String userName) {
        return userService.getRole(userName).getRoleName();
    }

    /**
     * 根据userName查找用户权限，自定义Realm中调用
     *
     * @param userName
     * @return Set<permissions>
     */
    @Override
    public Set<String> getPermissionsByUserName(String userName) {
        return userService.getPermissions(userName);
    }
}
