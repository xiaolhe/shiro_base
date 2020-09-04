package com.xiaolhe.shiro.web.common;


/**
 * <p>
 *     统一返回结果外层封装
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-08-31  16:41
 */
public class ResponseResult<T> {

    //状态
    private String status;
    //信息
    private String msg;
    //返回的数据
    private T data;


    public ResponseResult() {
        this.status = Constants.SUCCESS_CODE;
        this.msg = "请求成功";
    }
    public ResponseResult(String msg) {
        this.status = Constants.SUCCESS_CODE;
        this.msg = msg;
    }
    public ResponseResult(T data) {
        this();
        this.data = data;
    }


    public ResponseResult(ErrorType errorType) {
        this.status = errorType.getCode();
        this.msg = errorType.getMsg();
    }

    public ResponseResult(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }



    public ResponseResult(String msg, T data) {
        super();
        this.status = Constants.SUCCESS_CODE;
        this.msg = msg;
        this.data = data;
    }
    public ResponseResult(String status, String msg, T data) {
        super();
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     *  成功时候的调用
     * */
    public static <T> ResponseResult<T> success(T data){
        return new ResponseResult<T>(data);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
