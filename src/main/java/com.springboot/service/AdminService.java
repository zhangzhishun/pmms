package com.springboot.service;

import com.springboot.entity.Stuclass;
import com.springboot.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author eternalSy
 * @version 1.0.0
 */

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;

    /** 判断账号密码是否匹配
     * 200：账号密码正确
     * 400：密码错误
     * 404：账号不存在
     * 500：网络异常
     * */
    public Integer login(Integer admId,String admPassword) {
        try {
            if(admPassword.equals(adminMapper.getAdmin(admId).getAdmPassword())){
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
}
