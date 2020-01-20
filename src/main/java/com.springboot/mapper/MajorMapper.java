package com.springboot.mapper;

import com.springboot.entity.Major;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface MajorMapper {

    /** 根据stuId获取Student表 */
    Major getMajorByName(String majorName);
}