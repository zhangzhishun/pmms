package com.springboot.service;

import com.springboot.entity.Student;
import com.springboot.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 200 - 请求成功
 * 404 - 请求的资源（网页等）不存在
 * 500 - 内部服务器错误
 * @author eternalSy
 * @version 1.0.0
 */

@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    /** 判断账号密码是否匹配
     * 200：账号密码正确
     * 400：密码错误
     * 404：账号不存在
     * 500：网络异常
     * */
    public Integer login(Integer stuId,String password) {
        try {
            if(password.equals(studentMapper.getStudentById(stuId).getStuPassword())){
                return 200;
            }else{
                return 400;
            }
        }catch (NullPointerException ex){
            // 空指针异常：数据库没有这个用户
            return 404;
        }catch (Exception ex){
            // 其他异常：可能是网络问题等
            return 500;
        }
    }

    /**
     * 根据stuId获取Student对象
     * */
    public Student getStudentById(Integer stuId){
        return studentMapper.getStudentById(stuId);
    }

    /** 更新Student表 */
    public Integer updateStudent(Student newStudent){
        return studentMapper.updateStudent(newStudent);
    }

    /** 删除Student表 */
    public Integer delStudentByStuId(Integer stuId){
        return studentMapper.delStudentByStuId(stuId);
    }
}
