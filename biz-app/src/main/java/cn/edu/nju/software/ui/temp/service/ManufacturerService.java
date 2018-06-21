package cn.edu.nju.software.ui.temp.service;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.entity.Batch;
import cn.edu.nju.software.ui.temp.entity.Dealer;
import cn.edu.nju.software.ui.temp.entity.ItemType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author:yangsanyang
 * Time:2018/5/13 11:33 PM.
 * Illustration:
 */
public interface ManufacturerService {

    /**
     * 获取所有的可选择的item类型与名称，其中其id作为新建批次item时的itemTypeId
     * @return ItemType list
    */
    BizResponse<List<ItemType>> getAllItemTypes();

    /**
     * 添加新出厂的编号和itemId等信息
     * @param organizationId 工厂id
     * @param batchNum 批次编号
     * @param itemIdString itemId组成的String,以","分隔
     * @param itemTypeId 代表item类型与名称的id
     * @return 执行结果
     */
    BizResponse addBatch(int organizationId ,String batchNum , String itemIdString , int itemTypeId);

    /**
     * 获取该工厂生产产品的所有itemId
     * @param organizationId 工厂id
     * @return itemId list
     */
    BizResponse<List<String>> getAllSoldItemIds(int organizationId);
    
    /**
     * 获取该工厂库存的itemType与数量的对应关系
     * @param organizationId 工厂id
     * @return Map<ItemType,Integer>
     */
    BizResponse<Map<ItemType,Integer>> getUnsoldItemTypeAndNumber(int organizationId);

    /**
     * 获取某批次生产产品的所有itemId
     * @param batchNum 批次编号
     * @return itemId list
     */
    BizResponse<List<String>> getCertainBatchItemIds(String batchNum);

    /**
     * 获取该工厂生产产品的所有批次编号
     * @param organizationId 工厂id
     * @return batchNum list
     */
    BizResponse<List<String>> getAllBatchNums(int organizationId);

    /**
     * 获取该工厂生产产品的批次编号与itemId的对应关系
     * @param organizationId 工厂id
     * @return Map<batchNum , List<itemId>>
     */
    BizResponse<Map<String,List<String>>> getBatchAndItemIds(int organizationId);


    /**
     * 在进行批次召回时，获取itemId与订单中所填email的对应关系
     * @param batchNum 批次编号
     * @return Map<itemId,email>
     */
    BizResponse<Map<String,String>> getItemIdAndEmail( String batchNum);
    
    /**
     * 根据批次编号获得产品类别
     * @param batchNum 批次编号
     * @return ItemType
     */
    BizResponse<ItemType> getBatch(String batchNum);
    
    /**
     * 获取所有的经销商信息
     * @return List<Dealer>
     */
    BizResponse<List<Dealer>> getAllDealers();
    
    
    /**
     * 厂家增加销售单
     * @param organizationId 厂家id
     * @param dealerId 经销商id，个人为0
     * @param destination 目的地
     * @param itemIdString itemId的String，以","分隔
     * @email 买家邮箱
     * @return 操作结果
     */
    BizResponse addSellingOrder(int organizationId , int dealerId , String destination , String itemIdString, String email);
}
