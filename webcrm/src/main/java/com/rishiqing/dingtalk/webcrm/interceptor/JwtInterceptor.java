package com.rishiqing.dingtalk.webcrm.interceptor;

import com.rishiqing.dingtalk.isv.api.model.corp.CorpStaffVO;
import com.rishiqing.dingtalk.webcrm.util.jwt.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-12-18 15:57
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {
    private static final Logger bizLogger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            String jwtToken = StringUtils.isEmpty(request.getHeader("token")) ? request.getParameter("token") : request.getHeader("token");
            // 检查是否为空
            if (StringUtils.isEmpty(jwtToken)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            CorpStaffVO staffVO = JwtUtil.check(jwtToken);
            if (staffVO == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            request.setAttribute("loginUser", staffVO);
            //TODO 判断如果快要过期了，那么需要对token进行续期，看下方postHandle()方法
            return true;
        } catch (Exception e) {
            bizLogger.error("error interceptor", e);
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //判断如果快要过期了，那么需要对token进行续期
        String token = StringUtils.isEmpty(request.getHeader("token")) ? request.getParameter("token") : request.getHeader("token");
        //获得过期日期时间
        Date expireDate = JwtUtil.getExpireDate(token);
        //得到相差毫秒数
        Long diff = System.currentTimeMillis() - expireDate.getTime();
        if (diff >= 1000 * 60 * 5L) {
            //5min，即将过期，开始续期返回新的token
            CorpStaffVO corpStaffVO = JwtUtil.check(token);
            String newToken = JwtUtil.sign(corpStaffVO);
            response.addHeader("token", newToken);
        } else {
            response.addHeader("token", token);
        }
    }
}
