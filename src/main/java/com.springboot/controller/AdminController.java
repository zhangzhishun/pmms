package com.springboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.springboot.entity.*;
import com.springboot.service.*;
import com.springboot.until.DealDate;
import com.springboot.until.FileUtils;
import com.springboot.until.httpResult.HttpResult;
import com.springboot.until.httpResult.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
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
    AdminService adminServiceImpl;
    @Autowired
    LevelService levelServiceImpl;
    @Autowired
    MajorService majorServiceImpl;
    @Autowired
    StuClassService stuClassServiceImpl;
    @Autowired
    StudentService studentServiceImpl;
    @Autowired
    ApplyInfoService applyInfoServiceImpl;

    /**
     * 根据支部获取支部内所有党员信息
     * */
    @GetMapping("/getAllPartyByPartyBranch/{partyBranch}")
    public Object getAllPartyByPartyBranch(@PathVariable("partyBranch") Integer partyBranch){
        System.out.println("stuLogin访问"+ partyBranch + "支部");
        List<Object> result = adminServiceImpl.getAllPartyByPartyBranch(partyBranch);
        return new HttpResult(result);
    }

    /**
     * 根据学号获取学生信息
     * */
    @GetMapping("/getStudentByStuId/{StuId}")
    public Object getStudentByStuId(@PathVariable("StuId") Integer StuId){
        System.out.println("stuLogin 查找学号："+ StuId );
        List<Object> result = adminServiceImpl.getStudentByStuId(StuId);
        return new HttpResult(result);
    }

    /**
     * 根据支部编号和级别获取满足条件的所有学生
     * */
    @PostMapping("/getStudentByPBAndLevel")
    public Object getStudentByPBAndLevel(@RequestParam("pb") Integer pb,@RequestParam("level") Integer level){
        System.out.println("getStudentByPBAndLevel 查找："+ pb + " " + level);
        List<Object> result = adminServiceImpl.getStudentByPBAndLevel(pb,level);
        return new HttpResult(result);
    }

    @GetMapping("/getApplyInfoByStuId/{StuId}")
    public Object getApplyInfoByStuId(@PathVariable("StuId") Integer StuId){
        System.out.println("stuLogin 查找学号："+ StuId );
        List<ApplyInfo> result = applyInfoServiceImpl.getApplyInfoByStuId(StuId);
        return new HttpResult(result);
    }

    /**
     * 更新学生基本信息
     * 所有数据都在data字段里
     * */
    @PostMapping("/updateStudent")
    public Object updateStudent(@RequestParam("data") String data){
        System.out.println(data);
        /* 从data中读取需要的数据 */
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer stuId = jsonObject.getInteger("stuId");
        String stuSex = jsonObject.getString("stuSex");
        String stuClassName = jsonObject.getString("stuClassName");
        String stuName = jsonObject.getString("stuName");
        String stuOriginPlace = jsonObject.getString("stuOriginPlace");
        String stuContactInformation = jsonObject.getString("stuContactInformation");
        System.out.println("updateStudent 查找：" + jsonObject.get("stuId"));

        // 传入的学号对应的学生信息
        Student newStudent = studentServiceImpl.getStudentById(stuId);
        // 传入的班级名对应的班级信息
        StuClass stuClass = stuClassServiceImpl.getStuClassByName(stuClassName);

        // 如果用户传入的字段和数据库中的字段数据不匹配 那么重新设置新的数据
        if (newStudent.getStuName() != stuName) {
            newStudent.setStuName(stuName);
        }
        if (newStudent.getStuSex() != stuSex) {
            newStudent.setStuSex(stuSex);
        }
        if (newStudent.getStuOriginPlace() != stuOriginPlace) {
            newStudent.setStuOriginPlace(stuOriginPlace);
        }
        if (stuClass.getStuClassId() != newStudent.getStuId()) {
            newStudent.setStuClassId(stuClass.getStuClassId());
        }
        if (newStudent.getStuContactInformation() != stuContactInformation) {
            newStudent.setStuContactInformation(stuContactInformation);
        }

        /** 更新每个阶段的时间 */
        // 获取到的ApplyInfo顺序和前端传过来的applyInfo顺序一致  都是按着applyId排序的  所以如果时间改变直接设置时间就可
        List<ApplyInfo> applyInfo = applyInfoServiceImpl.getApplyInfoByStuId(stuId);
        JSONArray jsonArrayApplyTime = JSONArray.parseArray(jsonObject.getString("applyTime"));
        ApplyInfo temp;
        Integer updateApplyInfoResult = 1;
        for (int i = 0; i < jsonArrayApplyTime.size(); i++) {
            temp = applyInfo.get(i);
            // 因为后台传过来的时间格式是：2020-01-01 21:23:19.0  所以需要转换
            String time = temp.getApplyTime().substring(0, temp.getApplyTime().indexOf("."));
            // 如果用户传过来的数据和数据库中的数据不一致那么进行更改
            if(!time.equals(jsonArrayApplyTime.getString(i))){
                // 先获取后台传过来的levelId然后付
                temp.setApplyTime(DealDate.dealDateFormat(jsonArrayApplyTime.getString(i)));
                // 如果更新ApplyInfo表成功那么继续  否则直接返回错误
                if(applyInfoServiceImpl.updateApplyInfo(temp) > 0){
                   continue;
                }else{
                    return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
                }
            }
        }
        /** 如果学生基本信息和申请信息全部更新成功 返回成功提示（data字段返回值为1）*/
        if(updateApplyInfoResult>0 && studentServiceImpl.updateStudent(newStudent)>0){
            return new HttpResult(1);
        }else{
            return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
        }
    }

    /**
     * 更新学生基本信息
     * 所有数据都在data字段里
     * */
    @PostMapping("/addStudent")
    public Object addStudent(@RequestParam("data") String data){
        System.out.println(data);
        /* 从data中读取需要的数据 */
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer stuId = jsonObject.getInteger("stuId");
        String stuSex = jsonObject.getString("stuSex");
        String stuClassName = jsonObject.getString("stuClassName");
        String stuName = jsonObject.getString("stuName");
        String stuOriginPlace = jsonObject.getString("stuOriginPlace");
        String stuContactInformation = jsonObject.getString("stuContactInformation");
        System.out.println("updateStudent 查找：" + jsonObject.get("stuId"));

        // 传入的学号对应的学生信息
        Student newStudent = studentServiceImpl.getStudentById(stuId);
        // 传入的班级名对应的班级信息
        StuClass stuClass = stuClassServiceImpl.getStuClassByName(stuClassName);

        // 如果用户传入的字段和数据库中的字段数据不匹配 那么重新设置新的数据
        if (newStudent.getStuName() != stuName) {
            newStudent.setStuName(stuName);
        }
        if (newStudent.getStuSex() != stuSex) {
            newStudent.setStuSex(stuSex);
        }
        if (newStudent.getStuOriginPlace() != stuOriginPlace) {
            newStudent.setStuOriginPlace(stuOriginPlace);
        }
        if (stuClass.getStuClassId() != newStudent.getStuId()) {
            newStudent.setStuClassId(stuClass.getStuClassId());
        }
        if (newStudent.getStuContactInformation() != stuContactInformation) {
            newStudent.setStuContactInformation(stuContactInformation);
        }

        /** 更新每个阶段的时间 */
        // 获取到的ApplyInfo顺序和前端传过来的applyInfo顺序一致  都是按着applyId排序的  所以如果时间改变直接设置时间就可
        List<ApplyInfo> applyInfo = applyInfoServiceImpl.getApplyInfoByStuId(stuId);
        JSONArray jsonArrayApplyTime = JSONArray.parseArray(jsonObject.getString("applyTime"));
        ApplyInfo temp;
        Integer updateApplyInfoResult = 1;
        for (int i = 0; i < jsonArrayApplyTime.size(); i++) {
            temp = applyInfo.get(i);
            // 因为后台传过来的时间格式是：2020-01-01 21:23:19.0  所以需要转换
            String time = temp.getApplyTime().substring(0, temp.getApplyTime().indexOf("."));
            // 如果用户传过来的数据和数据库中的数据不一致那么进行更改
            if(!time.equals(jsonArrayApplyTime.getString(i))){
                // 先获取后台传过来的levelId然后付
                temp.setApplyTime(DealDate.dealDateFormat(jsonArrayApplyTime.getString(i)));
                // 如果更新ApplyInfo表成功那么继续  否则直接返回错误
                if(applyInfoServiceImpl.updateApplyInfo(temp) > 0){
                    continue;
                }else{
                    return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
                }
            }
        }
        /** 如果学生基本信息和申请信息全部更新成功 返回成功提示（data字段返回值为1）*/
        if(updateApplyInfoResult>0 && studentServiceImpl.updateStudent(newStudent)>0){
            return new HttpResult(1);
        }else{
            return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
        }
    }

    @PostMapping("uploadHeadFile")
    public Object uploadHeadFile(MultipartFile file) {
        System.out.println("获取到图片或文字");
        String fileName[] = file.getOriginalFilename().split("\\.");
        String filePath = "F:\\pmms\\";
        String uploadName = FileUtils.createtFileName(fileName[0])+"."+fileName[1];
        try {
            FileUtils.upload(file.getBytes(), filePath,uploadName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpResult(uploadName);
    }

    @GetMapping("/delStudentByStuId/{StuId}")
    public Object delStudentByStuId(@PathVariable("StuId") Integer StuId){
        System.out.println("stuLogin 查找学号："+ StuId );
        Integer result = studentServiceImpl.delStudentByStuId(StuId);
        return new HttpResult(result);
    }

}
