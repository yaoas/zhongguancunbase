/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.pm.background.admin.config;

import com.pm.background.admin.common.intercept.GunsUserFilter;
import com.pm.background.admin.common.intercept.ShiroUserFilter;
import com.pm.background.admin.common.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro的配置文件
 *
 * @author Mark sunlightcs@gmail.com
 * @since 3.0.0 2017-09-27
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());

        return securityManager;
    }

    /**
     * 身份认证realm;
     */
    @Bean
    public UserRealm myShiroRealm() {
        UserRealm myShiroRealm = new UserRealm();
        return myShiroRealm;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    @Value("${spring.profiles.active}")
    private String active;

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("localhost");
        redisManager.setPort(6379);
        //  配置缓存过期时间三个月
        redisManager.setExpire(7592000);
        redisManager.setTimeout(43200000);
        if (!"dev".equals(active)){
            redisManager.setPassword("redispass123");
        }

        return redisManager;
    }




    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setGlobalSessionTimeout(43200000000L);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }




    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public static  LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(this.securityManager());
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setUnauthorizedUrl("/");

        //session过期拦截
        HashMap<String, Filter> myFilters = new HashMap<>();
        myFilters.put("user", new GunsUserFilter());
        //*跨域*/
        myFilters.put("user", new ShiroUserFilter());
        shiroFilter.setFilters(myFilters);

        Map<String, String> filterMap = new LinkedHashMap<>();

        /*swagger 资源过滤*/
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/v2/api-docs", "anon");
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/v2/api-docs-ext", "anon");
        filterMap.put("/doc.html", "anon");
        filterMap.put("/workorder/getManufactureOrders","anon");
        filterMap.put("/turnorder/add","anon");
        filterMap.put("/working/getList","anon");
        filterMap.put("/warehousing/testQr","anon");
        filterMap.put("/warehousing/checkQrCode/*","anon");
        filterMap.put("/fast/file/upload/*","anon");
        filterMap.put("/js/**","anon");
        filterMap.put("/login/**", "anon"); //登陆
        filterMap.put("/smallApp/**", "anon"); //不在拦截这类请求
        filterMap.put("/kaptcha", "anon");  //验证码
        filterMap.put("/global/*", "anon");  //全局路径（错误或者超时）
        filterMap.put("/mbp3erTHiU.txt", "anon");
        filterMap.put("/9HF3VfACcX.txt", "anon");
        filterMap.put("/favicon.ico", "anon");
        //项目接口放行
        filterMap.put("/commodityInfo/*", "anon");
        filterMap.put("/wxLogin", "anon");
        filterMap.put("/wheatherLogin", "anon");
        filterMap.put("/phoneSec", "anon");
        filterMap.put("/commoditySpec/*", "anon");
        filterMap.put("/storeInfo/*", "anon");
        filterMap.put("/rotationChart/*", "anon");
        filterMap.put("/file/*", "anon");
        filterMap.put("/changeLittleUser", "anon");
        filterMap.put("/addressManage/*", "anon");
        filterMap.put("/commodityTime/queryById", "anon");
        filterMap.put("/tset", "anon");
        filterMap.put("/orderInfo/rechargeNotify", "anon");
        filterMap.put("/orderInfo/orderNotify", "anon");
        filterMap.put("/**", "user");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }



    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
