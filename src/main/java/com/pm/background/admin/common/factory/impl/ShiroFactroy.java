package com.pm.background.admin.common.factory.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pm.background.admin.common.factory.IConstantFactory;
import com.pm.background.admin.common.factory.IShiro;
import com.pm.background.admin.common.shiro.ShiroUser;
import com.pm.background.admin.sys.dao.MenuMapper;
import com.pm.background.admin.sys.dao.UserMapper;
import com.pm.background.admin.sys.entity.Menu;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.common.constant.Constant;
import com.pm.background.common.utils.SpringContextHolder;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//import com.hsshy.beam.common.constant.Constant;
//import com.hsshy.beam.common.factory.IConstantFactory;
//import com.hsshy.beam.common.shiro.ShiroUser;
//import com.hsshy.beam.common.utils.SpringContextHolder;
//import com.hsshy.beam.common.factory.IShiro;
//import com.hsshy.beam.sys.dao.MenuMapper;
//import com.hsshy.beam.sys.dao.UserMapper;
//import com.hsshy.beam.sys.entity.Menu;
//import com.hsshy.beam.sys.entity.User;

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
public class ShiroFactroy implements IShiro {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    public static IShiro me() {
        return SpringContextHolder.getBean(IShiro.class);
    }

    @Override
    public User user(String account) {

        //查询用户信息
        User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getAccount,account));


        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        return user;
    }

    @Override
    public ShiroUser shiroUser(User user) {
        ShiroUser shiroUser = new ShiroUser();

        shiroUser.setId(user.getId());
        shiroUser.setAccount(user.getAccount());
        shiroUser.setDeptId(user.getDeptId());
        IConstantFactory me = ConstantFactory.me();
        String depName = me.getDeptName(user.getDeptId());
        shiroUser.setDeptName(depName);
        shiroUser.setName(user.getUserNickNameUTF8());
        shiroUser.setAvatar(user.getAvatarUrl());
        shiroUser.setPhone(user.getPhone());

        List<Long> roleList = ConstantFactory.me().getRoleIdsById(user.getId());
        List<String> roleNameList = new ArrayList<String>();
        for (Long roleId : roleList) {
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId));
        }
        shiroUser.setRoleList(roleList);
        shiroUser.setRoleNames(roleNameList);

        return shiroUser;
    }

    @Override
    public List<String> findPermissionsByUserId(Long userId) {

        List<String> permsList;
        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<Menu> menuList = menuMapper.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(Menu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = userMapper.queryAllPerms(userId);
        }
        return permsList;
    }



    @Override
    public SimpleAuthenticationInfo info(ShiroUser shiroUser, User user, String realmName) {
        String credentials = user.getPassword();

        return new SimpleAuthenticationInfo(shiroUser, credentials, ByteSource.Util.bytes(user.getSalt()), realmName);
    }

}
