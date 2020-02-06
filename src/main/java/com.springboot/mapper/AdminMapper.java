package com.springboot.mapper;

import com.springboot.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author eternalSy
 * @version 1.0.0
 */
@Repository
public interface AdminMapper {

    /**
     * 根据admId获取Admin表
     * @param  admId 管理员ID
     * @return  Admin
     * */
    Admin getAdminById(Integer admId);

    /** 根据admName获取Admin表 */
    Admin getAdminByName(String admName);

    /** 获取所有党员信息 */
    List<Object> getAllParty();

    /** 获取指定支部所有党员信息 */
    List<Object> getAllPartyByPartyBranch(Integer partyBranch);

    /** 更新Admin */
    Integer updateAdmin(Admin admin);



}