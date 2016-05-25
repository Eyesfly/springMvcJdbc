package com.bjrxht.controller;

import com.bjrxht.entity.User;
import com.bjrxht.entity.UserInfo;
import com.bjrxht.service.IUserService;
import com.bjrxht.utils.AppConstant;
import com.bjrxht.utils.EndecryptUtils;
import com.bjrxht.utils.Message;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.common.collect.Maps;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/28.
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    Producer captchaProducer;
    @Autowired
    IUserService userService;
    @RequestMapping("/customSecurityCheck")
    public ModelAndView loginSubmit(ModelMap modelMap, User user, HttpServletRequest request, HttpServletResponse response)throws Exception{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vcode = request.getParameter("code");

        String error = null;
        ModelAndView  mav = new ModelAndView("/login");
        try {
            Object code = request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            if (vcode!=null&&code!=null&&!vcode.equalsIgnoreCase(code.toString())) {
                error = "验证码错误";
            }else{
                Subject subject = SecurityUtils.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                subject.login(token);
            }
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
            e.printStackTrace();
        }
        modelMap.addAttribute("error",error);
        if(error != null) {//出错了，返回登录页面
            modelMap.addAttribute("error",error);
            mav = new ModelAndView("/login");
        } else {//登录成功
            mav = new ModelAndView("redirect:/");
        }
        mav.addObject("error",error);
        return mav;
    }

    @RequestMapping("/logout")
    public String logout(){
       Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:/";
    }

    @RequestMapping("/login")
    public String login(ModelMap modelMap,User entity){
        return "/login";
    }
    @RequestMapping("/register")
    public String register(ModelMap modelMap,User entity){
        return "/register";
    }
    @RequestMapping("/customRegister")
    public String customRegister(ModelMap modelMap,User entity){
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
            return "/login";
        }else{
            modelMap.addAttribute("error","注册失败！");
            return "/register";
        }
    }
    @RequestMapping("/code")
    @ResponseBody
    public String code(ModelMap modelMap,HttpServletResponse response,HttpServletRequest request) throws Exception {
        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage image = captchaProducer.createImage(capText);
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
        encoder.encode(image);
        return null;
    }
}
