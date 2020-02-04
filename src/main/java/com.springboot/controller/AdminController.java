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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /** 文件保存路径 */
    String filePathHead = "E:\\file\\study\\Vue\\pmms-vue\\pmms\\src\\assets\\upload\\";
    String filePathFile = "F:\\pmms\\";
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
    public Object getStudentByStuId(@PathVariable("StuId") Integer stuId){
        System.out.println("stuLogin 查找学号："+ stuId );
        List<Object> result = studentServiceImpl.getStuAllInfoByStuId(stuId);
        return new HttpResult(result);
    }

    /**
     * 根据支部编号和级别获取满足条件的所有学生
     * */
    @PostMapping("/getStudentByPBAndLevel")
    public Object getStudentByPBAndLevel(@RequestParam("pb") Integer pb,@RequestParam("level") Integer level){
        System.out.println("getStudentByPBAndLevel 查找："+ pb + " " + level);
        List<Object> result = studentServiceImpl.getStudentByPBAndLevel(pb,level);
        return new HttpResult(result);
    }

    @GetMapping("/getApplyInfoByStuId/{StuId}")
    public Object getApplyInfoByStuId(@PathVariable("StuId") Integer stuId){
        System.out.println("stuLogin 查找学号："+ stuId );
        List<ApplyInfo> result = applyInfoServiceImpl.getApplyInfoByStuId(stuId);
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
        Student newStudent = studentServiceImpl.getStudentByStuId(stuId);
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
        if (!stuClass.getStuClassId().equals(newStudent.getStuId())) {
            newStudent.setStuClassId(stuClass.getStuClassId());
        }
        if (newStudent.getStuContactInformation() != stuContactInformation) {
            newStudent.setStuContactInformation(stuContactInformation);
        }

        /** 更新每个阶段的时间 */
        // 获取到的ApplyInfo顺序和前端传过来的applyInfo顺序一致  都是按着applyId排序的  所以如果时间改变直接设置时间就可
        List<ApplyInfo> applyInfo = applyInfoServiceImpl.getApplyInfoByStuId(stuId);
        JSONArray jsonArrayApplyTime = JSONArray.parseArray(jsonObject.getString("applyTime"));
        JSONArray jsonArrayApplyFile = JSONArray.parseArray(jsonObject.getString("fileName"));
        ApplyInfo temp;
        Integer updateApplyInfoResult = 1;
        for (int i = 0; i < jsonArrayApplyTime.size(); i++) {
            temp = applyInfo.get(i);
            // 因为后台传过来的时间格式是：2020-01-01 21:23:19.0  所以需要转换
            String time = temp.getApplyTime().substring(0, temp.getApplyTime().indexOf("."));
            // 如果用户传过来的数据和数据库中的数据不一致那么进行更改
            if(!time.equals(jsonArrayApplyTime.getString(i))){
                temp.setApplyTime(DealDate.dealDateFormat(jsonArrayApplyTime.getString(i)));
            }
            System.out.println("TEMP:::" + temp.getFileName());
            // 如果用户传过来的数据和数据库中的数据不一致那么进行更改
            if(!temp.getFileName().equals(jsonArrayApplyFile.getString(i))){
                temp.setFileName(jsonArrayApplyFile.getString(i));
            }
            // 如果更新ApplyInfo表成功那么继续  否则直接返回错误
            if(applyInfoServiceImpl.updateApplyInfo(temp) > 0){
                continue;
            }else{
                return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
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
     * 添加学生记录和基本信息
     * 所有数据都在data字段里
     * */
    @PostMapping("/addStudent")
    public Object addStudent(@RequestParam("data") String data){
        System.out.println(data);
        /* 从data中读取需要的数据存到student表 */
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer stuId = jsonObject.getInteger("stuId");
        String stuPassword = jsonObject.getString("stuPassword");
        String stuName = jsonObject.getString("stuName");
        String stuSex = jsonObject.getString("stuSex");
        String stuPhoto = jsonObject.getString("stuPhoto");
        String stuOriginPlace = jsonObject.getString("stuOriginPlace");
        Integer stuClassId = stuClassServiceImpl.getStuClassByName(jsonObject.getString("stuClassName")).getStuClassId();
        String stuContactInformation = jsonObject.getString("stuContactInformation");
        String stuAddress = jsonObject.getString("stuAddress");
        /* 从data中读取需要的数据存到student表 */
        String fileName = JSONArray.parseArray(jsonObject.getString("fileName")).get(0).toString();

        if(studentServiceImpl.getStudentByStuId(stuId) != null){
            return new HttpResult(false, 200,"用户已存在", "error");
        }
        // 添加学生基本信息到student数据库表
        Student insertStu = new Student(stuId,stuPassword,stuName,stuSex,stuPhoto,stuOriginPlace,stuClassId,stuContactInformation,stuAddress,2);
        // 返回值为插入学生的学号
        Integer insertStuId = studentServiceImpl.insertStudent(insertStu);
        // 如果Student表插入成功 继续向applyInfo表插入数据
        if (insertStuId > 0) {
            ApplyInfo applyInfo = new ApplyInfo(null,stuId,null,1,1,fileName);
            Integer result = applyInfoServiceImpl.insertApplyInfo(applyInfo);
            if(result > 0){
                return new HttpResult(1);
            }else{
                return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
            }
        }else{
            return new HttpResult(ResultCode.GATEWAY_TIMEOUT);
        }
    }

    /**
     * 添加积极分子及以上
     * */
    @PostMapping("addApplyInfo")
    public Object addApplyInfo(@RequestParam("data") String data) {
        System.out.println(data);
        /* 从data中读取需要的数据 然后存到applyinfo表 */
        JSONObject jsonObject = JSONObject.parseObject(data);
        Integer stuId = jsonObject.getInteger("stuId");
        String stuName = jsonObject.getString("stuName");
        Integer levelId = jsonObject.getInteger("levelId");
        String fileName = JSONArray.parseArray(jsonObject.getString("fileName")).get(0).toString();
        Student student = studentServiceImpl.getStudentByStuId(stuId);
        // 如果用户输入的学生名和学号相匹配
        if(student != null && stuName.equals(student.getStuName())){
            // 如果该学生对应等级的申请已经提交 那么输出提示  否则插入新数据
            ApplyInfo applyInfo = applyInfoServiceImpl.getApplyInfoByStuIdLevelId(stuId,levelId);
            if(applyInfo != null){
                return new HttpResult(false, 200,"该学生已经通过"+
                        levelServiceImpl.getLevelById(levelId).getLevelName() +"身份", "error");
            }else{
                // 默认申请通过
                applyInfoServiceImpl.insertApplyInfo(new ApplyInfo(null,stuId,null,levelId,1,fileName));
                return new HttpResult(1);
            }
        }else {
            // 如果 stuName.equals(studentServiceImpl.getStudentByStuId(stuId) 报空指针异常代表数据库中没有该stuId
            return new HttpResult(false, 200,"学号和名字不匹配", "error");
        }
    }

    /**
     * 接收图片和文件
     * */
    @PostMapping("uploadHeadFile")
    public Object uploadHeadFile(MultipartFile file) {
        System.out.println("获取到图片或文字");
        String fileName[] = file.getOriginalFilename().split("\\.");
        String uploadName = FileUtils.createtFileName() + "." +fileName[1];
        System.out.println("uploadName" + uploadName);
        try {
            if("jpeg".equals(fileName[1]) || "jpg".equals(fileName[1]) || "png".equals(fileName[1])){
                FileUtils.upload(file.getBytes(), filePathHead,uploadName);
            }else{
                FileUtils.upload(file.getBytes(), filePathFile,uploadName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpResult(uploadName);
    }

    @GetMapping("/delStudentByStuId/{StuId}")
    public Object delStudentByStuId(@PathVariable("StuId") Integer stuId){
        System.out.println("stuLogin 查找学号："+ stuId );
        Integer result = studentServiceImpl.delStudentByStuId(stuId);
        return new HttpResult(result);
    }

}
