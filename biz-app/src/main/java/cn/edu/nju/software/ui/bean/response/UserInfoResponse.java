package cn.edu.nju.software.ui.bean.response;

import cn.edu.nju.software.ui.temp.entity.UserType;
import lombok.Data;

/**
 * @author Daniel
 * @since 2018/5/13 23:11
 */
@Data
public class UserInfoResponse {
    int userId;
    int stationId;
    String username;
    String stationName;
    UserType userType;
}
