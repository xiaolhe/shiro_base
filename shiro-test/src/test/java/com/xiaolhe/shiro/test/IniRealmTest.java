package com.xiaolhe.shiro.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 *     iniRealm验证
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-03  16:46
 */
@Slf4j
public class IniRealmTest {

    /**
     * 1.创建Security Manager
     * 2.主体提交认证
     * 3.Security Manager认证
     * 4.Authenticactor认证
     * 5.realm验证
     */


   @Test
    public void AuthenticactionTest(){


       IniRealm iniRealm = new IniRealm("classpath:user.ini");
       //1.构建SecurityManager环境
       DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
       defaultSecurityManager.setRealm(iniRealm);

       //2.主体提交
       SecurityUtils.setSecurityManager(defaultSecurityManager);
       Subject subject = SecurityUtils.getSubject();

       //3.SecurityManager认证
       //账号验证不通过，报异常：org.apache.shiro.authc.UnknownAccountException
       //密码验证不通过，报异常：org.apache.shiro.authc.IncorrectCredentialsException
       UsernamePasswordToken token = new UsernamePasswordToken("xiaolhe","root");
       subject.login(token);


       /*

       [users]  //设置用户
        xiaolhe=root,admin //用户名=密码，角色
        [roles]      //角色有哪些权限
        admin=user:delete // 管理员权限=user:delete删除权限 ,user:upadte 更新权限
        */
       //验证用户有哪些权限 admin 超级管理员权限
       subject.checkRole("admin");
       //验证用户是否有 delete权限
       subject.checkPermission("user:delete");
       System.out.println("Authenticted验证是否通过："+subject.isAuthenticated());

   }

}
