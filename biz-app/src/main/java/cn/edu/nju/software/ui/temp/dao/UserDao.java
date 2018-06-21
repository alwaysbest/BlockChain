package cn.edu.nju.software.ui.temp.dao;

import cn.edu.nju.software.ui.temp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author:yangsanyang
 * Time:2018/5/14 12:34 AM.
 * Illustration:
 */
public interface UserDao extends JpaRepository<User,Integer>{
    
    User findByUserNameAndPassword(String userName , String password);
}
