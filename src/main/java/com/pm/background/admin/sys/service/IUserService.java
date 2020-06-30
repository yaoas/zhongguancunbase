package com.pm.background.admin.sys.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pm.background.admin.sys.dto.ChangePassowdForm;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.common.base.service.ICommonService;
import com.pm.background.common.utils.R;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * 管理员表
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-07 18:03:20
 */
public interface IUserService extends ICommonService<User> {

    IPage<User> selectPageList(User user);

    R saveUser(User user);




    R resetPassword(Map<String, Long> userIds) throws InvocationTargetException, IllegalAccessException;

    R changePassword(ChangePassowdForm changePassowdForm);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId);

    List<User> getUserByRoleId(Long roleId);

    R wxLogin(String code) throws URISyntaxException, IOException;

    R changeStatus(User map) throws InvocationTargetException, IllegalAccessException;


}
