package com.xiaolhe.shiro.web.common;

/**
 * <p>
 *     错误枚举类基类
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-08-31  16:47
 */
public class ErrorType {

    // 成员变量
    String code;
    String name;
    String msg;
    String addMsg;

    public ErrorType(){};

    // 构造方法
    public ErrorType(String code, String name, String msg) {
        this.name = name;
        this.code = code;
        this.msg  = msg ;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg+((addMsg==null||addMsg.trim()=="") ? "" : ("：" + addMsg));
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAddMsg() {
        return addMsg;
    }

    public void setAddMsg(String addMsg) {
        this.addMsg = addMsg;
    }

    @Override
    public String toString() {
        return "{" +
                "  code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", msg='" + msg  + '\'' + ((addMsg==null||addMsg.trim()=="") ? "" : ("：" + addMsg)) +
                '}';
    }
}
