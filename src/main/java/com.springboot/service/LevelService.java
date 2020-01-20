package com.springboot.service;

import com.springboot.entity.Level;
import com.springboot.mapper.LevelMapper;
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
public class LevelService {
    @Autowired
    LevelMapper levelMapper;

    public Level getLevelByName(String levelName) {
        return levelMapper.getLevelByName(levelName);
    }
}
