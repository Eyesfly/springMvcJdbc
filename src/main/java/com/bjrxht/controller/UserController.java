package com.bjrxht.controller;

import com.bjrxht.service.IUserService;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/29.
 */
@Controller
@RequestMapping("/role")
public class UserController {
    public Logger logger = Logger.getLogger(getClass());
    @Autowired
    private IUserService userService;

@RequestMapping("findRoles")
@ResponseBody
    public List<String> findRoles(){
        List<String> roles = new ArrayList<String>();
        List<String> permissions = new ArrayList<String>();
        roles = userService.findRoles("user");
        permissions = userService.findPermissions("user");
        logger.info("roles============"+roles);
        logger.info("permissions============"+permissions);
        return roles;
    }
    @RequestMapping("permissionsJson")
    @ResponseBody
    public String findAllPermissions(){
        List<Map<String,Object>> permissions = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> treeList = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        permissions = userService.findAllPermissions();
        treeList = tree(list,permissions);
        Gson json = new Gson();
        String treeJson = json.toJson(treeList);
        logger.info("permissions============"+treeJson);

        return treeJson;
    }

    @RequestMapping("permission")
    public String permission(){
        return "/user/permissions";
    }

    public List<Map<String,Object>> tree(List<Map<String,Object>> treeList,List<Map<String,Object>> permissions){

        for (Map<String, Object> permission : permissions ) {
            Map<String,Object> map = new HashMap<String, Object>();
            String id = permission.get("ID").toString();
            String name =  (String)permission.get("Name");
            map.put("id",id);
            map.put("text",name);
//            获取子节点
            List<Map<String,Object>> permissions_sun = new ArrayList<Map<String, Object>>();
            permissions_sun = userService.findAllPermissionsByParent(id+"");
            if(!permissions_sun.isEmpty()){
                List<Map<String,Object>> suns = new ArrayList<Map<String, Object>>();
                List<Map<String,Object>> tempList = new ArrayList<Map<String, Object>>();
                suns = tree(tempList,permissions_sun);
                map.put("children",suns);
            }
            treeList.add(map);
        }

       return treeList;
    }

}
