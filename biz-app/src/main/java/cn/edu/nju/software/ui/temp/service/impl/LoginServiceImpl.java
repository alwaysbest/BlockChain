package cn.edu.nju.software.ui.temp.service.impl;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.dao.UserDao;
import cn.edu.nju.software.ui.temp.entity.User;
import cn.edu.nju.software.ui.temp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:yangsanyang
 * Time:2018/5/14 9:17 AM.
 * Illustration:
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final UserDao userDao;
    
    @Autowired
    public LoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
    
    @Override
    public BizResponse<User> login(String userName, String password) {
        User user = userDao.findByUserNameAndPassword(userName, password);
        if (user == null) {
            return BizResponse.createWithoutData(-1, "error");
        }
        return BizResponse.defaultResponse(user);
    }
}
