package com.example.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MainInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    //本类是用来代替web.xml的配置类，
    @Override
    protected Class<?>[] getRootConfigClasses() {
        //根容器是Servlet容器的父容器
        //基本的Spring配置类，一般用于业务层配置,负责Service，dao层，包括数据源等较底层
        return new Class[]{MainConfiguration.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //配置DispatcherServlet的配置类、主要用于Controller、视图解析器等配置
        return new Class[]{WebConfiguration.class};
    }

    @Override
    protected String[] getServletMappings() {
        //匹配路径，与上面一致
        return new String[]{"/"};
    }
}
