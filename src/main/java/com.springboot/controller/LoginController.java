package com.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.service.StudentService;
import com.springboot.service.AdminService;
import com.springboot.until.TokenUtils;
import com.springboot.until.httpResult.HttpResult;
import com.springboot.until.httpResult.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @CrossOrigin 实现跨域访问
 * @author eternalSy
 * @version 1.0.0
 */
@Controller
@CrossOrigin
@ResponseBody
public class LoginController {

    @Autowired
    StudentService studentService;
    @Autowired
    AdminService adminService;


    @PostMapping("/stuLogin")
    public Object stuLogin(@RequestParam("username") String stuId,@RequestParam("password") String stuPassword,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        System.out.println("login被访问");
        Integer loginReq = studentService.login(Integer.valueOf(stuId),stuPassword);
        // 登陆成功
        if(loginReq == 200){
            String token = TokenUtils.createJwtToken("username");
            System.out.println(token);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            jsonObject.put("base", HttpResult.success());
            jsonObject.put("token", token);
        }else if(loginReq == 400){
            // 密码错误
            jsonObject.put("base", HttpResult.failure(ResultCode.PARAMETER_ERROR));
        }
        else if(loginReq == 404){
            // 账号不存在
            jsonObject.put("base", HttpResult.failure(ResultCode.NOT_FOUND));
        }else if(loginReq == 500){
            // 网络异常
            jsonObject.put("base", HttpResult.failure(ResultCode.GATEWAY_TIMEOUT));
        }
        return jsonObject;
    }

    @PostMapping("/adminLogin")
    public Object adminLogin(@RequestParam("username") String admId,@RequestParam("password") String admPassword,HttpServletResponse response){
        JSONObject jsonObject = new JSONObject();
        System.out.println("login被访问");
        Integer loginReq = adminService.login(Integer.valueOf(admId),admPassword);
        // 登陆成功
        if(loginReq == 200){
            String token = TokenUtils.createJwtToken("username");
            System.out.println(token);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            response.addCookie(cookie);
            jsonObject.put("base", HttpResult.success());
            jsonObject.put("token", token);
        }else if(loginReq == 400){
            // 密码错误
            jsonObject.put("base", HttpResult.failure(ResultCode.PARAMETER_ERROR));
        }
        else if(loginReq == 404){
            // 账号不存在
            jsonObject.put("base", HttpResult.failure(ResultCode.NOT_FOUND));
        }else if(loginReq == 500){
            // 网络异常
            jsonObject.put("base", HttpResult.failure(ResultCode.GATEWAY_TIMEOUT));
        }
        return jsonObject;
    }

}
