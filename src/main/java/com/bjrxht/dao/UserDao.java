package com.bjrxht.dao;

import com.bjrxht.entity.Role;
import com.bjrxht.entity.User;
import com.bjrxht.entity.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Administrator on 2016/3/29.
 */
@Repository
public class UserDao {

    protected Logger logger = Logger.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> findRoles(String username){
        return  jdbcTemplate.query("select role.role_name rname from sys_role_user ruser left join sys_role role on role.id = ruser.role_id left join sys_user us on us.id = ruser.user_id where us.username = ?",
                new Object[]{username},
                new int[]{java.sql.Types.VARCHAR},
                new RowMapper(){
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("rname");
                    }
                });
    }

    public List<String> findPermissions(String username){
        return  jdbcTemplate.query("select res.permission permission from sys_role_resource rr left join sys_role role on role.id = rr.role_id left join sys_resource res on res.id = rr.resource_id " +
                        " left join sys_role_user ruser on ruser.role_id = rr.role_id left join sys_user us on us.id = ruser.user_id where us.username = ?",
                new Object[]{username},
                new int[]{java.sql.Types.VARCHAR},
                new RowMapper(){
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("permission");
                    }
                });
    }

    public List<Map<String,Object>> findAllPermissions(){
        return  jdbcTemplate.queryForList("select * from sys_resource where PARENT_ID  IS NULL ORDER BY PRIORITY ");
    }
    public List<Map<String,Object>> findAllPermissionsByParent(String parentId){
        return  jdbcTemplate.queryForList("select * from sys_resource where PARENT_ID = ? ORDER BY PRIORITY ",new Object[]{parentId},
                new int[]{java.sql.Types.VARCHAR});
    }
    public User findByUsername(String username){
        List<User> users = new ArrayList<User>();
        User user = null;
        users =  jdbcTemplate.query("select * from sys_user where username = ?",
                new Object[]{username},
                new int[]{java.sql.Types.VARCHAR},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User user  = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setPassword(rs.getString("password"));
                        user.setSalt(rs.getString("salt"));
                        return user;
                    }
                }
        );
        if(users.size()>0){
            user = users.get(0);
        }
        return user;
    }



    /***
     * 新增或修改对象
     * @param user
     */
    public int saveOrUpdate(final User user) {
        if(user!=null && user.getId() > 0){
          return   jdbcTemplate.update(
                    "update SYS_USER set username=?,password=?,salt=? where id = ?",
                    new PreparedStatementSetter(){
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setString(1, user.getUsername());
                            ps.setString(2, user.getPassword());
                            ps.setString(3, user.getSalt());
                            ps.setInt(4, user.getId());
                        }
                    }
            );
        }else{
           return jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement("insert into SYS_USER(id,username,password,salt) values(seq_test.nextval,?,?,?)");
                    ps.setString(1, user.getUsername());
                    ps.setString(2, user.getPassword());
                    ps.setString(3, user.getSalt());
                    return ps;
                }
            });

        }
    }

}
