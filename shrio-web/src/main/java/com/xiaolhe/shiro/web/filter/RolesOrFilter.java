package com.xiaolhe.shiro.web.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>
 *     AuthorizationFilter
 *     授权过滤器
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-05  10:33
 */
public class RolesOrFilter extends AuthorizationFilter {

    /**
     * 授权访问资源
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object object) throws Exception {

        //1.创建主体
        Subject subject = getSubject(request,response);
        //2.创建一个角色字符串数组
        String[]  roles  = (String[]) object;
        //3.如果角色不为null 或字符串数组的长度不为0 即可访问
        if (roles == null || roles.length == 0 ){
            return true; //不需要什么角色，就可以访问
        }

        //遍历字符串数组
        for (String role : roles) {
            //判断当前主体，有这个角色，可以访问
            if (subject.hasRole(role)){
                return true;
            }
        }
        //如果都没有，则返回false
        return false;
    }
}
