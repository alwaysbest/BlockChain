package cn.edu.nju.software.ui.bizservice.impl;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.bizservice.IdentityAuthority;
import org.springframework.stereotype.Service;

/**
 * @author Daniel
 * @since 2018/5/2 17:02
 */
@Service
public class IdentityAuthorityImpl implements IdentityAuthority {
    @Override
    public BizResponse verifyAuthorityCode(String authorityCode) {
        return null;
    }

    @Override
    public BizResponse<String> generateAuthorityCode(String username, String password) {
        return null;
    }
}
