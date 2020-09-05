package com.xiaolhe.shiro.web.dao;

import com.xiaolhe.shiro.web.vo.User;

import java.util.List;

/**
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  19:16
 */
public interface UserDao {
    //通过用户名查询用户
    User queryUserByUserName(String userName);

    //通过用户名，查询角色
    List<String> queryRolesByUserName(String userName);

    //通过用户名，查询权限
    List<String> queryPremisssionByUserName(String userName);
}
