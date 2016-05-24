package com.bjrxht.filter.shiro;

import com.bjrxht.service.IUserService;
import com.bjrxht.service.impl.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SysUserFilter extends PathMatchingFilter {
    public static final String CURRENT_USER = "user";
    @Autowired
    private IUserService userService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String username = (String) SecurityUtils.getSubject().getPrincipal();
        request.setAttribute(CURRENT_USER, userService.findByUsername(username));
        return true;
    }
}
