# Shiro-No-01
> 🐇 ©陆袆 Email: xiaolhe_shiro@126.com
> 

> 🍎提交日期 2020年9月4日
 - shiro认证 
 - shiro授权 
 - shiro角色权限 
 - shiro三种Realm验证 CustomRealm,JdbcRealm,InitRealm

# Shiro-No-02
 >🍎 新增 Shiro会话管理
 > 提交日期 2020年9月5日
 - 自定义RedisSession
 - 自定以CustomSessionManager
 - JedisUtil工具类
 - spring-redis.xml配置文件
 - 在spring.xml中配置bean对象，并在SecurityManager引入bean对象

 >🍎 新增 Shiro缓存管理
 - 创建RedisCacheManager，实现CacheManager
 - 创建RedisCache ，实现Cache
 - 在spring.xml中配置bean对象，并在SecurityManager引入bean对象
 
  >🍎 新增 Shiro自动登录
  - 在spring.xml中配置cookie对象，并在SecurityManager引入bean对象
  - 在vo类，添加属性 
  ```java
   private boolean rememberMe;//记住我
  ```
   然后在页面login.html中添加代码
   ```html
 <input type="checkbox" onclick="document.getElementById('rememberMe').value=document.getElementById('rememberMe').value==='false';"/>记住我<br/>
```
  - 在controller层添加代码
  ```java
   token.setRememberMe(user.isRememberMe());
```
  - 
  
 