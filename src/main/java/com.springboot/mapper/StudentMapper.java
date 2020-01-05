package com.springboot.mapper;

import com.springboot.entity.Stuclass;
import com.springboot.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface StudentMapper {

    /** 根据stuId获取Student表 */
    Student getStudent(Integer stuId);
}