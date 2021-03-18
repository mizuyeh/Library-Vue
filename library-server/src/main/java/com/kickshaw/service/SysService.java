package com.kickshaw.service;

import com.kickshaw.entity.User;
import com.kickshaw.utils.JsonData;

import java.util.Map;
import java.util.Set;

/**
 * @Description shiro相关的服务
 * @date 2021/3/17
 */
public interface SysService {
    /**
     * 注册(用户名，密码)
     * @param userName
     * @param password
     * @return String
     */
    JsonData<String> register(String userName, String password);

    /**
     * 登录(用户名，密码)
     * @param userName
     * @param password
     * @return String
     */
    JsonData<String> login(String userName, String password);

    /**
     * 根据userName查找用户，自定义Realm中调用
     *
     * @param userName
     * @return User
     */
    public User selectByuserName(String userName);

    /**
     * 根据userName查找用户角色名，自定义Realm中调用
     *
     * @param userName
     * @return roles
     */
    public String getRoleByUserName(String userName);

    /**
     * 根据userName查找用户权限，自定义Realm中调用
     *
     * @param userName
     * @return Set<permissions>
     */
    public Set<String> getPermissionsByUserName(String userName);

}
