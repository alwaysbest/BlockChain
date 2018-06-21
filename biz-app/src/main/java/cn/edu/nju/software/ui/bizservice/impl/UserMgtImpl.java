package cn.edu.nju.software.ui.bizservice.impl;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.fabricservice.serviceinvoker.HFClientHelper;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import cn.edu.nju.software.ui.bizservice.UserMgt;
import cn.edu.nju.software.ui.dao.UserEntityDao;
import cn.edu.nju.software.ui.entity.UserEntity;
import org.hyperledger.fabric.sdk.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Daniel
 * @since 2018/5/12 20:30
 */
@Service
public class UserMgtImpl implements UserMgt {
    @Autowired
    ServiceInvoker serviceInvoker;

    @Autowired
    UserEntityDao userDao;

    @Autowired
    ServerCache serverCache;

    @Override
    public BizResponse<String> userRegister(String username) {
        HFClientHelper helper = serviceInvoker.getHfClientHelper();
        String password = helper.registerUser(username, "org1.department1");
        Enrollment enrollment = helper.enroll(username, password);
        if (enrollment == null) {
            return BizResponse.createWithoutData(-1, "create error");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(password);
        userEntity.setEnrollKey(enrollment.getKey().getEncoded());
        userEntity.setEnrollCert(enrollment.getCert());
        userDao.save(userEntity);
        return BizResponse.createSuccess(password, "create success");
    }

    @Override
    public BizResponse check(String username, String password) {
        UserEntity entity = userDao.findByUsername(username);
        if (entity == null || !entity.getPassword().equals(password))
            return BizResponse.createWithoutData(-1, "usr not exists or password wrong!");
        return BizResponse.createSuccess(null, "success");
    }

}
