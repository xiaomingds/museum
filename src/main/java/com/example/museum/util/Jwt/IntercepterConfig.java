package com.example.museum.util.Jwt;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zeng
 * @date 2020/2/17 11:18
 */
@Configuration
public class IntercepterConfig  implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry){
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/error"); //注册
        excludePath.add("/login"); //登录
        excludePath.add("/logout"); //登出
        excludePath.add("/user/login"); //登出
        excludePath.add("/user/info");
        excludePath.add("http://localhost:8088/");
        //不拦截Swagger
        excludePath.add("/v2/api-docs/**");
        excludePath.add("/v2/api-docs-ext/**");
        excludePath.add("/swagger-resources");
        excludePath.add("/error/**");
        excludePath.add("/webjars/**");
        excludePath.add("/swagger-ui.html");
        excludePath.add("/doc.html");

        registry.addInterceptor(new TokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludePath);
        WebMvcConfigurer.super.addInterceptors(registry);
    }


}
