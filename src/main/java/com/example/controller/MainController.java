package com.example.controller;

import com.example.entity.TestBean;
import com.example.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
//我们使用Controller代替Servlet
//一个方法加上注解就相当于原来的一个Servlet

public class MainController {

    //访问http://localhost:8080/mvc/test进入对应html
    @RequestMapping(value = "/test")
    public ModelAndView index(@RequestParam(value="username",required = false, defaultValue = "伞兵一号") String username){
        //创建视图名称对象
        ModelAndView modelAndView = new ModelAndView("index");
        //像参数注入数据
        modelAndView.getModel().put("name", "啊这");
        //返回的其实只是视图名称
        return modelAndView;
    }

    //向前端页面传值
    @RequestMapping(value = "/test2")
    public String index(Model model){  //这里不仅仅可以是Model，还可以是Map、ModelMap
        model.addAttribute("name", "Wahoyu-");
        //简略版返回视图名称
        return "index";
    }

    //可以收到User对象
    @RequestMapping(value = "/user")
    public ModelAndView index(User user){
        System.out.println("收到请求参数："+user);
        return new ModelAndView("index");
    }

    //可以继承HttpSession
    @RequestMapping(value = "/session")
    public ModelAndView index(HttpSession session){
        session.setAttribute("test", "鸡你太美");
        System.out.println(session.getAttribute("test"));
        return new ModelAndView("index");
    }

    //可调用Servlet方法
    @RequestMapping(value = "/servlet")
    public ModelAndView index(HttpServletRequest request){
        System.out.println("接受到请求参数："+request.getParameterMap().keySet());
        return new ModelAndView("index");
    }

    //第二次能获取到Cookie
    @RequestMapping(value = "/cookie")
    public ModelAndView index(HttpServletResponse response,
                              @CookieValue(value = "test", required = false) String test){
        System.out.println("获取到cookie值为："+test);
        response.addCookie(new Cookie("test", "lbwnb"));
        return new ModelAndView("index");
    }

    //使用注解快速获取Session
    @RequestMapping(value = "/index")
    public ModelAndView index(@SessionAttribute(value = "test", required = false) String test,
                              HttpSession session){
        session.setAttribute("test", "xxxx");
        System.out.println(test);
        return new ModelAndView("index");
    }

    //重定向
    @RequestMapping("/redirect")
    public String index(){
        return "redirect:home";
    }
    //被动方法
    @RequestMapping("/home")
    public String home(){
        return "index";
    }
    //请求转发
    @RequestMapping("/forward")
    public String index2(){
        return "forward:home";
    }
    //探寻session/request/bean的scope
    @Resource
    TestBean bean;
    @RequestMapping(value = "/scope")
    public ModelAndView index3(){
        //System.out.println(bean);
        return new ModelAndView("index");
    }

    //RESTful格式声明
    @RequestMapping("/restful/{str}")
    public String index4(@PathVariable String str) {
        System.out.println(str);
        return "index";
    }

    //将restful传入的值作为函数的参数
    @RequestMapping("/rest_param/{str}")
    public String index5(@PathVariable("str") String text){
        System.out.println(text);
        return "index";
    }

    //四种不同的请求对应相同的地址
    @RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
    public String get(@PathVariable("id") String text){
        System.out.println("获取用户："+text);
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public String post(String username){
        System.out.println("添加用户："+username);
        return "index";
    }

    @RequestMapping(value = "/index/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") String text){
        System.out.println("删除用户："+text);
        return "index";
    }

    @RequestMapping(value = "/index", method = RequestMethod.PUT)
    public String put(String username){
        System.out.println("修改用户："+username);
        return "index";
    }

}
