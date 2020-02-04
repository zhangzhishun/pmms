package com.springboot.controller;

import com.springboot.entity.Student;
import com.springboot.service.AdminService;
import com.springboot.service.StudentService;
import com.springboot.until.httpResult.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @CrossOrigin 实现跨域访问
 * @author eternalSy
 * @version 1.0.0
 */
@Controller
@CrossOrigin
@ResponseBody
@RequestMapping("student")
public class StudentController {

    @Autowired
    StudentService studentServiceImpl;
    @Autowired
    AdminService adminServiceImpl;

    /**
     * 根据学号获取学生信息
     * */
    @GetMapping("/getStudentByStuId/{StuId}")
    public Object getStudentByStuId(@PathVariable("StuId") Integer stuId){
        System.out.println("学生查找学号："+ stuId );
        List<Object> result = studentServiceImpl.getStuAllInfoByStuId(stuId);
        return new HttpResult(result);
    }

    /**
     * 根据学号查看学生是否缴纳党费
     * */
    @GetMapping("/getPartyDuesByStuId/{StuId}")
    public Object getPartyDuesByStuId(@PathVariable("StuId") Integer stuId){
        System.out.println("学生查找学号："+ stuId );
        Student student = studentServiceImpl.getStudentByStuId(stuId);
        if(student.getStuPartyDues() == 0){
            return new HttpResult(false, 200,"不需要缴纳", "error");
        }else if(student.getStuPartyDues() == 1){
            return new HttpResult(false, 200,"已经缴纳", "error");
        }else if(student.getStuPartyDues() == 2){
            return new HttpResult(false, 200,"未缴纳", "error");
        }else{}
        return new HttpResult(false, 200,"未查到该名学生信息", "error");
    }


    /**
     * 修改用户密码
     * */
    @PostMapping("updateStuPsw")
    public Object updateStuPsw(@RequestParam("stuId") Integer stuId,@RequestParam("prePsw") String prePsw,
                               @RequestParam("newPsw") String newPsw){
        Integer result ;
        Student stu = studentServiceImpl.getStudentByStuId(stuId);
        // 根据学号查找学生如果结果为null
        if(stu == null){
            return new HttpResult(false, 200,"该学生不存在", "error");
        }else{
            // 如果原密码和用户传进来的一致 那么更新student  否则直接返回消息提示
            if(prePsw.equals(stu.getStuPassword())){
                stu.setStuPassword(newPsw);
                System.out.println(stu.toString());
                result = studentServiceImpl.updateStudent(stu);
            }else{
                return new HttpResult(false, 200,"原密码输入错误", "error");
            }
        }
        return new HttpResult(result);
    }
}
