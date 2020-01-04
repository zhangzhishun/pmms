package com.springboot.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.domain.Student;
import com.springboot.until.TokenUtils;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Controller
@CrossOrigin
public class LoginController {

    @PostMapping("/loginPost")
    @ResponseBody
    public Object loginPost(@RequestParam("username") String username,@RequestParam("password") String password,HttpServletResponse response){
        System.out.println("login被访问");
        Student result = new Student();
        JSONObject jsonObject = new JSONObject();
        if(username.equals("1") && password.equals("1")){
            String token = TokenUtils.createJwtToken("11111");
            System.out.println(token);
            jsonObject.put("token", token);
            /*Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);*/
            result.setStuId("1");
            result.setPassword("1");
            jsonObject.put("student",result);
        }else{
            jsonObject.put("message", "登录失败,密码错误");
        }
        return jsonObject;
    }

    @ResponseBody
    @GetMapping("/login")
    public Student loginGet(){
        Student result = new Student("1","1");
        System.out.println(11111);
        return result;
    }

}
