package com.bjrxht.service;

import com.bjrxht.entity.UserInfo;
import com.bjrxht.utils.PageBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/25.
 */
public interface IUserInfoService {

    void saveOrUpdate(UserInfo userInfo);
    int delete(UserInfo userInfo);
    UserInfo queryForUserInfo(UserInfo userInfo);
    List<UserInfo> queryForList();
    List<UserInfo> list(UserInfo userInfo);
    PageBean list(Map<String,String> query);
}
