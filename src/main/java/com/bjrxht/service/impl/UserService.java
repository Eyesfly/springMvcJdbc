package com.bjrxht.service.impl;

import com.bjrxht.dao.UserDao;
import com.bjrxht.entity.User;
import com.bjrxht.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/29.
 */
@Service
public class UserService implements IUserService{
@Autowired
private UserDao userDao;

    public int saveOrUpdate(User user) {
        return userDao.saveOrUpdate(user);
    }

    public void changePassword(Long userId, String newPassword) {

    }

    public void correlationRoles(Long userId, Long... roleIds) {

    }

    public void uncorrelationRoles(Long userId, Long... roleIds) {

    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<String> findRoles(String username) {
        return userDao.findRoles(username);
    }

    public List<String> findPermissions(String username) {
        return userDao.findPermissions(username);
    }

    public List<Map<String, Object>> findAllPermissions() {
        return userDao.findAllPermissions();
    }

    public List<Map<String, Object>> findAllPermissionsByParent(String parentId) {
        return userDao.findAllPermissionsByParent(parentId);
    }
}
