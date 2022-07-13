package com.example.config;

import com.example.entity.TestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
//配置类声明
@Configuration
//自动扫描Controller中的bean
@ComponentScan("com.example.controller")
//搭配WebMvcConfigurer接口，使能够展示静态页面
@EnableWebMvc
//Servlet容器
// 与Spring的MainConfiguration不同，WebConfiguration主要是配置前端相关的配置
public class WebConfiguration implements WebMvcConfigurer {

    //配置ThymeleafViewResolver作为视图解析器，并解析我们的HTML页面
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(@Autowired SpringTemplateEngine springTemplateEngine){
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        //可以存在多个视图解析器，并且可以为他们设定解析顺序
        resolver.setOrder(1);
        //编码格式是重中之重
        resolver.setCharacterEncoding("UTF-8");
        //和之前JavaWeb阶段一样，需要使用模板引擎进行解析，所以这里也需要设定一下模板引擎
        resolver.setTemplateEngine(springTemplateEngine);
        return resolver;
    }

    //配置模板解析器
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setCharacterEncoding("utf-8");
        //需要解析的后缀名称
        resolver.setSuffix(".html");
        //需要解析的HTML页面文件存放的位置
        resolver.setPrefix("/WEB-INF/template/");
        return resolver;
    }

    //配置模板引擎Bean
    @Bean
    public SpringTemplateEngine springTemplateEngine(@Autowired ITemplateResolver resolver){
        SpringTemplateEngine engine = new SpringTemplateEngine();
        //模板解析器，默认即可
        engine.setTemplateResolver(resolver);
        return engine;
    }
    //开启默认的Servlet
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //配置静态资源的访问路径
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //**表示一级或多级目录
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");

    }
    //探寻session/request/bean的scope
    @Bean
    @RequestScope
    public TestBean testBean(){
        return new TestBean();
    }


}
