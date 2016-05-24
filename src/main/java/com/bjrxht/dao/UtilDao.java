package com.bjrxht.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/3/28.
 */
@Repository
public class UtilDao {
    /**
     * 获取查询数据的总条数，用于列表数据的分页
     *
     * @param sql
     * @param parms
     * @param jdbcTemplate
     * @return
     */
    public static int getTotal(StringBuffer sql, Object[] parms,
                               JdbcTemplate jdbcTemplate) {
        return jdbcTemplate.queryForInt(sql.toString(), parms);
    }
}
