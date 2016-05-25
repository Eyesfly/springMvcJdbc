package com.bjrxht.controller;

import com.bjrxht.entity.User;
import com.bjrxht.entity.UserInfo;
import com.bjrxht.service.IUserService;
import com.bjrxht.utils.AppConstant;
import com.bjrxht.utils.EndecryptUtils;
import com.bjrxht.utils.Message;
import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/28.
 */
@Controller
public class LoginController {

    @Autowired
    IUserService userService;
    @RequestMapping("/login.ht")
    public String loginSubmit(ModelMap modelMap, User user, HttpServletRequest request, HttpServletResponse response)throws Exception{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String error = null;

        try {
            subject.login(token);
            /*if(Boolean.TRUE.equals(user.isLocked())) {
                throw new LockedAccountException(); //帐号锁定
            }*/
        } catch (UnknownAccountException e) {
            error = "用户名不存在";
        } catch (IncorrectCredentialsException e) {
            error = "用户名/密码错误";
        }catch (ExcessiveAttemptsException ex)
        {
            error="帐号被锁定1小时";
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
    }

    @RequestMapping("/logout.ht")
    public String logout(){
       Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/";
    }

    @RequestMapping("/register.ht")
    public String register(ModelMap modelMap,User entity){
        //加密用户输入的密码，得到密码的摘要和盐，保存到数据库
        User user = EndecryptUtils.md5Password(entity.getUsername(), entity.getPassword());
        entity.setPassword(user.getPassword());
        entity.setSalt(user.getSalt());
        int row = 0;
        try {
           row =  userService.saveOrUpdate(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(row>0){
            modelMap.addAttribute("error","注册成功请登录！");
            return "redirect:/login.jsp";
        }else{
            modelMap.addAttribute("error","注册失败！");
            return "redirect:/register.jsp";
        }
    }
}
