package cn.edu.nju.software.ui.temp.service.impl;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.dao.DealerItemDao;
import cn.edu.nju.software.ui.temp.dao.DealerItemTypeDao;
import cn.edu.nju.software.ui.temp.entity.DealerItem;
import cn.edu.nju.software.ui.temp.entity.DealerItemType;
import cn.edu.nju.software.ui.temp.entity.Separator;
import cn.edu.nju.software.ui.temp.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author:yangsanyang
 * Time:2018/5/14 9:18 AM.
 * Illustration:
 */
@Service
public class DealerServiceImpl implements DealerService{
    

    private final DealerItemDao dealerItemDao;
    
    private final DealerItemTypeDao dealerItemTypeDao;
    
    @Autowired
    public DealerServiceImpl(DealerItemDao dealerItemDao, DealerItemTypeDao dealerItemTypeDao) {
        this.dealerItemDao = dealerItemDao;
        this.dealerItemTypeDao = dealerItemTypeDao;
    }
    
    @Override
    public BizResponse<List<DealerItemType>> getAllDealerItemTypes() {
        
        return BizResponse.defaultResponse(dealerItemTypeDao.findAll());
    }
    
    @Override
    public BizResponse<Map<DealerItemType , Integer>> getAllDealerItemTypesAndNumber(int organizationId) {
        
        List<DealerItemType> dealerItemTypeList = dealerItemTypeDao.findAll();
        
        Map<Integer , List<DealerItem>> map = dealerItemDao.findAllByDealerId(organizationId).stream().collect(Collectors.groupingBy(DealerItem::getDealerItemTypeId));
        
        Map<DealerItemType , Integer> resultMap = new HashMap<>();
        dealerItemTypeList.forEach(dealerItemType -> {
            int id = dealerItemType.getId();
            resultMap.put(dealerItemType , map.getOrDefault(id , new ArrayList<>()).size());
        });
        
        
        return BizResponse.defaultResponse(resultMap);
    }
    
    
    @Override
    public BizResponse sellItem(int organizationId , String itemIdString ) {
        
        Arrays.asList(itemIdString.split(Separator.SEPARATOR_PETTERN)).forEach(itemId -> dealerItemDao.deleteByDealerIdAndItemId(organizationId, itemId));
        
        return BizResponse.defaultResponse(null);
        
    }
    
    @Override
    public BizResponse addItem(int organizationId, String itemIdString , int dealerItemTypeId) {
    
        
        List<DealerItem> dealerItemList = Arrays.stream(itemIdString.split(Separator.SEPARATOR_PETTERN))
                                          .map(itemId -> new DealerItem(organizationId , itemId , dealerItemTypeId))
                                          .collect(Collectors.toList());
    
        dealerItemDao.saveAll(dealerItemList);
        return BizResponse.defaultResponse(null);
        
    }
}
