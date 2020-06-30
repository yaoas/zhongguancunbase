package com.pm.background.admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pm.background.admin.sys.dao.RoleMapper;
import com.pm.background.admin.sys.entity.Menu;
import com.pm.background.admin.sys.entity.Role;
import com.pm.background.admin.sys.service.IMenuService;
import com.pm.background.admin.sys.service.IRoleService;
import com.pm.background.admin.sys.sysrolemenu.entity.SysRoleMenuEntity;
import com.pm.background.admin.sys.sysrolemenu.service.SysRoleMenuService;
import com.pm.background.common.utils.R;
import com.pm.background.common.utils.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:13:03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * @author Larry
     * @date 2020/5/26 0026 17:44
     * @param role
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.pm.background.admin.sys.entity.Role>
     * @description 查询时同时对应查询角色对应菜单信息
     **/
    @Override
    public IPage<Role> selectPageList(Role role) {
        Page<Role> page = this.baseMapper.selectPage(new Page(role.getCurrentPage(), role.getPageSize()), new QueryWrapper<Role>().ne("id",4));
        page.getRecords().stream().forEach(item->{
            Long id = item.getId();
            List<SysRoleMenuEntity> list = sysRoleMenuService.list(new QueryWrapper<SysRoleMenuEntity>().lambda().
                eq(SysRoleMenuEntity::getRoleId, id).select(SysRoleMenuEntity::getMenuId));
            //查询所有角色信息
            List<Long> collect = list.stream().map(item1 -> item1.getMenuId()).collect(Collectors.toList());
            if (collect.size()>0){
                List<Menu> list1 = menuService.list(new QueryWrapper<Menu>().lambda().in(Menu::getId, collect));
                item.setMenuNames(list1.stream().map(a->a.getName()).collect(Collectors.joining(",")));
                item.setMenuIds(list1.stream().filter(b->b.getParentId()!=0).map(a->a.getId()).collect(Collectors.toList()));
            }
        });

        return page;
    }

    @Override
    public R deleteRole(Long[] roleIds) {
        if(ToolUtil.isEmpty(roleIds)||roleIds.length<=0){
            return R.fail("未选择删除的角色");
        }
        for(Long roleId:roleIds){
            Integer count = baseMapper.getCountByRoleId(roleId);
            if(count>0){
                return R.fail("当前删除的角色，还有用户关联，请先取消其关联");
            }
        }

        this.removeByIds(Arrays.asList(roleIds));
        return R.ok();
    }

    @Override
    public List<Long> getCheckMenuIds(Long roleId) {
        List<Long> checkMenuIds = baseMapper.getCheckMenuIds(roleId);
        Menu menu = new Menu();
        List<Long> checkMenuIdsChilder = new ArrayList<>();
        for(Long menuId :checkMenuIds){
           menu.setParentId(menuId);
           int count = baseMapper.selectIfHaveChildren(menu);
           if(count == 0){
               checkMenuIdsChilder.add(menuId);
           }
       }
        return checkMenuIdsChilder;
    }

    @Override
    @Transactional
    public R saveMuenPerms(Role role) {
        Role r = this.getById(role.getId());
         if(ToolUtil.isEmpty(r)){
            return R.fail("找不到该角色");
        }
        baseMapper.delMenuPermByRoleId(role.getId());
        if(role.getMenuIds().size()<=0){
            return R.ok();
        }
        //去重
        //List<Long> collect = role.getMenuIds().stream().distinct().collect(Collectors.toList());
        //全部都是子集 去寻找父集
        List<Long> collect = menuService.list(new QueryWrapper<Menu>().lambda().in(Menu::getId, role.getMenuIds())).stream().map(Menu::getParentId).distinct().collect(Collectors.toList());
        role.getMenuIds().addAll(collect);
        LinkedList<SysRoleMenuEntity> sysRoleMenuEntities = new LinkedList<>();
        for (Long menuId : role.getMenuIds()) {
            SysRoleMenuEntity sysRoleMenuEntity = new SysRoleMenuEntity();
            sysRoleMenuEntity.setRoleId(role.getId());
            sysRoleMenuEntity.setMenuId(menuId);
            sysRoleMenuEntities.add(sysRoleMenuEntity);
        }
        sysRoleMenuService.saveBatch(sysRoleMenuEntities);
        return R.ok("保存成功");
    }

}
