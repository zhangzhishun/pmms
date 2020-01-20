package com.springboot.mapper;

import com.springboot.entity.Admin;
import com.springboot.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface AdminMapper {

    /** 根据admId获取Admin表 */
    Admin getAdminById(Integer admId);

    /** 根据admName获取Admin表 */
    Admin getAdminByName(String admName);

    /** 获取所有党员信息 */
    List<Object> getAllParty();

    /** 获取指定支部所有党员信息 */
    List<Object> getAllPartyByPartyBranch(Integer partyBranch);

    /** 获取指定支部所有党员信息 */
    List<Object> getStudentByStuId(Integer stuId);

    /**
     * 根据支部和学生身份筛选学生
     * pb：支部编号
     * level：学生身份等级
     * */
    List<Object> getStudentByPBAndLevel(Integer pb, Integer level);
}