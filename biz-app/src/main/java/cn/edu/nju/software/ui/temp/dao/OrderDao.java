package cn.edu.nju.software.ui.temp.dao;

import cn.edu.nju.software.ui.temp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author:yangsanyang
 * Time:2018/5/13 5:14 PM.
 * Illustration:
 */
public interface OrderDao extends JpaRepository<Order , Integer>{
    
    Order findByOrderNum(String orderNum);
}
