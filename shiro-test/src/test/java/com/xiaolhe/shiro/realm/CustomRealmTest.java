package com.xiaolhe.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * <p>
 *     自定义CustomRealm
 * </p>
 * @author: 陆袆 -v-
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  10:24
 */
public class CustomRealmTest {
    @Test
    public void AuthenticactionTest(){


        CustomRealm customRealm = new CustomRealm();
        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        //2.主体提交
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        //3.SecurityManager认证
        //账号验证不通过，报异常：org.apache.shiro.authc.UnknownAccountException
        //密码验证不通过，报异常：org.apache.shiro.authc.IncorrectCredentialsException  did not match the expected credentials[与预期的凭证不匹配]
        UsernamePasswordToken token = new UsernamePasswordToken("xiaolhe","123");
        subject.login(token);

        subject.checkRole("user");
        subject.checkPermissions("user:add","user:delete");
        System.out.println("Authenticted验证是否通过："+subject.isAuthenticated());

    }

}
