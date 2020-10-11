package com.xiaolhe.shiro.web.controller;

import com.xiaolhe.shiro.web.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *     用户登录
 * </p>
 * @author: 陆袆 >_<注解
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  15:07
 */
@Controller
@Slf4j
public class UserController {


    @RequestMapping(value = "/sublogin",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String sublogin(User user){
        //1.获取主体
        Subject subject = SecurityUtils.getSubject();
        //2.主体提交请求
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            //shiro自动登录
            token.setRememberMe(user.isRememberMe());
            subject.login(token);

        } catch (AuthenticationException e) {
            e.getMessage();
            return "登录异常";
        }
        //判断角色
        if(subject.hasRole("admin")){
            return "您现在是管理员权限";
        }

        return " 未获得权限！请联系管理员进行添加权限！";
    }


    //shiro过滤器
    @RequestMapping(value = "/testroles",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testRole(){
        return "success admin shirofilter";
    }


    //custom过滤器
    @RequestMapping(value = "/testroles1",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ResponseBody
    public String testRole1(){
        return "success admin customfilter";
    }

}
