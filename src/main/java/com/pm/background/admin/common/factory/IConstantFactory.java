package com.pm.background.admin.common.factory;
import java.util.List;

/**
 * 常量生产工厂的接口
 *
 * @author fengshuonan
 * @date 2017-06-14 21:12
 */
public interface IConstantFactory {


    /**
     * @method
     * @description 根据用户id获取角色id
     * @date: 2018/10/10 22:06
     * @author: hs
     * @param  * @param null
     * @return
     */
    List<Long> getRoleIdsById(Long userId);


    /**
     * 通过角色id获取角色名称
     */
    String getSingleRoleName(Long roleId);

    /**
     * 通过角色id获取角色英文名称
     */
    String getSingleRoleTip(Long roleId);


    /**
     * 获取部门名称
     */
    String getDeptName(Long deptId);




    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    String getDictsByName(String name, Integer val);




}
