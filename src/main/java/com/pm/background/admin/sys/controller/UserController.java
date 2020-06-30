package com.pm.background.admin.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.pm.background.admin.common.factory.impl.ConstantFactory;
import com.pm.background.admin.sys.dto.ChangePassowdForm;
import com.pm.background.admin.sys.entity.Role;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.admin.sys.service.IRoleService;
import com.pm.background.admin.sys.service.IUserService;
import com.pm.background.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import com.pm.background.admin.sys.sysuserrole.service.SysUserRoleService;
import com.pm.background.common.base.controller.BaseController;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.ToolUtil;
import com.pm.background.common.utils.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
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
@Api(value = "UserController", tags = {"用户接口"})
@RequestMapping("/sys/user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private IRoleService roleService;

    @Transactional
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/page/list", method = RequestMethod.POST)
    public R pageList(@RequestBody User user) {
        List<Long> roleIds = user.getRoleIds();
        QueryWrapper qw = new QueryWrapper<User>();
        if (roleIds != null && roleIds.size() > 0) {
            Long roleId = roleIds.get(0);
            List<SysUserRoleEntity> list = sysUserRoleService.list(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getRoleId, roleId));
            List<Long> collect = list.stream().map(item -> item.getUserId()).collect(Collectors.toList());
            if (collect.size() > 0) {
                qw.in("id", collect);
            }else{
                IPage<User> page=new Page<>();
                return   R.ok(page);
            }
        }
        if (StringUtils.isNotBlank(user.getAccount())) {
            qw.like("account", user.getAccount());
        }
        if (StringUtils.isNotBlank(user.getName())) {
            qw.like("name", URLEncoder.encode(user.getName()));
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            qw.like("phone", user.getPhone());
        }
        if (StringUtils.isNotBlank(user.getUserType())) {
            qw.like("user_type", user.getUserType());
        }
        qw.select("id", "account", "name", "email", "phone", "status", "user_type", "money_element", "last_login_time", "create_time");
        qw.notIn("id", "1");
        qw.orderByDesc("create_time");
        IPage<User> page = userService.page(new Page(user.getCurrentPage(), user.getPageSize()), qw);
        if (page.getRecords().size()==0&&page.getTotal()!=0){
            page = userService.page(new Page(user.getCurrentPage()-1, user.getPageSize()), qw);
        }
        page.getRecords().stream().forEach(item -> {
            List<Long> collect = sysUserRoleService.
                    list(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId, item.getId())).
                    stream().map(a -> a.getRoleId()).collect(Collectors.toList());
            if (collect.size() != 0) {
                List<Role> list = roleService.list(new QueryWrapper<Role>().lambda().in(Role::getId, collect).select(Role::getRoleName, Role::getId));
                item.setRoleNameString(list.stream().map(b -> b.getRoleName()).collect(Collectors.joining(",")));
                item.setRoleIds(list.stream().map(b -> b.getId()).collect(Collectors.toList()));
            }
        });
        return R.ok(page);
    }

    @ApiOperation("改变状态,是否可用")
    @ApiOperationSupport(params = @DynamicParameters(name = "map", properties = {
            @DynamicParameter(name = "userId", value = "用户id", example = "12", required = true, dataTypeClass = Long.class),
            @DynamicParameter(name = "status", value = "状态 1为启用0为禁用", example = "1", required = true, dataTypeClass = Integer.class),
    }))
    @RequestMapping(value = "/change/status", method = RequestMethod.POST)
    public R changeStatus(@RequestBody User user) throws InvocationTargetException, IllegalAccessException {
        return userService.changeStatus(user);
    }

    @ApiOperation("保存用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@Valid @RequestBody User user) throws UnsupportedEncodingException {
       if (StringUtils.isNotBlank(user.getName())){
           user.setName(URLEncoder.encode(user.getName(), "utf-8"));//将微信昵称用utf-8编码后储存);
       }
        user.setStatus(1);
        R r = userService.saveUser(user);
        return r;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation("删除用户")
    @ApiOperationSupport(params = @DynamicParameters(name = "queryMap", properties = {
            @DynamicParameter(name = "userId", value = "要删除的用户id", example = "112", required = true, dataTypeClass = Long.class)
    }))
    public R delete(@RequestBody Map<String, Long> queryMap) {
        Long userId = queryMap.get("userId");
        boolean result = userService.removeById(userId);
        sysUserRoleService.remove(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getUserId, userId));
        if (result) {
            return R.ok("删除成功");
        } else {
            return R.fail("删除失败");
        }
    }

    @ApiOperation("用户详情")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public R info(@RequestBody Map<String, Long> map) {
        Long userId = map.get("userId");
        User user = userService.getById(userId);
        if (ToolUtil.isEmpty(user)) {
            return R.fail("找不到该用户");
        }
        List<Long> roleIds = ConstantFactory.me().getRoleIdsById(userId);
        user.setRoleIds(roleIds);
        if (roleIds.size() > 0) {
            user.setRoleNameString(roleService.list(new QueryWrapper<Role>().lambda().in(Role::getId, roleIds)).stream().map(item -> item.getRoleName()).collect(Collectors.joining(",")));
        } else {
            user.setRoleNameString("");
        }
        return R.ok(user);
    }

    @RequestMapping(value = "/reset/password", method = RequestMethod.POST)
    @ApiOperation("重置用户密码")
    @ApiOperationSupport(params = @DynamicParameters(name = "mapx", properties = {
            @DynamicParameter(name = "userId", value = "用户id", example = "12", required = true, dataTypeClass = Long.class)
    }))
    public R resetPassword(@RequestBody Map<String, Long> mapx) throws InvocationTargetException, IllegalAccessException {

        return userService.resetPassword(mapx);
    }

    /**
     * @return java.lang.Object
     * @author Larry
     * @date 2020/5/22 0022 9:50
     * @description 新增了关于非空的判断
     **/
    @ApiOperation("修改密码")
    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    public Object changePassword(@Valid @RequestBody ChangePassowdForm changePassowdForm) {
        return userService.changePassword(changePassowdForm);
    }

    @ApiOperation("查询角色下的用户")
    @ApiOperationSupport(params = @DynamicParameters(name = "map1", properties = {
            @DynamicParameter(name = "roleId", value = "角色id", example = "12", required = true, dataTypeClass = Long.class)
    }))
    @RequestMapping(value = "/getUserByRole", method = RequestMethod.POST)
    public R getUserByRole(@RequestBody Map<String, Long> map1) {
        Long id = map1.get("roleId");
        List<User> users = userService.getUserByRoleId(id);
        return R.ok(users);
    }






    /**
     * @return com.pm.background.common.utils.R
     * @author Larry
     * @date 2020/5/14 0014 9:52
     * @description 图片上传方法
     **/
    @ApiOperation("上传头像图片接口")
    @ApiOperationSupport(
            responses = @DynamicResponseParameters(properties = {
                    @DynamicParameter(value = "图片本地存储路径", name = "oldPath"),
                    @DynamicParameter(value = "图片访问路径", name = "newPath")
            })
    )
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public R upload(MultipartFile file) {
        return savePic(file);
    }

}