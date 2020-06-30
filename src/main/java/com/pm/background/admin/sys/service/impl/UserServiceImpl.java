package com.pm.background.admin.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.background.admin.common.shiro.ShiroUtils;
import com.pm.background.admin.sys.dao.UserMapper;
import com.pm.background.admin.sys.dto.ChangePassowdForm;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.admin.sys.service.IUserService;
import com.pm.background.admin.sys.sysrolemenu.entity.SysRoleMenuEntity;
import com.pm.background.admin.sys.sysrolemenu.service.SysRoleMenuService;
import com.pm.background.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import com.pm.background.admin.sys.sysuserrole.service.SysUserRoleService;
import com.pm.background.common.constant.Constant;
import com.pm.background.common.enumeration.UserRoleEnum;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.util.pay.WeixinUtil;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 管理员表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-07 18:03:20
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private SysUserRoleService userRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public IPage<User> selectPageList(User user) {
        return baseMapper.selectPageList(new Page(user.getCurrentPage(),user.getPageSize()),user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveUser(User user) {
        String account = user.getAccount();
        Long id = user.getId();
        if(user.getId()!=null){
            if(user.getId()== Constant.SUPER_ADMIN&& ShiroUtils.getUserId().longValue()!=Constant.SUPER_ADMIN){
                return R.fail("不能修改超级管理员信息");
            }
            User oldUser = this.getById(user.getId());
            Assert.notNull(oldUser,"找不到该用户");
           if (account!=null){
               if (account.length()>20){
                   return R.fail("用户名最长20个字符");
               }
           }
            if (user.getPassword()!=null){
                if (user.getPassword().length()>20){
                    return R.fail("密码最长20个字符");
                }
            }
            if(!oldUser.getAccount().equals(account)){
                Integer count = baseMapper.selectCount(new QueryWrapper<User>().lambda().eq(User::getAccount, account));
                if(count!=0){
                    return R.fail("该用户已存在");
                }
            }
            if(this.updateById(user)){
                userRoleService.remove(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId,user.getId()));
                if(user.getRoleIds()==null||user.getRoleIds().size()<=0){
                    return R.ok();
                } else {
                    user.getRoleIds().stream().forEach(item-> userRoleService.save(new SysUserRoleEntity(id,item)));
                    return R.ok();
                }
            }
                return R.fail("未知原因，保存失败");
        }else {
            Integer count = baseMapper.selectCount(new QueryWrapper<User>().lambda().eq(User::getAccount, account));
            if(count!=0){
                return R.fail("该用户已存在");
            }
            if (account!=null){
                if (account.length()>20){
                    return R.fail("用户名最长20个字符");
                }
            }
            if (user.getPassword()!=null){
                if (user.getPassword().length()>20){
                    return R.fail("密码最长20个字符");
                }
            }
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            if (user.getPassword()!=null){
                user.setPassword(ShiroUtils.sha256(user.getPassword(), salt));
            }else{
                user.setPassword(ShiroUtils.sha256("123456", salt));
            }
            user.setUserType("1");
            if(this.save(user)){
                if(user.getRoleIds()==null||user.getRoleIds().size()==0){
                    return R.ok();
                }
                else {
                    user.getRoleIds().stream().forEach(item-> userRoleService.save(new SysUserRoleEntity(user.getId(),item)));
                    return R.ok();
                }
            }
                return R.fail("未知原因，保存失败");
        }
    }




    @Override
    public R resetPassword(Map<String, Long> map) throws InvocationTargetException, IllegalAccessException {
        Long userId = map.get("userId");
        if(userId == Constant.SUPER_ADMIN) {
            return R.fail("管理员密码不能重置");
        }
        User user = new User();
        user.setId(userId);
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256("123456", salt));
        user.setId(userId);
        this.updateById(user);
        return R.ok("操作成功");
    }

    @Override
    public R changePassword(ChangePassowdForm changePassowdForm) {
        if(!changePassowdForm.getNewPwd().equals(changePassowdForm.getPassword_confirm())){
            return R.fail("两次密码不一致");
        }
        User user = this.getById(ShiroUtils.getUserId());
        Assert.notNull(user,"找不到该用户");
        String old = ShiroUtils.sha256(changePassowdForm.getOldPwd(),user.getSalt());
        if(user.getPassword().equals(old)){
            String salt = RandomStringUtils.randomAlphanumeric(20);
            user.setSalt(salt);
            user.setPassword(ShiroUtils.sha256(changePassowdForm.getNewPwd(), salt));
            user.setAvatarRealUrl(changePassowdForm.getAvatarRealUrl());
            user.setAvatarUrl(changePassowdForm.getAvatarUrl());
            this.updateById(user);
            return R.ok("修改成功");
        }
        else {
            return R.fail("密码有误");
        }
    }
    @Override
    public List<User> getUserByRoleId(Long roleId){
        List<Long> collect = userRoleService.list(new QueryWrapper<SysUserRoleEntity>().lambda().
            eq(SysUserRoleEntity::getRoleId, roleId)).stream().map(item -> item.getUserId()).collect(Collectors.toList());
        List<User> list = list(new QueryWrapper<User>().lambda().in(User::getId, collect));
        return list;
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        List<Long> meunIds=new ArrayList<>();
        List<Long> collect = userRoleService.list(new QueryWrapper<SysUserRoleEntity>().lambda().
                eq(SysUserRoleEntity::getUserId, userId)).stream().map(item -> item.getRoleId()).collect(Collectors.toList());
        if (collect.size()>0){
            meunIds = sysRoleMenuService.list(new QueryWrapper<SysRoleMenuEntity>().lambda().in(SysRoleMenuEntity::getRoleId, collect)).stream().map(item -> item.getMenuId()).collect(Collectors.toList());
        }
        return meunIds;
    }
    /**
     * @author Larry
     * @date 2020/5/15 0015 9:00
     * @return com.pm.background.common.utils.R
     * @description  微信小程序登录
     **/
    @Override
    public R wxLogin(String code) throws RuntimeException, IOException, URISyntaxException {
        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code为空");
        }
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        //拼接参数
        String param = "?grant_type=" + "authorization_code" + "&appid=" + WeixinUtil.APPID + "&secret=" + WeixinUtil.AppSecret + "&js_code=" + code;
        //创建请求对象\
        client = HttpClients.createDefault();
        String url = "https://api.weixin.qq.com/sns/jscode2session" + param;
        URIBuilder uriBuilder = new URIBuilder(url);
        //调用获取access_token接口
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
        /**
         * 设置返回编码
         */
        httpGet.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
        /**
         * 请求服务
         */
        response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity,"UTF-8");
        try {
            jsonObject = JSONObject.parseObject(result);
            System.out.println(jsonObject);
            if (jsonObject != null) {
                Object errcode = jsonObject.get("errcode");
                if (errcode != null) {
                    //返回异常信息
                    throw new RuntimeException("获取openid失败");
                }
                String openid=jsonObject.getString("openid");
                QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
                userQueryWrapper.eq("openid",openid);
                User one =getOne(userQueryWrapper);
                //如果不存在的话 新建 有的话直接登陆
                if (one==null){
                    one = new User();
                    one.setMoneyElement(Constant.ZERO);
                    String salt = RandomStringUtils.randomAlphanumeric(20);
                    one.setSalt(salt);
                    one.setOpenId(openid);
                    one.setAccount(openid);
                    one.setPassword(ShiroUtils.sha256(openid, salt));
                    one.setUserType("2");
                    one.setName(URLEncoder.encode("", "utf-8"));//将微信昵称用utf-8编码后储存);
                    save(one);
                    SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                    sysUserRoleEntity.setUserId(one.getId());
                    sysUserRoleEntity.setRoleId(Long.parseLong(UserRoleEnum.LITTLEAPPUSER.getValue()));
                    sysUserRoleService.save(sysUserRoleEntity);
                }
                return R.ok(jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
                response.close();
                client.close();
        }
        return R.fail("登录失败");
    }



    @Override
    public R changeStatus(User user){

        if(user.getId() == Constant.SUPER_ADMIN){
            return R.fail("不能修改超级管理员的状态");
        }
        this.updateById(user);
        return R.ok("状态修改成功");
    }

}
