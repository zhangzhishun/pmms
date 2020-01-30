package com.springboot.service;

import com.springboot.entity.Student;
import com.springboot.mapper.StudentMapper;
import com.springboot.until.httpResult.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 获取指定学生所有信息
     * */
    public List<Object> getStudentByStuId(Integer stuId){
        return studentMapper.getStudentByStuId(stuId);
    }

    /**
     * 根据支部和学生身份筛选学生
     * pb：支部编号
     * level：学生身份等级
     * */
    public List<Object> getStudentByPBAndLevel(Integer pb, Integer level){
        return studentMapper.getStudentByPBAndLevel(pb,level);
    }


    /**
     * 根据stuId获取Student对象
     * */
    public Student getStudentById(Integer stuId){
        Student student ;
        try{
            student = studentMapper.getStudentById(stuId);
        }catch (NullPointerException e){
            return null;
        }
        return student;
    }

    /** 更新Student表 */
    public Integer updateStudent(Student newStudent){
        return studentMapper.updateStudent(newStudent);
    }

    /** 删除Student表 */
    public Integer delStudentByStuId(Integer stuId){
        return studentMapper.delStudentByStuId(stuId);
    }

    /** 添加数据到Student表 */
    public Integer insertStudent(Student student){
        return studentMapper.insertStudent(student);
    }
}
