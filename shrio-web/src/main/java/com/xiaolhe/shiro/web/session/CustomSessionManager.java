package com.xiaolhe.shiro.web.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * <p>
 *     自定义SessionManager
 *     目的是：为了防止多次读取session，影响性能
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-05  15:27
 */
public class CustomSessionManager extends DefaultWebSessionManager {


    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {

        Serializable sessionId = getSessionId(sessionKey);
        //2.第二次及多次杜取session 从request中读取sessionkey,
        ServletRequest request = null;
        //3.判断 这个sessionKey 是不是 webSessionKey
        if (sessionKey instanceof WebSessionKey) {
            //从request 中获取这个sessionKey
            request = ((WebSessionKey) sessionKey).getServletRequest();
        }

        //request 携带了sessionKey，判断是否不为null
        if (request != null && sessionId != null) {
            Session session = (Session) request.getAttribute(sessionId.toString());
            //判断session不为null 则返回session
            if (session != null) {
                return session;
            }
        }

        //如果未携带,则从redis中获取session
       Session session =  super.retrieveSession(sessionKey);
        //判断request 和 sessionID 不为null 则添加到request，并返回session
        if (request !=null && sessionId != null){
            request.setAttribute(sessionId.toString(),session);
        }
        return session;
    }
}
