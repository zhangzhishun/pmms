package com.springboot.mapper;

import com.springboot.entity.StuClass;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface StuClassMapper {

    /** 根据stuId获取Student表 */
    StuClass getStuClassByName(String stuClassName);
}