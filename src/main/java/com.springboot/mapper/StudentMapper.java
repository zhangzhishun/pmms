package com.springboot.mapper;

import com.springboot.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface StudentMapper {

    /** 根据stuId获取Student表 */
    Student getStudentById(Integer stuId);
    /** 更新Student表 */
    Integer updateStudent(Student newStudent);
    /** 删除Student表 */
    Integer delStudentByStuId(Integer stuId);
}