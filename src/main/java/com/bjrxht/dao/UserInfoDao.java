package com.bjrxht.dao;

import com.bjrxht.entity.UserInfo;
import com.bjrxht.utils.PageBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/25.
 */
@Repository
public class UserInfoDao{
    protected Logger logger = Logger.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /***
     * 新增或修改对象
     * @param userInfo
     */
    public void saveOrUpdate(final UserInfo userInfo) {
        if(userInfo!=null && userInfo.getId() > 0){
            jdbcTemplate.update(
                    "update USER_INFO set uname=?,unumber=? where id = ?",
                    new PreparedStatementSetter(){
                        public void setValues(PreparedStatement ps) throws SQLException {
                            ps.setString(1, userInfo.getUname());
                            ps.setInt(2, userInfo.getUnumber());
                            ps.setInt(3, userInfo.getId());
                        }
                    }
            );
        }else{
            jdbcTemplate.update(new PreparedStatementCreator() {
                                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                                        PreparedStatement ps = connection.prepareStatement("insert into USER_INFO(id,uname,unumber) values(seq_test.nextval,?,?)");
                                        ps.setString(1, userInfo.getUname());
                                        ps.setInt(2, userInfo.getUnumber());
                                        return ps;
                                    }
                                });

        }

    }

    /**
     * 删除所传对象
     * @param userInfo
     */
    public int delete(UserInfo userInfo) {
       return jdbcTemplate.update(
                "delete from USER_INFO where id = ?",
                new Object[]{userInfo.getId()},
                new int[]{java.sql.Types.INTEGER});
    }

    /***
     *
     * 查询用户信息
     * @param userInfo
     * @return
     */
    public UserInfo queryForUserInfo(UserInfo userInfo) {
        return (UserInfo) jdbcTemplate.queryForObject("select * from USER_INFO where id = ?",
                new Object[]{userInfo.getId()},
                new int[]{java.sql.Types.INTEGER},
                new RowMapper() {
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        UserInfo userInfo  = new UserInfo();
                        userInfo.setId(rs.getInt("id"));
                        userInfo.setUname(rs.getString("uname"));
                        userInfo.setUnumber(rs.getInt("unumber"));
                        return userInfo;
                    }
                }
        );

    }

    @SuppressWarnings("unchecked")
    //最全的参数查询
    public List<UserInfo> queryForListByUname(UserInfo userInfo) {
        return (List<UserInfo>) jdbcTemplate.queryForList("select * from USER_INFO where uname = ?",
                new Object[]{userInfo.getUname()},
                new int[]{java.sql.Types.VARCHAR},
                UserInfo.class);
    }

    public PageBean list(Map<String,String> query){
        StringBuffer sql_total = new StringBuffer();
        sql_total.append("select count(1) ");
        StringBuffer sql_where = new StringBuffer(" where 1 = 1 ");
        String sql_orderby = " ORDER BY ID ";
        String sql_fields = "select * ";
        List<String> parms = new ArrayList<String>();
        String sql_from = " from USER_INFO";
        sql_total.append(sql_from);
        sql_total.append(sql_where);
        int total = UtilDao.getTotal(sql_total, parms.toArray(), jdbcTemplate);
        PageBean page = new PageBean(total, Integer.valueOf(query.get("rows")));
        page.setCurrentPageNo(Integer.valueOf(query.get("page")));

        StringBuffer sql = new StringBuffer();
        sql.append(sql_fields);
        sql.append(sql_from);
        sql.append(sql_where);
        sql.append(sql_orderby);

        StringBuffer sqlPage = new StringBuffer();
        sqlPage.append("SELECT * FROM (select t.*,rownum rn from (");
        sqlPage.append(sql);
        sqlPage.append(" ) t where rownum<="+page.getCurrentPageEndRecord()+") where rn>"+page.getCurrentPageStartRecord());

//        List<Map<String,Object>> data = jdbcTemplate.queryForList(sqlPage.toString(),parms.toArray());
        List<UserInfo> data = jdbcTemplate.query(sqlPage.toString(),parms.toArray(),new RowMapper(){
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserInfo userInfo  = new UserInfo();
                userInfo.setId(rs.getInt("id"));
                userInfo.setUname(rs.getString("uname"));
                userInfo.setUnumber(rs.getInt("unumber"));
                return userInfo;
            }
        });
        page.setRows(data);
        return page;
    }

    @SuppressWarnings("unchecked")
    //最全的参数查询
    public List<UserInfo> queryForList() {
        return  jdbcTemplate.query("select * From user_info",new RowMapper(){
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserInfo userInfo  = new UserInfo();
                userInfo.setId(rs.getInt("id"));
                userInfo.setUname(rs.getString("uname"));
                userInfo.setUnumber(rs.getInt("unumber"));
                return userInfo;
            }
        });
    }


    @SuppressWarnings("unchecked")
    public List<UserInfo> list(UserInfo userInfo) {
        return jdbcTemplate.query("select * from USER_INFO where uname like '%?%'",
                new Object[]{userInfo.getUname()},
                new int[]{java.sql.Types.VARCHAR},
                new RowMapper(){
                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        UserInfo userInfo  = new UserInfo();
                        userInfo.setId(rs.getInt("id"));
                        userInfo.setUname(rs.getString("uname"));
                        userInfo.setUnumber(rs.getInt("unumber"));
                        return userInfo;
                    }
                });
    }


    /***
     *
     *
     *
     *  调用存储过程
     public void callProcedure(int id){
     this.jdbcTemplate.update("call SUPPORT.REFRESH_USERS_SUMMARY(?)", new Object[]{Long.valueOf(id)});
     */
}
