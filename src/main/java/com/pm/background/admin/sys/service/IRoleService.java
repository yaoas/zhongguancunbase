package com.pm.background.admin.sys.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pm.background.admin.sys.entity.Role;
import com.pm.background.common.utils.R;
import java.util.List;

/**
 * 角色
 *
 * @author hs
 * @email 457030599@qq.com
 * @date 2018-10-10 21:13:03
 */
public interface IRoleService extends IService<Role> {

    IPage<Role> selectPageList(Role role);

    R deleteRole(Long roleIds[]);

    List<Long> getCheckMenuIds(Long roleId);

    R saveMuenPerms(Role role);


}
