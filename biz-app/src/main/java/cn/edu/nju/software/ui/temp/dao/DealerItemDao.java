package cn.edu.nju.software.ui.temp.dao;

import cn.edu.nju.software.ui.temp.entity.DealerItem;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Author:yangsanyang
 * Time:2018/5/14 10:16 AM.
 * Illustration:
 */
public interface DealerItemDao extends JpaRepository<DealerItem, Integer> {

    List<DealerItem> findAllByDealerId(int dealerId);

    @Transactional
    void deleteByDealerIdAndItemId(int dealId, String itemId);
}
