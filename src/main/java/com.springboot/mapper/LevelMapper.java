package com.springboot.mapper;

import com.springboot.entity.Level;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface LevelMapper {

    /** 根据levelName获取Level表 */
    Level getLevelByName(String levelName);
}