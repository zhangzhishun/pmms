package com.springboot.mapper;

import com.springboot.entity.Admin;
import com.springboot.entity.Student;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface AdminMapper {

    /** 根据admId获取Student表 */
    Admin getAdmin(Integer admId);
}