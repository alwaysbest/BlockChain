package cn.edu.nju.software.ui.bizservice;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import org.springframework.stereotype.Service;

/**
 * 业务层的用户权限验证
 */
@Service
public interface IdentityAuthority {
    /**
     * 验证用户的授权码是否正确
     *
     * @param authorityCode
     * @return
     */
    BizResponse verifyAuthorityCode(String authorityCode);

    BizResponse<String> generateAuthorityCode(String username, String password);
}
