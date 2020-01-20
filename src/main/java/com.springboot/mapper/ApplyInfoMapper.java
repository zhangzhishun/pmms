package com.springboot.mapper;

import com.springboot.entity.ApplyInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface ApplyInfoMapper {

    /** 根据stuId获取该学生涉及的所有Applyinfo表 */
    List<ApplyInfo> getApplyInfoByStuId(Integer stuId);

    /** 更新ApplyInfo表 */
    Integer updateApplyInfo(ApplyInfo applyInfo);
}