package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.TracingItemInfo;
import cn.edu.nju.software.common.pojo.bizservice.request.*;
import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.fabricservice.serviceinvoker.ServiceInvoker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel
 * @since 2018/5/16 21:28
 */
@RestController
@Api("溯源系统信息平台api")
@RequestMapping("/api")
public class APIController {
    @Autowired
    ServiceInvoker serviceInvoker;

    @RequestMapping(value = "/itemGet", method = RequestMethod.POST)
    @ApiOperation(value = "获得商品状态信息")
    public BizResponse<TracingItemInfo> getItemInfo(UIItemGetRequest uiItemGetRequest) {
        return null;
    }

    @RequestMapping(value = "/itemAdd", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品信息")
    public BizResponse addItemInfo(UIItemAddRequest uiItemAddRequest) {
        return null;
    }

    @RequestMapping(value = "/itemOrder", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品销售订单")
    public BizResponse itemOrder(UIItemOrderRequest uiItemOrderRequest) {
        return null;
    }

    @RequestMapping(value = "/itemInStock", method = RequestMethod.POST)
    @ApiOperation(value = "商品入库")
    public BizResponse itemInStock(UIItemInStockRequest itemInStockRequest) {
        return null;
    }

    @RequestMapping(value = "/logisticsOrder", method = RequestMethod.POST)
    @ApiOperation(value = "新增商品物流订单")
    public BizResponse logisticsOrder(UILogisticsOrderRequest uiLogisticsOrderRequest) {
        return null;
    }

    @RequestMapping(value = "/itemDeparture", method = RequestMethod.POST)
    @ApiOperation(value = "商品出发")
    public BizResponse itemDeparture(UIItemDepartureRequest itemDepartureRequest) {
        return null;
    }

    @RequestMapping(value = "/itemArrive", method = RequestMethod.POST)
    @ApiOperation(value = "商品到达")
    public BizResponse itemArrive(UIItemArriveRequest uiItemArriveRequest) {
        return null;
    }

    @RequestMapping(value = "/itemSign", method = RequestMethod.POST)
    @ApiOperation(value = "商品签收")
    public BizResponse itemSign(UIItemSignRequest uiItemSignRequest) {
        return null;
    }
}
