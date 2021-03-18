package com.kickshaw.config;

import com.kickshaw.shiro.CustomerRealm;
import com.kickshaw.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description
 * @date 2021/3/16
 */
@Configuration
public class ShiroConfig {
    @Value("${shiro.password.algorithmName}")
    private String algorithmName;

    @Value("${shiro.password.hashIterations}")
    private int hashIterations;

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        System.out.println("ShiroConfig ShiroFilterFactoryBean 执行");
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //设置SecurityManager
        shiroFilter.setSecurityManager(securityManager);
        //如果访问需要登录的某个接口，却没有登录，则调用此接口(如果不是前后端分离，则跳转页面)
        //shiroFilter.setLoginUrl("/login");
        //shiroFilterFactoryBean.setLoginUrl("/xxx.jsp");
        //登录成功后，跳转的链接，若前后端分离，没必要设置这个
        //shiroFilterFactoryBean.setSuccessUrl("");
        //登录成功，未授权会调用此方法
        shiroFilter.setUnauthorizedUrl("/unauthorized");
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断，规则：http://shiro.apache.org/web.html#urls-
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");
        //登录用户才可以访问
        //filterChainDefinitionMap.put("/authc/**", "authc");
        //管理员角色才能访问
        //filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //有编辑权限才能访问
        //filterChainDefinitionMap.put("/video/update", "perms[video_update]");
        //authc：url必须通过认证才可以访问
        //anon：url可以匿名访问
        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilter.setFilters(filterMap);

        // 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilter;
    }

    @Bean
    public SecurityManager securityManager(CustomerRealm customerRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //设置密码验证器
        //customerRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        //如果不是前后端分离，不用设置setSessionManager
        securityManager.setRealm(customerRealm);
        /*
         * 关闭shiro自带的session，详情见文档
         * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 密码验证器，双重md5
     *
     * @return
     */
    /*@Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法，使用md5算法
        hashedCredentialsMatcher.setHashAlgorithmName(algorithmName);
        //散列次数，使用2次md5算法，相当于md5(md5(xxx))
        hashedCredentialsMatcher.setHashIterations(hashIterations);
        return hashedCredentialsMatcher;
    }*/

    /**
     * 添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
