package com.xtm.config;

import com.xtm.interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author:藏剑
 * @date:2019/6/18 11:32
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/defaultKaptcha","/loginResource/**", "/asserts/**", "/admin/login", "/swagger-ui.html#", "/front/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/article.html").setViewName("article/newArticle");
        registry.addViewController("/listArticle.html").setViewName("article/listArticle");
        registry.addViewController("/news.html").setViewName("news/publishNews");
        registry.addViewController("/listNews.html").setViewName("news/listNews");
        registry.addViewController("/profile.html").setViewName("admin/profile");
        registry.addViewController("/messages.html").setViewName("message/listMessage");
        registry.addViewController("/dashboard.html").setViewName("dashboard");
    }
}
