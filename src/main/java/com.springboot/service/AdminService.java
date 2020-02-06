package com.springboot.service;

import com.springboot.entity.Admin;
import com.springboot.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author eternalSy
 * @version 1.0.0
 */

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /** 判断账号密码是否匹配
     * 账号密码正确： 返回值为admLevel
     * 400：密码错误
     * 404：账号不存在
     * 500：网络异常
     * */
    public Integer login(String adminName,String admPassword) {
        try {
            Admin admin = adminMapper.getAdminByName(adminName);
            if(admPassword.equals(admin.getAdmPassword())){
                return 200;
            }else{
                return 400;
            }
        }catch (NullPointerException ex){
            // 空指针异常：数据库没有这个用户
            return 404;
        }catch (Exception ex){
            // 其他异常：可能是网络问题等
            return 500;
        }
    }

    /**
     * 根据adminName获取admin对象
     * */
    public Admin getAdminByName(String admName){
        return adminMapper.getAdminByName(admName);
    }

    /**
     * 获取所有admin
     * */
    public List<Object> getAllParty(){
        return adminMapper.getAllParty();
    }

    /**
     * 获取指定支部所有admin
     * */
    public List<Object> getAllPartyByPartyBranch(Integer partyBranch){
        return adminMapper.getAllPartyByPartyBranch(partyBranch);
    }

    /**
     * 更新Admin
     * */
    public Integer updateAdmin(Admin admin){
        return adminMapper.updateAdmin(admin);
    }


}
