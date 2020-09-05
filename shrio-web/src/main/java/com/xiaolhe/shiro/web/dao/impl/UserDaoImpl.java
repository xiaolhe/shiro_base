package com.xiaolhe.shiro.web.dao.impl;

import com.xiaolhe.shiro.web.dao.UserDao;
import com.xiaolhe.shiro.web.vo.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author: 陆袆 >_<
 * @email: amixiao@qq.com
 * @createTime: 2020-09-04  19:17
 */
@Component
public class UserDaoImpl implements UserDao {


    @Resource
    private JdbcTemplate jdbcTemplate;



    /**
     * 从数据库中获取用户名
     * @param userName
     * @return
     */
    @Override
    public User queryUserByUserName(String userName) {

        String sql = "select username,password from users where username= ?";
        List<User> list = jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                //将查询到的数据，赋值到对象中
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });

        //查询完之后，判断集合是否为空，如果为空，直接返回null
        if (CollectionUtils.isEmpty(list)){
            return null;
        }
        //直接取素组的第一条
        return list.get(0);
    }

    /**
     * 通过用户名查询角色
     * @param userName
     * @return
     */
    @Override
    public List<String> queryRolesByUserName(String userName) {
        String sql = "select role_name from user_role where username = ?";
        return jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("role_name");
            }
        });
    }


    //通过用户名，查询权限
    @Override
    public List<String> queryPremisssionByUserName(String userName) {
        String sql = "select permission from user_role where username=?";
        return jdbcTemplate.query(sql, new String[]{userName}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("permission");
            }
        });
    }
}
