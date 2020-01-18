package com.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.springboot.service.StudentService;
import com.springboot.service.AdminService;
import com.springboot.until.token.TokenUtils;
import com.springboot.until.httpResult.HttpResult;
import com.springboot.until.httpResult.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
    public Object stuLogin(@RequestParam("username") String stuId, @RequestParam("password") String stuPassword, HttpServletRequest request){
        System.out.println("stuLogin访问");
        Integer loginReq = studentService.login(Integer.valueOf(stuId),stuPassword);
        if(loginReq == 200){
            // 登陆成功
            request.getSession().setAttribute("stuId", stuId);
            return new HttpResult();
        }else if(loginReq == 400){
            // 密码错误
            return new HttpResult(ResultCode.PARAMETER_ERROR);
        }
        else if(loginReq == 404){
            // 账号不存在
            return new HttpResult(ResultCode.NOT_FOUND);
        }else if(loginReq == 500){
            // 网络异常
            return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
        }
        return null;
    }

    @PostMapping("/adminLogin")
    public Object adminLogin(@RequestParam("username") String admName,@RequestParam("password") String admPassword,HttpServletRequest request){
        System.out.println("adminLogin访问"+admName);
        Integer loginReq = adminService.login(admName,admPassword);
        if(loginReq == 200){
            // 登陆成功
            request.getSession().setAttribute("admLevel", loginReq);
            return new HttpResult(adminService.getAdminByName(admName));
        }else if(loginReq == 400){
            // 密码错误
            return new HttpResult(ResultCode.PARAMETER_ERROR);
        }
        else if(loginReq == 404){
            // 账号不存在
            return new HttpResult(ResultCode.NOT_FOUND);
        }else if(loginReq == 500){
            // 网络异常
            return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
        }
        return null;
    }

}
