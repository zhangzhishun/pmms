package com.springboot.service;

import com.springboot.entity.StuClass;
import com.springboot.mapper.StuClassMapper;
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
public class StuClassService {
    @Autowired
    StuClassMapper stuClassMapper;

    public StuClass getStuClassByName(String stuClassName){
        return stuClassMapper.getStuClassByName(stuClassName);
    }
}
