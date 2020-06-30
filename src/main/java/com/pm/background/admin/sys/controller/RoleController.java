package com.pm.background.admin.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.pm.background.admin.sys.entity.Role;
import com.pm.background.admin.sys.entity.swagger.SysRoleSwaggerListEntity;
import com.pm.background.admin.sys.service.IRoleService;
import com.pm.background.admin.sys.sysuserrole.entity.SysUserRoleEntity;
import com.pm.background.admin.sys.sysuserrole.service.SysUserRoleService;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 角色
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:13:03
 */
@Api(value="RoleController",tags={"角色接口"})
@RestController
@RequestMapping("/sys/role")
public class RoleController  {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private         SysUserRoleService sysUserRoleService;

    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/page/list",method = RequestMethod.POST)
    public Object pageList(@RequestBody Role role)  {

        return  R.ok(roleService.selectPageList(role));
    }

    /**
     * 描述:   查询list集合方法
     * author: Larry
     * date: 2020-05-27
     */
    @ApiOperation(value="集合查询")
    @RequestMapping(value="/list",method = RequestMethod.POST)
    @ApiOperationSupport(
            responses = @DynamicResponseParameters(properties = {
                    @DynamicParameter(value = "查询结果",name = "responseListEnity",dataTypeClass = SysRoleSwaggerListEntity.class)
            })
    )
    public R list(@ApiParam(name ="sysRole",required = true,value = "根据传入的实体类查询") @RequestBody Role sysRole){
        QueryWrapper<Role> queryWrapper = new QueryWrapper(sysRole);
        queryWrapper.ne("id",4);
        queryWrapper.orderByDesc("id");
        List list = roleService.list(queryWrapper);
        return R.ok(list);
    }


    @ApiOperation("保存角色")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Object save(@Valid @RequestBody Role role){
        List<Long> menuIds = role.getMenuIds();
        String roleName = role.getRoleName();

        if (role.getId()==null&&role.getRoleName()!=null){
            roleName = role.getRoleName().trim();
            role.setOpenStatus(1);
            if (menuIds==null){
                return R.fail("保存失败,角色必须拥有至少一个权限");
            }

            if (StringUtils.isBlank(roleName)){
                return R.fail("角色名称不能为空");
            }
            if (roleName.length()>14){
                return R.fail("角色名称最长14个字");
            }
            int count = roleService.count(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, roleName));
            if (count!=0){
                return R.fail("角色名称重复");
            }
        }else{
            if (roleName!=null){
                if (roleName.trim().length()==0){
                    return R.fail("角色名称不能为空");
                }
                if (roleName.length()>14){
                    return R.fail("角色名称最长14个字");
                }
            }
            Role byId = roleService.getById(role.getId());
            if (!byId.getRoleName().equals(roleName)){
                int count = roleService.count(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, roleName));
                if (count!=0){
                    return R.fail("角色名称重复");
                }
            }
        }
        roleService.saveOrUpdate(role);
        if (menuIds!=null){
            roleService.saveMuenPerms(role);
        }
        return R.ok("成功");
    }
    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Object delete(@RequestBody Map<String,Long> map){
        Long roleId = map.get("id");
        int count = sysUserRoleService.count(new QueryWrapper<SysUserRoleEntity>().lambda().eq(SysUserRoleEntity::getRoleId, roleId));
        if (count>0){
            return R.fail("请先清空该角色下所有用户");
        }
        boolean b = roleService.removeById(roleId);
        if(b){
            return R.ok("删除成功");
        }else{
            return R.fail("删除失败");
        }
    }

    @ApiOperation("角色详情")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public Object info(@RequestBody Map<String,Long> map ){
        Long roleId = map.get("roleId");
        roleService.getById(roleId);
        return R.ok();
    }

    @ApiOperation(value = "获取角色的菜单权限")
    @RequestMapping(value = "/menu/list",method = RequestMethod.POST)
    public Object roleMenuList(@RequestBody Map<String,Long> map )  {
        Long roleId = map.get("roleId");
        return  R.ok(roleService.getCheckMenuIds(roleId));
    }

    @ApiOperation("配置菜单权限")
    @RequestMapping(value = "/save/menu/perm",method = RequestMethod.POST)
    public Object saveMuenPerms(@RequestBody Role role){

        return roleService.saveMuenPerms(role);
    }
}