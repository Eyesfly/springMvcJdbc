package com.bjrxht.controller;

import com.bjrxht.entity.User;
import com.bjrxht.entity.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/3/28.
 */
@Controller
public class LoginController {
    @RequestMapping("/login.ht")
    public String loginSubmit(ModelMap modelMap, User user, HttpServletRequest request, HttpServletResponse response)throws Exception{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String error = null;
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            error = "用户名不存在";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        } catch (AuthenticationException e) {
            //其他错误，比如锁定，如果想单独处理请单独catch处理
            error = "其他错误：" + e.getMessage();
        }
        modelMap.addAttribute("error",error);
        if(error != null) {//出错了，返回登录页面
            modelMap.addAttribute("error",error);
            return "redirect:/login.jsp";
        } else {//登录成功
            return "redirect:/";
        }
/*
        return "/login";
*/
//        return "redirect:/user/list.ht";
    }
    @RequestMapping("/logout.ht")
    public String logout(){
       Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/";
    }

}
