package com.pm.background.admin.common.factory.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pm.background.admin.common.factory.IConstantFactory;
import com.pm.background.admin.sys.dao.DeptMapper;
import com.pm.background.admin.sys.dao.DictMapper;
import com.pm.background.admin.sys.dao.RoleMapper;
import com.pm.background.admin.sys.dao.UserMapper;
import com.pm.background.admin.sys.entity.Dept;
import com.pm.background.admin.sys.entity.Dict;
import com.pm.background.admin.sys.entity.Role;
import com.pm.background.common.utils.SpringContextHolder;
import com.pm.background.common.utils.ToolUtil;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 常量的生产工厂
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:55:21
 */
@Component
@DependsOn("springContextHolder")
public class ConstantFactory implements IConstantFactory {

    private RoleMapper roleMapper = SpringContextHolder.getBean(RoleMapper.class);
    private DeptMapper deptMapper = SpringContextHolder.getBean(DeptMapper.class);
    private DictMapper dictMapper = SpringContextHolder.getBean(DictMapper.class);
    private UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);

    public static IConstantFactory me() {
        return SpringContextHolder.getBean("constantFactory");
    }


    @Override
    public List<Long> getRoleIdsById(Long userId) {
        return userMapper.getRoleIdsById(userId);
    }


    /**
     * 通过角色id获取角色名称
     */
    @Override
//    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    public String getSingleRoleName(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getRoleName())) {
            return roleObj.getRoleName();
        }
        return "";
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Override
//    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    public String getSingleRoleTip(Long roleId) {
        if (0 == roleId) {
            return "--";
        }
        Role roleObj = roleMapper.selectById(roleId);
        if (ToolUtil.isNotEmpty(roleObj) && ToolUtil.isNotEmpty(roleObj.getRoleName())) {
            return roleObj.getRemark();
        }
        return "";
    }

    /**
     * 获取部门名称
     */
    @Override
//    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    public String getDeptName(Long deptId) {
        Dept dept = deptMapper.selectById(deptId);
        if (ToolUtil.isNotEmpty(dept) && ToolUtil.isNotEmpty(dept.getName())) {
            return dept.getName();
        }
        return "";
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    @Override
    public String getDictsByName(String name, Integer val) {
        QueryWrapper<Dict> temp = new QueryWrapper<>();
        temp.lambda().eq(Dict::getName,name);
        Dict dict = dictMapper.selectOne(temp);
        if (dict == null) {
            return "";
        } else {
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(Dict::getPid, dict.getId());
            List<Dict> dicts = dictMapper.selectList(wrapper);
            for (Dict item : dicts) {
                if (item.getNum() != null && item.getNum().equals(val)) {
                    return item.getName();
                }
            }
            return "";
        }
    }



}
