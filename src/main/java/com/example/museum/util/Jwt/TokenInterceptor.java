package com.example.museum.util.Jwt;
import com.alibaba.fastjson.JSONObject;

import com.example.museum.entity.ApiResult;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{

            if(request.getMethod().equals("OPTIONS")){
                response.setStatus(HttpServletResponse.SC_OK);
                return true;
            }

            response.setCharacterEncoding("utf-8");

            String token = request.getHeader("token");


            if(token != null){
                boolean result = TokenUtil.verify(token);
                if(result){
                    return true;
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try{
                JSONObject json = new JSONObject();
                json.put("data","");
                json.put("message","token过期 请重新登录");
                json.put("code","508");

                response.getWriter().append(json.toJSONString());
                System.out.println("认证失败，未通过拦截器");
                //        response.getWriter().write("50000");
            }catch (Exception e){
                e.printStackTrace();
                response.sendError(500);
                return false;
            }


            return false;

        }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
       // System.out.println("====post===");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
     //   System.out.println("====afterCompletion===");

    }

}
