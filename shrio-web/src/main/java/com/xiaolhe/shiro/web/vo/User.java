package com.xiaolhe.shiro.web.vo;

import lombok.Data;

/**
 * <p>
 *     VO类
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  15:11
 */
@Data
public class User {

    private String username;
    private String password;
    private boolean rememberMe;//记住我

}
