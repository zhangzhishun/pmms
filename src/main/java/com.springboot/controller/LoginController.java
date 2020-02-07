package com.springboot.controller;

import com.springboot.entity.Student;
import com.springboot.service.StuClassService;
import com.springboot.service.StudentService;
import com.springboot.service.AdminService;
import com.springboot.until.httpResult.HttpResult;
import com.springboot.until.httpResult.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    StudentService studentServiceImpl;
    @Autowired
    AdminService adminServiceImpl;
    @Autowired
    StuClassService stuClassServiceImpl;

    @PostMapping("/stuLogin")
    public Object stuLogin(@RequestParam("username") String stuId, @RequestParam("password") String stuPassword, HttpServletRequest request){
        System.out.println("stuLogin访问");
        Integer loginReq = studentServiceImpl.login(Integer.valueOf(stuId),stuPassword);
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
        Integer loginReq = adminServiceImpl.login(admName,admPassword);
        if(loginReq == 200){
            // 登陆成功
            request.getSession().setAttribute("admLevel", loginReq);
            return new HttpResult(adminServiceImpl.getAdminByName(admName));
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

    @PostMapping("/changePassword")
    public Object changePassword(@RequestParam("stuId") Integer stuId, @RequestParam("stuName") String stuName,
                                 @RequestParam("stuClassName") String stuClassName,  @RequestParam("stuContactInformation") String stuContactInformation,
                                 @RequestParam("newPsw") String newPsw, HttpServletRequest request){
        System.out.println("changePassword访问");
        Student student = studentServiceImpl.getStudentByStuId(stuId);
        // 如果输入的信息全部正确 那么更新用户信息  否则返回提示
        if(student!=null &&
                stuClassServiceImpl.getStuClassByName(stuClassName)!=null &&
                stuName.equals(student.getStuName()) &&
                student.getStuClassId().equals(stuClassServiceImpl.getStuClassByName(stuClassName).getStuClassId()) &&
                stuContactInformation.equals(student.getStuContactInformation()) ){
            student.setStuPassword(newPsw);
            return new HttpResult(studentServiceImpl.updateStudent(student));
        }else{
            return new HttpResult(false, 200,"输入信息的不匹配", "error");
        }
    }

}
