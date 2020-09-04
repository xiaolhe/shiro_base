package com.xiaolhe.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *     自定义realm
 *     已经加密加盐
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  10:12
 */
public class CustomRealmPwdSalt extends AuthorizingRealm {

    //模拟数据
    Map<String ,String > map = new HashMap<>(16);
    {
//        map.put("xiaolhe","123");
//        map.put("xiaolhe","68053af2923e00204c3ca7c6a3150cf7"); //MD5加密
        map.put("xiaolhe","4297f44b13955235245b2497399d7a93"); //MD5加密+加盐
        super.setName("CustomRealmPwdSalt");
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        //1.角色role：从主体传过来的认证信息中，获取用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //1.权限permission：从主体传过来的认证信息中，获取用户名
        Set<String> permissions = getPremisssionByUserName(userName);
        //2.通过用户名，从数据库中获取凭证
        Set<String> roles = getRolesByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * <p>
     *     通过用户名获取权限
     *     模拟数据库或缓存从获取凭证
     * </p>
     * @param userName
     * @return
     */
    private Set<String> getPremisssionByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    /**
     * <p>
     *     通过用户名获取角色
     *     模拟数据库或缓存中获取凭证
     * </p>
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        //设置管理员角色
        sets.add("admin");
        //设置普通用户角色
        sets.add("user");
        return sets;

    }


    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //1.从主体传过来的认证信息中，获取用户名
        String userName = (String) authenticationToken.getPrincipal();

        //2.通过用户名，从数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if (password ==null){
            //如果密码不存在，返回null
            return null;
        }
        //如果密码存在，创建SimpleAuthenticationInfo 对象，返回simpleAuthenticationInfo 信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("xiaolhe",password,"CustomRealmPwdSalt");
        //加盐后，
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("123"));
        return simpleAuthenticationInfo;
    }

    /**
     * <p>
     *     模拟从数据库或缓存中获取凭证
     * </p>
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        //通过用户名获取凭证
        return map.get(userName);
    }

    public static void main(String[] args) {
        Md5Hash hash = new Md5Hash("123","123");
        System.out.println(hash.toString());
    }
}
