package com.xiaolhe.shiro.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>
 *     shiro认证
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-03  16:46
 */
@Slf4j
public class AuthenticactionTest {

    /**
     * 1.创建Security Manager
     * 2.主体提交认证
     * 3.Security Manager认证
     * 4.Authenticactor认证
     * 5.realm验证
     */

    SimpleAccountRealm simpleAccountRealm =  new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("xiaolhe","root");
    }
   @Test
    public void AuthenticactionTest(){
       //1.构建SecurityManager环境
       DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
       defaultSecurityManager.setRealm(simpleAccountRealm);

       //2.主体提交
       SecurityUtils.setSecurityManager(defaultSecurityManager);
       Subject subject = SecurityUtils.getSubject();

       //3.SecurityManager认证
       //账号验证不通过，报异常：org.apache.shiro.authc.UnknownAccountException
       //密码验证不通过，报异常：org.apache.shiro.authc.IncorrectCredentialsException
       UsernamePasswordToken token = new UsernamePasswordToken("xiaolhe","root");
       subject.login(token);

       //是否认证成功
//       log.info("isAuthenticated:"+subject.isAuthenticated());
       System.out.println("Authenticated验证是否通过: "+subject.isAuthenticated());

   }

}
