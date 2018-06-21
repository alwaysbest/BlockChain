package cn.edu.nju.software.ui.temp.dao;

import cn.edu.nju.software.ui.temp.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author:yangsanyang
 * Time:2018/5/13 5:01 PM.
 * Illustration:
 */
public interface BatchDao extends JpaRepository<Batch, Integer>{
    
    Batch findByBatchNum(String batchNum);
    
    List<Batch> findAllByManufacturerId(int manufacturerId);
    
}
