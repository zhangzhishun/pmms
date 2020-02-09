package com.springboot.mapper;

import com.springboot.entity.ApplyInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface ApplyInfoMapper {

    /** 根据stuId获取该学生涉及的所有Applyinfo表 */
    List<ApplyInfo> getApplyInfoByStuId(@Param("stuId") Integer stuId);

    /** 更新ApplyInfo表 */
    Integer updateApplyInfo(ApplyInfo applyInfo);

    /** 添加数据到applyInfo表 */
    Integer insertApplyInfo(ApplyInfo applyInfo);

    /** 根据stuId和levelId获取applyinfo表 */
    ApplyInfo getApplyInfoByStuIdLevelId(@Param("stuId") Integer stuId,@Param("levelId") Integer levelId);
}