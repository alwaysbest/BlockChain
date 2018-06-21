package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import cn.edu.nju.software.ui.bizservice.UserMgt;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Daniel
 * @since 2018/5/2 17:01
 */
@RestController
@RequestMapping("/usr")
public class UserController {
    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    UserMgt userMgt;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "")
    public BizResponse<String> register(
            @ApiParam(value = "用户名", required = true)
            @RequestParam String username) {
        return userMgt.userRegister(username);
    }

}
