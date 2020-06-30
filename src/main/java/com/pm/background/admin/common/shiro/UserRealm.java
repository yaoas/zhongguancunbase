/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pm.background.admin.common.shiro;

import com.pm.background.admin.common.factory.IShiro;
import com.pm.background.admin.common.factory.impl.ShiroFactroy;
import com.pm.background.admin.sys.entity.User;
import com.pm.background.common.utils.ToolUtil;
import com.pm.background.common.utils.util.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 认证
 *
 */
@Component
public class UserRealm extends AuthorizingRealm {
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		IShiro shiroFactory = ShiroFactroy.me();
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();

		List<String> roleNameList = shiroUser.getRoleNames();

		List<String> permsList = shiroFactory.findPermissionsByUserId(shiroUser.getId());
		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String perms : permsList){
			if(ToolUtil.isEmpty(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(StringUtils.split(perms.trim(),",")));
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		info.addRoles(roleNameList);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		IShiro shiroFactory = ShiroFactroy.me();
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		User user = shiroFactory.user(token.getUsername());

		ShiroUser shiroUser = shiroFactory.shiroUser(user);

		SimpleAuthenticationInfo info = shiroFactory.info(shiroUser,user,getName());
		return info;
	}

	@Override
	public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
		HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
		shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
		shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
		super.setCredentialsMatcher(shaCredentialsMatcher);
	}
}
