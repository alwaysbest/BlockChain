package cn.edu.nju.software.ui.temp.service;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.entity.User;

/**
 * Author:yangsanyang
 * Time:2018/5/13 11:12 PM.
 * Illustration:
 */
public interface LoginService {
    
    /**
     * 登录验证
     * @param userName 用户名
     * @param password 密码
     * @return user信息，user为null代表密码错误
     */
    BizResponse<User> login (String userName , String password);
}
