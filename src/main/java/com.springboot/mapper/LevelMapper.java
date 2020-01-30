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

    /** 根据levelId获取Level表 */
    Level getLevelById(Integer levelId);

    /** 根据levelId和stuId获取Level表 */
    Level getLevelBylevelIdStuId(Integer levelId,Integer stuId);
}