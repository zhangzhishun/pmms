package com.springboot.service;

import com.springboot.entity.Major;
import com.springboot.mapper.MajorMapper;
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
public class MajorService {
    @Autowired
    MajorMapper majorMapper;

    public Major getMajorByName(String majorName) {
        return majorMapper.getMajorByName(majorName);
    }
}
