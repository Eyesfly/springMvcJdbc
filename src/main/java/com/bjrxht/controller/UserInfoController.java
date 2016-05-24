package com.bjrxht.controller;

import com.bjrxht.entity.UserInfo;
import com.bjrxht.service.IUserInfoService;
import com.bjrxht.utils.PageBean;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/25.
 */
@Controller
@RequestMapping("/user")
public class UserInfoController{
    protected Logger logger = Logger.getLogger(getClass());
    @Autowired
    public IUserInfoService userInfoService;

    @RequestMapping("/saveUserInfo")
    @ResponseBody
    public String save(UserInfo userInfo){
        userInfoService.saveOrUpdate(userInfo);
        return "true:保存成功";
//        return "redirect:/user/list.ht";
    }

    @RequestMapping("/update/{userId}")
    public String update(ModelMap modelMap, @PathVariable int userId) throws Exception{

            UserInfo userInfo = new UserInfo();
            userInfo.setId(userId);
            userInfo = userInfoService.queryForUserInfo(userInfo);
            modelMap.addAttribute("userInfo",userInfo);

//        return "/user/update";
        return "true:保存成功";
    }



    @RequestMapping("/list")
    public String list(ModelMap modelMap){
        Subject currentUser  = SecurityUtils.getSubject();
/*      logger.info("====USER================="+ currentUser.getPrincipals());
      logger.info("hasRole====ROLE_USER================="+ currentUser.hasRole("ROLE_USER"));
      logger.info("hasRole====ROLE_MANAGER================="+ currentUser.hasRole("ROLE_MANAGER"));*/
/*      List<UserInfo> userInfos =   userInfoService.queryForList();
        modelMap.addAttribute("userInfos",userInfos);*/
        return "/user/list";
    }


    @RequestMapping("/create")
    public String create(ModelMap modelMap){
        return "/user/create";
    }



    @RequestMapping(value = "/userJson", produces = { "text/html;charset=UTF-8" })
    @ResponseBody
    public String userJson(ModelMap modelMap,HttpServletRequest req,HttpSession session){

        String page = req.getParameter("page") == null ? "" : req
                .getParameter("page");
        String rows = req.getParameter("rows") == null ? "" : req
                .getParameter("rows");
        if ("".equals(rows)) {
            rows = "10";
        }
        Map<String, String> query = new HashMap<String, String>();
        query.put("page", page);
        query.put("rows", rows);

        PageBean pageBean = userInfoService.list(query);

        Gson gson = new Gson();
        String json =  gson.toJson(pageBean);
//        String json =  "{\"total\":15681,\"totalPages\":1569,\"pageSize\":10,\"currentPageNo\":1,\"previousPageNo\":1,\"nextPageNo\":2,\"isFirstPage\":true,\"isLastPage\":false,\"hasPreviousPage\":false,\"hasNextPage\":true,\"rows\":[{\"id\":23,\"uname\":\"李四士大夫\",\"unumber\":52},{\"id\":26,\"uname\":\"张三\",\"unumber\":88},{\"id\":27,\"uname\":\"张三\",\"unumber\":88},{\"id\":28,\"uname\":\"张三\",\"unumber\":88},{\"id\":29,\"uname\":\"张三\",\"unumber\":88},{\"id\":30,\"uname\":\"张三\",\"unumber\":88},{\"id\":31,\"uname\":\"张三\",\"unumber\":88},{\"id\":32,\"uname\":\"张三\",\"unumber\":88},{\"id\":39,\"uname\":\"张三\",\"unumber\":88},{\"id\":40,\"uname\":\"张三\",\"unumber\":88}]}";
        logger.info("json=========="+json);
        return json;
    }


    @RequestMapping("/delete/{userId}")
    public String delete(ModelMap modelMap, @PathVariable int userId){
       UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
       userInfoService.delete(userInfo);
        return "redirect:/user/list.ht";
    }


    @RequestMapping("/deleteAll")
    @ResponseBody
    public String deleteAll(ModelMap modelMap,HttpServletRequest req){
        String usersId = req.getParameter("fileIds");
        String[] strs = usersId.split(",");
        for (int i = 0; i < strs.length; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(Integer.valueOf(strs[i]));
            userInfoService.delete(userInfo);
        }
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("result",true);
        map.put("message","删除成功");
        return gson.toJson(map);
    }


}
