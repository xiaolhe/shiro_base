package com.xiaolhe.shiro.web.realm;

import com.xiaolhe.shiro.web.dao.UserDao;
import com.xiaolhe.shiro.web.vo.User;
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

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *     自定义realm
 *     已经加密加盐
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  10:12
 */
public class CustomRealmJDBC extends AuthorizingRealm {


    @Resource
    private UserDao userDao;

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
        List<String> list = userDao.queryPremisssionByUserName(userName);
        Set<String> sets = new HashSet<>(list);
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
        System.out.println("从数据库中获取获取授权数据...");
        List<String> list = userDao.queryRolesByUserName(userName);
        Set<String> sets = new HashSet<>(list);
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
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,password,"CustomRealmJDBC");
        //加盐后，(可以自定义：随机数或其他）
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
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
        User user = userDao.queryUserByUserName(userName);
        //判断用户如果不为空，
        if (user !=null){
            user.getPassword();
        }
        return null;
    }

    public static void main(String[] args) {
        Md5Hash hash = new Md5Hash("123","xiaolhe");
        System.out.println(hash.toString());
    }
}
