package cn.edu.nju.software.ui.bizservice.impl;

import cn.edu.nju.software.fabricservice.bean.SampleUser;
import cn.edu.nju.software.ui.dao.UserEntityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Daniel
 * @since 2018/5/12 23:58
 */
@Component
public class ServerCache {
    @Autowired
    UserEntityDao userDao;


    public Map<String, SampleUser> users = new ConcurrentHashMap<>();

    public void addUser(String username, SampleUser sampleUser) {
        users.put(username, sampleUser);
    }

    public SampleUser getUser(String username) {
        return users.get(username);
    }
}
