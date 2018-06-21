package cn.edu.nju.software.ui.temp.service;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.entity.Dealer;
import cn.edu.nju.software.ui.temp.entity.DealerItem;
import cn.edu.nju.software.ui.temp.entity.DealerItemType;

import java.util.List;
import java.util.Map;

/**
 * Author:yangsanyang
 * Time:2018/5/14 12:04 AM.
 * Illustration:
 */
public interface DealerService {
    
    /**
     * 获得所有的dealerItemType
     * @return List<DealerItemType>
     */
    BizResponse<List<DealerItemType>> getAllDealerItemTypes();
    
    /**
     * 获取所有的库存信息
     * @param organizationId 经销商编号
     * @return List<DealerItem>
     */
    BizResponse<Map<DealerItemType , Integer>> getAllDealerItemTypesAndNumber(int organizationId);
    
    /**
     * 销售item
     * @param organizationId 经销商编号
     * @param itemIdString itemId组成的String，以","分隔
     * @return 操作结果
     */
    BizResponse sellItem(int organizationId , String itemIdString );
    
    /**
     * 添加item
     * @param organizationId 经销商编号
     * @param itemIdString itemId组成的String，以","分隔
     * @return 操作结果
     */
    BizResponse addItem(int organizationId , String itemIdString , int dealerItemTypeId);
    

}
