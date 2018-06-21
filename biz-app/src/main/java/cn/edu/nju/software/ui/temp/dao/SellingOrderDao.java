package cn.edu.nju.software.ui.temp.dao;

import cn.edu.nju.software.ui.temp.entity.SellingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author:yangsanyang
 * Time:2018/5/14 12:37 PM.
 * Illustration:
 */
public interface SellingOrderDao extends JpaRepository<SellingOrder,Integer>{
    
    SellingOrder findByItemIdStringContains(String itemId);
}
