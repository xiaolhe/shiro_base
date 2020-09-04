package com.xiaolhe.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * <p>
 *     jdbcRealm验证
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-03  16:46
 */
@Slf4j
public class JdbcRealmTest {

    /**
     * 1.创建Security Manager
     * 2.主体提交认证
     * 3.Security Manager认证
     * 4.Authenticactor认证
     * 5.realm验证
     */


    //创建数据源
   DruidDataSource druidDataSource = new DruidDataSource();
   {
      druidDataSource.setUrl(" jdbc:mysql://127.0.0.1:3306/shiro_db?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&serverTimezone=Asia/Shanghai");
      druidDataSource.setUsername("root");
      druidDataSource.setPassword("root");
   }


   @Test
    public void AuthenticactionTest(){

      JdbcRealm jdbcRealm = new JdbcRealm();
      //设置数据源
      jdbcRealm.setDataSource(druidDataSource);
      //开启权限 默认是关闭的
       jdbcRealm.setPermissionsLookupEnabled(true);

       //用户密码验证
       String Authentic = "select password from users where username = ? ";
       //设置自定义的SQL查询
       jdbcRealm.setAuthenticationQuery(Authentic);

       //角色验证 admin  xiaolhe
       String Roles = "select role_name from user_role where username = ?";
       jdbcRealm.setUserRolesQuery(Roles);


      //1.构建SecurityManager环境
       DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
       defaultSecurityManager.setRealm(jdbcRealm);

       //2.主体提交
       SecurityUtils.setSecurityManager(defaultSecurityManager);
       Subject subject = SecurityUtils.getSubject();

       //3.SecurityManager认证
       //账号验证不通过，报异常：org.apache.shiro.authc.UnknownAccountException
       //密码验证不通过，报异常：org.apache.shiro.authc.IncorrectCredentialsException
       UsernamePasswordToken token = new UsernamePasswordToken("xiaolhe","123456");
       subject.login(token);

//       subject.checkRole("admin");

       //角色验证
       subject.checkRole("admin");
       System.out.println("isAuthenticated:  "+subject.isAuthenticated());

   }

}
