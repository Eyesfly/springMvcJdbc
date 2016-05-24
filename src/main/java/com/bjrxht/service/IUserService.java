package com.bjrxht.service;

import com.bjrxht.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface IUserService {
     User createUser(User user); //创建账户
     void changePassword(Long userId, String newPassword);//修改密码
     void correlationRoles(Long userId, Long... roleIds); //添加用户-角色关系
     void uncorrelationRoles(Long userId, Long... roleIds);// 移除用户-角色关系
     User findByUsername(String username);// 根据用户名查找用户
     List<String> findRoles(String username);// 根据用户名查找其角色
     List<String> findPermissions(String username); //根据用户名查找其权限
     List<Map<String,Object>> findAllPermissions();
     List<Map<String,Object>> findAllPermissionsByParent(String parentId);
}
