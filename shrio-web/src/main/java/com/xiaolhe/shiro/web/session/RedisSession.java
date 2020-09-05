package com.xiaolhe.shiro.web.session;

import com.xiaolhe.shiro.web.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *     shiro会话管理
 *     1.继承 AbstractSessionDAO
 *
 * </p>
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-05  11:31
 */
public class RedisSession extends AbstractSessionDAO {

    @Resource
    private JedisUtil jedisUtil;

    //定义一个session前缀，session组成是由session前缀和sessionId
    private final String SHIRO_SESSION_PREFIX="xiaolhe-session:";


    //定义一个key，以二进制的方式存储
    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }


    //提取session的保存方法
    private void saveSession(Session session) {
        //1.如果传进来的session sessionId 不为null
        if (session !=null && session.getId() !=null ) {
            //2.获取key
            byte[] key = getKey(session.getId().toString());
            //3.定义一个value,保存一个session对象
            byte[] value = SerializationUtils.serialize(session);
            //4.保存到缓存中
            jedisUtil.set(key, value);
            //5.设置超时时间,10分钟=600s
            jedisUtil.expire(key, 600);
        }
    }


        /**
         * 创建Session
         * @param session
         * @return
         */
    @Override
    protected Serializable doCreate(Session session) {
        //1. 创建sessionID
        Serializable sessionId = generateSessionId(session);
        //2.将session和sessionId进行捆绑
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }


    /**
     * 读取session
     * @param sessionId
     * @return
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {

        System.out.println("start read Session......");
        //如果sessionId为null
        if (sessionId== null){
            return null;
        }

        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        //反序列化数组
        return (Session) SerializationUtils.deserialize(value);
    }


    /**
     * 更新session
     * @param session
     * @throws UnknownSessionException
     */
    @Override
    public void update(Session session) throws UnknownSessionException {
           saveSession(session);
    }

    /**
     * 删除session
     * @param session
     */
    @Override
    public void delete(Session session) {
        //如果session 和 sessionID 不为nll
        if ( session != null || session.getId() !=null){
            //啥也不干，仅返回
            return;
        }
        //如果不为null，则进行删除
        byte[] key = getKey(session.getId().toString());
        jedisUtil.delete(key);
    }


    /**
     * 获得指定的激活的session
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {

        //1.通过session前缀 先获取到 redis 中所有的 key 值
        Set<byte[]> keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);

        //2.创建一个session集合
        Set<Session> sessions = new HashSet<>();
        //如果这个集合为空集合，直接返回该集合
        if (CollectionUtils.isEmpty(keys)) {
            return sessions;
        }

        //如果keys集合不为空，则遍历
        for (byte[] key : keys) {
            //反序列化后
            Session session = (Session) SerializationUtils.deserialize(jedisUtil.get(key));
            //存储到集合中
            sessions.add(session);
        }

        return sessions;
    }
}
