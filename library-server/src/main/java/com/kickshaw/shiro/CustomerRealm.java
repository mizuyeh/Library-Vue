package com.kickshaw.shiro;

import com.kickshaw.entity.Role;
import com.kickshaw.entity.User;
import com.kickshaw.service.UserService;
import com.kickshaw.shiro.JwtToken;
import com.kickshaw.shiro.JwtUtil;
import com.kickshaw.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description 自定义realm
 * @date 2021/3/16
 */
@Slf4j
@Component("CustomerRealm")
public class CustomerRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用来进行身份认证，也就是说验证用户输入的账号和密码是否正确，
     * 获取身份验证信息，错误抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.info("————————身份认证——————————");
        String token = (String) auth.getCredentials();
        if (null == token) {
            throw new AuthenticationException("token为空!");
        }
        // 解密获得username，用于和数据库进行对比
        String userName = JwtUtil.parseTokenAud(token);
        User user = userService.getByName(userName);
        if (null == user) {
            throw new AuthenticationException("用户不存在!");
        }
        // 校验token是否过期
        if (!tokenRefresh(token, user)) {
            throw new AuthenticationException("Token已过期!");
        }
        return new SimpleAuthenticationInfo(user, token, getName());
    }

    /**
     * 获取用户权限信息，包括角色以及权限。
     * 只有当触发检测用户权限时才会调用此方法，例如checkRole,checkPermissionJwtToken
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("————权限认证 [ roles、permissions]————");
        User user = null;
        if (principals != null) {
            user = (User) principals.getPrimaryPrincipal();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (user != null) {
            // 用户拥有的角色，比如“admin/user”
            Role role = userService.getRole(user.getUserName());
            // 如果user没有角色则返回
            if(role == null) {
                return simpleAuthorizationInfo;
            }
            String roleName = role.getRoleName();
            simpleAuthorizationInfo.addRole(roleName);
            log.info("角色为：" + roleName);
            // 用户拥有的权限集合，比如“role:add,user:add”
            Set<String> permissions = userService.getPermissions(user.getUserName());
            simpleAuthorizationInfo.addStringPermissions(permissions);
            log.info("权限有："+permissions.toString());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * JWT Token续签：
     * 业务逻辑：登录成功后，用户在未过期时间内继续操作，续签token。
     *         登录成功后，空闲超过过期时间，返回token已失效，重新登录。
     * 实现逻辑：
     *    1.登录成功后将token存储到redis里面(这时候k、v值一样都为token)，并设置过期时间为token过期时间
     *    2.当用户请求时token值还未过期，则重新设置redis里token的过期时间。
     *    3.当用户请求时token值已过期，但redis中还在，则JWT重新生成token并覆盖v值(这时候k、v值不一样了)，然后设置redis过期时间。
     *    4.当用户请求时token值已过期，并且redis中也不存在，则用户空闲超时，返回token已失效，重新登录。
     */
    public boolean tokenRefresh(String token, User user) {
        String cacheToken = redisUtil.getCacheObject(token);
        // 过期后会得到"null"值，所以需判断字符串"null"
        if (cacheToken != null && cacheToken.length() != 0 && !"null".equals(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.isVerify(cacheToken)) {
                // 生成token
                String newToken = JwtUtil.createToken(user);
                // 将token存入redis,并设置超时时间
                redisUtil.setCacheObject(token, newToken, JwtUtil.getExpireTime(), TimeUnit.SECONDS);
            } else {
                // 重新设置超时时间
                redisUtil.expire(token, JwtUtil.getExpireTime());
            }
            log.info("存入redis的过期时间：" + redisUtil.getExpire(token));
            return true;
        }
        return false;
    }
}