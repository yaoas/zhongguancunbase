package com.pm.background.admin.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.google.gson.Gson;
import com.pm.background.admin.common.shiro.ShiroUtils;
import com.pm.background.admin.sys.dto.LoginForm;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.admin.sys.service.IUserService;
import com.pm.background.common.entity.CommonOkResponseEntity;
import com.pm.background.common.enumeration.UserRoleEnum;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.util.AddressUtils;
import com.pm.background.common.utils.util.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import java.util.Map;

@RestController
@Api(value="LoginController",tags={"登录接口"})
public class LoginController {
    @Autowired
    private IUserService iUserService;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value="登陆方法")
    public Object login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        try {
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassword());
            subject.login(token);
            ShiroUtils.getUserEntity().setSessionId(request.getSession().getId());
            if (!ShiroUtils.getSubject().hasRole(UserRoleEnum.LITTLEAPPUSER.getValue())){
                String ip = IpUtils.getIp(request);
                String   address = AddressUtils.getAddresses("?ip=" +ip+"&key="+"VMPBZ-O5A3U-G5DVG-4KXQP-KNLM6-QGBPI", "utf-8");
                Gson gson = new Gson();
                if (!"0".equals(address)){
                    Map<String, Object>  map = gson.fromJson(address, Map.class);
                    if (map.get("status").equals(0)){
                        Map<String, Map<String, String>> result = (Map<String, Map<String, String>>) map.get("result");
                        Map<String, String> addressInfo = result.get("ad_info");
                        String province=addressInfo.get("province");
                        String city=addressInfo.get("city");
                    }
                }

            }
        } catch (UnknownAccountException e) {
            return R.fail(e.getMessage());
        } catch (IncorrectCredentialsException e) {
            return R.fail("账号或者密码不正确");
        } catch (LockedAccountException e) {
            return R.fail("账号已被锁定,请联系管理员");
        } catch (AuthenticationException e) {
            return R.fail("账户验证失败");
        } /*catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/ catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        /**
         * @author Larry
         * @date 2020/5/28 0028 9:37
         * @param [loginForm, request]
         * @return java.lang.Object
         * @description 更新最后登录时间
         **/
        User user = new User();
        user.setLastLoginTime(new Date());
        iUserService.update(user,new QueryWrapper<User>().lambda().eq(User::getAccount,loginForm.getUsername()));
        return R.ok(ShiroUtils.getUserEntity());
    }


    /**
     * 退出
     */
    @ApiOperation(value="退出登陆方法")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Object logout() {
        ShiroUtils.logout();
        return R.ok("退出成功");
    }

    /**
     * @author Larry
     * @date 2020/5/14 0014 19:58
     * @return com.pm.background.common.utils.R
     * @description 微信登陆方法  不存在用户则新建 存在则直接登陆 返回openid  同时建立连接
     **/
    @RequestMapping(value = "/wxLogin",method = RequestMethod.POST)
    @ApiOperation(value="获取openid")
    @ApiOperationSupport(params = @DynamicParameters(name = "CreateOrderModel",properties = {
            @DynamicParameter(name = "code",value = "登陆用的字符串",example = "xxx",required = true,dataTypeClass = String.class)
    }))
    public R wxLogin(@RequestBody  Map<String,String> map) throws RuntimeException, IOException, URISyntaxException {
        String code=map.get("code");
        return iUserService.wxLogin(code);
    }


    @RequestMapping(value = "/phoneSec",method = RequestMethod.POST)
    @ApiOperation(value="获取用户手机号解密")
    @ApiOperationSupport(params = @DynamicParameters(name = "CreateOrderModel",properties = {
            @DynamicParameter(name = "iv",value = " e.detail.iv",example = "dsfasd545",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "encryptedData",value = "e.detail.encryptedData",example = "dsfasd545",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "openId",value = "openId",example = "dsfasd54542116345",required = true,dataTypeClass = String.class),
            @DynamicParameter(name = "sessionKey",value = "sessionKey",example = "dsfasd54542116345",required = true,dataTypeClass = String.class),
    }))
    public R phoneSec(@RequestBody Map<String,String> map1, HttpServletRequest request) throws RuntimeException, IOException, URISyntaxException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String iv = map1.get("iv");
        String encrypted = map1.get("encryptedData");
        String sessionKey = map1.get("sessionKey");
        String openId = map1.get("openId");
            if (StringUtils.isNotBlank(iv) && StringUtils.isNotBlank(encrypted)) {
                byte[] encrypData = Base64Utils.decodeFromString(encrypted);
                byte[] ivData = Base64Utils.decodeFromString(iv);
                byte[] sessionKey1 = Base64Utils.decodeFromString(sessionKey);
                AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivData);
                //java中自带的是PKCS5Padding填充，通过添加BouncyCastle组件来支持PKCS7Padding填充。
                Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                SecretKeySpec keySpec = new SecretKeySpec(sessionKey1, "AES");
                cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
                String resultString = new String(cipher.doFinal(encrypData), "UTF-8");
                JSONObject object = JSONObject.parseObject(resultString);
                boolean flag=false;
                try {
                    Long userId = ShiroUtils.getUserId();
                }catch (Exception e){
                    flag=!flag;
                }
                // 拿到手机号码
                iUserService.update(new UpdateWrapper<User>().set("phone", object.getString("phoneNumber")).eq("openid", openId));
                if (flag){
                    //登录
                    Subject subject = ShiroUtils.getSubject();
                    UsernamePasswordToken token = new UsernamePasswordToken(openId, openId);
                    subject.login(token);
                    ShiroUtils.getUserEntity().setSessionId(request.getSession().getId());
                }
            }
            return R.ok(ShiroUtils.getUserEntity());
    }


    /**
     * @return com.pm.background.common.utils.R
     * @author Larry
     * @date 2020/5/14 0014 19:58
     * @description 用户修改昵称和手机号
     **/
    @PostMapping(value = "/changeLittleUser")
    @ApiOperation(value = "微信用户登录后传回昵称和图片的方法", response = CommonOkResponseEntity.class,tags = {"小程序"})
    public R changeLittleUser(@RequestBody User user) throws UnsupportedEncodingException {
        user.setName(URLEncoder.encode(user.getName(), "utf-8"));//将微信昵称用utf-8编码后储存);
        boolean update = iUserService.update(user,new UpdateWrapper<User>().eq("openid",user.getOpenId()));
        if (update) {
            return R.ok("修改成功");
        } else {
            return R.fail("修改失败");
        }
    }




}
