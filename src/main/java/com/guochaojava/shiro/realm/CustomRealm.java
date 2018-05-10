package com.guochaojava.shiro.realm;

import com.guochaojava.model.User;
import com.guochaojava.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 自定义realm
 *
 * @author GuoChao.
 * @since 0.0.1
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return SimpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1.认证信息中获取登录名
        String email = (String) principalCollection.getPrimaryPrincipal();
        //2.从数据库中获取权限数据
        Set<String> roles = userService.selectRolesByEmail(email);
        Set<String> permissions = userService.selectPermissionsByEmail(email);

        //3.返回授权信息对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken 主体传过来的认证信息
     * @return SimpleAuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中获取登录名
        String email = (String) authenticationToken.getPrincipal();
        //2.通过登录名到数据库中获取凭证
        User user = userService.selectUserByEmail(email);
        if (user == null) {
            return null;
        }
        //3.返回认证对象信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(), "customRealm");
       /* //加密盐
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(""));*/
        return authenticationInfo;
    }
}