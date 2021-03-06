package com.springboot.mapper;

import com.springboot.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface StudentMapper {

    /** 根据stuId获取Student表 */
    Student getStudentById(@Param("stuId") Integer stuId);
    /** 更新Student表 */
    Integer updateStudent(Student newStudent);
    /** 删除Student表 */
    Integer delStudentByStuId(@Param("stuId") Integer stuId);
    /** 添加数据到Student表 */
    Integer insertStudent(Student student);
    /** 获取指定支部所有党员信息 */
    List<Object> getStudentByStuId(@Param("stuId") Integer stuId);

    /**
     * 根据支部和学生身份筛选学生
     * pb：支部编号
     * level：学生身份等级
     * */
    List<Object> getStudentByPBAndLevel(@Param("pb") Integer pb, @Param("level") Integer level);

}