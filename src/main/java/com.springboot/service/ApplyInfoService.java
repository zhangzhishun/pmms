package com.springboot.service;

import com.springboot.entity.ApplyInfo;
import com.springboot.mapper.ApplyInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 200 - 请求成功
 * 404 - 请求的资源（网页等）不存在
 * 500 - 内部服务器错误
 * @author eternalSy
 * @version 1.0.0
 */

@Service
public class ApplyInfoService {
    @Autowired
    ApplyInfoMapper applyInfoMapperImpl;

    public List<ApplyInfo> getApplyInfoByStuId(Integer stuId){
        return applyInfoMapperImpl.getApplyInfoByStuId(stuId);
    }

    /** 更新ApplyInfo表 */
    public Integer updateApplyInfo(ApplyInfo applyInfo){
        return applyInfoMapperImpl.updateApplyInfo(applyInfo);
    }
}
