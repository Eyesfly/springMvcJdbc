package com.bjrxht.interceptor;

import com.bjrxht.entity.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/3/28.
 */
public class UserInterceptor implements HandlerInterceptor {
    protected Logger logger = Logger.getLogger(getClass());
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
// intercept
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("user") == null) {
            logger.info("Interceptor：跳转到login页面！");
            httpServletRequest.getRequestDispatcher("/login.jsp").forward(httpServletRequest, httpServletResponse);
            return  false;
        } else {
            logger.info("userInfo============"+((UserInfo)session.getAttribute("user")));
            return true;
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("==============执行顺序: 2、postHandle================");
        if(modelAndView != null){  //加入当前时间
            modelAndView.addObject("var", "测试postHandle");
/*
            httpServletRequest.getRequestDispatcher("/index.jsp").forward(httpServletRequest, httpServletResponse);
*/
        }
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("==============执行顺序: 3、afterCompletion================");
    }
}
