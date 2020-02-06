package com.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.entity.ApplyInfo;
import com.springboot.entity.Student;
import com.springboot.service.AdminService;
import com.springboot.service.ApplyInfoService;
import com.springboot.service.StudentService;
import com.springboot.until.httpResult.HttpResult;
import com.springboot.until.httpResult.ResultCode;
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
    @Autowired
    ApplyInfoService applyInfoServiceImpl;

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

    /**
     * 学生上传资料
     * 所有数据都在data字段里
     * */
    @PostMapping("/updateFile")
    public Object addStudent(@RequestParam("data") String data ){
        System.out.println(data);
        /* 从data中读取需要的数据存到applyinfo表 */
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer stuId = jsonObject.getInteger("stuId");
        Integer levelId = jsonObject.getInteger("levelId");
        /* 从data中读取需要的数据存到applyinfo表 */
        String fileName = "";
        if(JSONArray.parseArray(jsonObject.getString("fileName")).size()>0){
            fileName = JSONArray.parseArray(jsonObject.getString("fileName")).get(0).toString();
        }
        // 基本信息到applyInfo数据库表
        ApplyInfo applyInfo = applyInfoServiceImpl.getApplyInfoByStuIdLevelId(stuId,levelId);
        if(applyInfo!=null){
            applyInfo.setFileName(fileName);
            // 返回值为插入更新的申请信息的返回结果
            Integer updateResult = applyInfoServiceImpl.updateApplyInfo(applyInfo);
            // 如果ApplyInfo表更新成功
            if (updateResult > 0) {
                return new HttpResult(1);
            }else{
                return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
            }
        }else{
            return new HttpResult(false, 200,"数据库不存在该学生该等级信息", "error");
        }


    }
}
