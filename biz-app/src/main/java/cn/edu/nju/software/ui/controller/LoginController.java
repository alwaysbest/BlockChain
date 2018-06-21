package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.bean.OrgType;
import cn.edu.nju.software.ui.bean.SessionKey;
import cn.edu.nju.software.ui.bean.response.UserInfoResponse;
import cn.edu.nju.software.ui.bizservice.UserMgt;
import cn.edu.nju.software.ui.temp.entity.User;
import cn.edu.nju.software.ui.temp.entity.UserType;
import cn.edu.nju.software.ui.temp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Daniel
 * @since 2018/5/13 9:10
 */
@RestController
@RequestMapping("/")
public class LoginController {
    @Autowired
    UserMgt userMgt;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BizResponse<UserType> login(@RequestParam String username,
                                       @RequestParam String password,
                                       HttpSession session) {
        BizResponse<User> user = loginService.login(username, password);
        if (user.getRespStatus().isSuccess()) {
            session.setAttribute(SessionKey.USR, user.getRespData());
        }
        return BizResponse.createSuccess(user.getRespData().getUserType(), "success");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public BizResponse logout(HttpSession session, HttpServletResponse response) {
        session.removeAttribute(SessionKey.USR);
        try {
            response.sendRedirect("/login.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BizResponse.createSuccess(null, "success");

    }

    @RequestMapping(value = "/currentUser", method = RequestMethod.POST)
    public BizResponse<UserInfoResponse> getCurrentUser(HttpSession session) {
        return null;
    }
}
