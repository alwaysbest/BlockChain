package cn.edu.nju.software.ui.temp.service;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.entity.Dealer;
import cn.edu.nju.software.ui.temp.entity.LogisticsSite;
import cn.edu.nju.software.ui.temp.entity.Order;

import java.util.Date;
import java.util.List;

/**
 * Author:yangsanyang
 * Time:2018/5/14 12:03 AM.
 * Illustration:
 */
public interface LogisticsService {
    
    /**
     * 获取所有的物流站点信息
     * @param organizationId 需要去除该站点的id
     * @return List<LogisticsSite>
     */
    BizResponse<List<LogisticsSite>> getAllLogisticsSite(int organizationId);


    BizResponse<List<String>> getAllItemIds(List<String> orderNums);

    
    /**
     * 添加一条订单
     * @param organizationId 物流站点id
     * @param destination 目的地
     * @param itemIdString itemId组成的String,用","分割
     * @param description 订单的描述
     * @return 添加结果
     */
    BizResponse addOrder(int organizationId , String destination  , String itemIdString , String description );
    
    /**
     *
     * @param orderNum 订单编号
     * @param organizationId 本站点id
     * @return 处理结果
     */
    BizResponse receiveOrder(String orderNum , int organizationId);
    
    /**
     *
     * @param orderNum 订单编号
     * @param organizationId 本站点id
     * @param nextLogisticsSiteId 下一个站点id
     * @return
     */
    BizResponse transitOrder(String orderNum , int organizationId ,int nextLogisticsSiteId);
    
    /**
     * 签收订单
     * @param orderNum 订单号
     * @param organizationId 站点id
     * @return 操作结果
     */
    BizResponse signOrder(String orderNum , int organizationId);
    
    
    /**
     * 获得要到达该站点的订单信息
     * @param organizationId 站点id
     * @return List<Order>
     */
    BizResponse<List<Order>> getToArrive(int organizationId);
    
    /**
     * 获得要触发的订单信息
     * @param organizationId 站点id
     * @return List<Order>
     */
    BizResponse<List<Order>> getToDeparture(int organizationId);
    
    /**
     * 根据id找到logisticsSite
     * @param id id
     * @return LogisticsSite
     */
    BizResponse<LogisticsSite> getLogisticsSite(int id);
}
