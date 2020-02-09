package com.springboot.mapper;

import com.springboot.entity.Level;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface LevelMapper {

    /** 根据levelName获取Level表 */
    Level getLevelByName(@Param("levelName") String levelName);

    /** 根据levelId获取Level表 */
    Level getLevelById(@Param("levelId") Integer levelId);

    /** 根据levelId和stuId获取Level表 */
    Level getLevelBylevelIdStuId(@Param("levelId") Integer levelId,@Param("stuId") Integer stuId);
}