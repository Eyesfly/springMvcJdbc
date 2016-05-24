package com.bjrxht.service.impl;

import com.bjrxht.dao.UserInfoDao;
import com.bjrxht.entity.UserInfo;
import com.bjrxht.service.IUserInfoService;
import com.bjrxht.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/25.
 */
@Service
public class UserInfoService implements IUserInfoService {
    @Autowired
    public UserInfoDao userInfoDao;
    @CacheEvict(value = { "usercache"}, allEntries = true)
    public void saveOrUpdate(UserInfo userInfo) {
        userInfoDao.saveOrUpdate(userInfo);
/*        throw new RecoverableDataAccessException("TEST");*/
    }

    public int delete(UserInfo userInfo) {
        return userInfoDao.delete(userInfo);
    }

    public UserInfo queryForUserInfo(UserInfo userInfo) {
        return userInfoDao.queryForUserInfo(userInfo);
    }

    public List<UserInfo> queryForList() {
        return userInfoDao.queryForList();
    }

    public List<UserInfo> list(UserInfo userInfo) {
        return userInfoDao.list(userInfo);
    }
    @Cacheable(value = "usercache",keyGenerator = "wiselyKeyGenerator")
    public PageBean list(Map<String, String> query) {
        System.out.println("无缓存的时候调用这里");
        return userInfoDao.list(query);
    }
}
