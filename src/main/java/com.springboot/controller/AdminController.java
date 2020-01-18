package com.springboot.controller;

import com.springboot.service.AdminService;
import com.springboot.service.StudentService;
import com.springboot.until.httpResult.HttpResult;
import com.springboot.until.httpResult.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @CrossOrigin 实现跨域访问
 * @author eternalSy
 * @version 1.0.0
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("admin")
public class AdminController {

    @Autowired
    StudentService studentService;
    @Autowired
    AdminService adminService;

    @GetMapping("/getAllPartyByPartyBranch/{partyBranch}")
    public Object getAllPartyByPartyBranch(@PathVariable("partyBranch") Integer partyBranch){
        System.out.println("stuLogin访问"+ partyBranch + "支部");
        List<Object> result = adminService.getAllPartyByPartyBranch(partyBranch);
        return new HttpResult(result);
    }

    @GetMapping("/getStudentByStuId/{StuId}")
    public Object getStudentByStuId(@PathVariable("StuId") Integer StuId){
        System.out.println("stuLogin 查找学号："+ StuId );
        List<Object> result = adminService.getStudentByStuId(StuId);
        return new HttpResult(result);
    }

}
