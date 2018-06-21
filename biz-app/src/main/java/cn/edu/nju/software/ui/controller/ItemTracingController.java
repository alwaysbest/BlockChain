package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.OpType;
import cn.edu.nju.software.common.pojo.TracingItemInfo;
import cn.edu.nju.software.common.pojo.bizservice.request.*;
import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.bizservice.ItemTracingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Daniel
 * @since 2018/5/2 15:21
 */
@RestController
@Api("溯源信息api")
public class ItemTracingController {

    @Autowired
    ItemTracingService itemTracingService;

    @RequestMapping(value = "/item", method = RequestMethod.POST)
    @ApiOperation(value = "获得当前的商品状态信息")
    public BizResponse<List<TracingItemInfo>> getTracingItemInfo(
            @ApiParam(value = "获得商品信息", required = true)
            @RequestBody UIItemGetRequest request) {
        return itemTracingService.getTracingItemInfo(request);
    }

    @RequestMapping(value = "/item/add", method = RequestMethod.POST)
    @ApiOperation(value = "增加一件商品")
    public BizResponse getTracingItemInfo(
            @ApiParam(value = "增加的商品的信息", required = true)
            @RequestBody UIItemAddRequest request) {
        return itemTracingService.addItemTracingInfo(request);
    }

    @RequestMapping(value = "/item/change", method = RequestMethod.POST)
    @ApiOperation(value = "改变一件商品信息")
    public BizResponse changeTracingItemInfo(
            @ApiParam(value = "改变商品的信息,opType取值为0,1,2分别代表创建、物流、送达", required = true)
            @RequestBody UIItemChangeRequest request) {
        return itemTracingService.changeItemTracingInfo(request);
    }

    @RequestMapping(value = "/item/itemTransfer", method = RequestMethod.POST)
    @ApiOperation(value = "商品转交,如将商品从商家转交给经销商/代理商")
    public BizResponse itemTransfer(
            @ApiParam(value = "转交的商品信息", required = true)
            @RequestBody UIItemTransferRequest request) {
        UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
        uiItemChangeRequest.setEnvStatus(request.getEnvStatus());
        uiItemChangeRequest.setItemStatus(request.getItemStatus());
        uiItemChangeRequest.setItemId(request.getItemId());
        uiItemChangeRequest.setNextOrg(request.getNextOrg());
        uiItemChangeRequest.setExtraInfo(request.getDiliveryOrg());
        return itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
    }

    @RequestMapping(value = "/item/inStock", method = RequestMethod.POST)
    @ApiOperation(value = "商品入库")
    public BizResponse itemTransfer(
            @ApiParam(value = "入库信息", required = true)
            @RequestBody UIItemInStockRequest request) {
        UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
        uiItemChangeRequest.setEnvStatus(request.getEnvStatus());
        uiItemChangeRequest.setItemStatus(request.getItemStatus());
        uiItemChangeRequest.setItemId(request.getItemId());
        uiItemChangeRequest.setOpType(OpType.INSTOCK.ordinal());
        uiItemChangeRequest.setNextOrg(request.getNextOrg());
        uiItemChangeRequest.setExtraInfo(request.getStockInfo());
        return itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
    }

    @RequestMapping(value = "/item/outStock", method = RequestMethod.POST)
    @ApiOperation(value = "商品出库")
    public BizResponse itemTransfer(
            @ApiParam(value = "出库信息", required = true)
            @RequestBody UIItemOutStockRequest request) {
        UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
        uiItemChangeRequest.setEnvStatus(request.getEnvStatus());
        uiItemChangeRequest.setItemStatus(request.getItemStatus());
        uiItemChangeRequest.setItemId(request.getItemId());
        uiItemChangeRequest.setNextOrg(request.getNextOrg());
        uiItemChangeRequest.setExtraInfo(request.getStockInfo());
        return itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
    }

    @RequestMapping(value = "/item/media", method = RequestMethod.POST)
    @ApiOperation(value = "商品中转")
    public BizResponse itemTransfer(
            @ApiParam(value = "中转信息", required = true)
            @RequestBody UIItemMediaRequest request) {
        UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
        uiItemChangeRequest.setEnvStatus(request.getEnvStatus());
        uiItemChangeRequest.setItemStatus(request.getItemStatus());
        uiItemChangeRequest.setItemId(request.getItemId());
        uiItemChangeRequest.setNextOrg(request.getNextOrg());
        uiItemChangeRequest.setExtraInfo(request.getMediaInfo());
        return itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
    }

    @RequestMapping(value = "/item/delivery", method = RequestMethod.POST)
    @ApiOperation(value = "商品签收")
    public BizResponse itemTransfer(
            @ApiParam(value = "签收信息", required = true)
            @RequestBody UIItemDeliveryRequest request) {
        UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
        uiItemChangeRequest.setEnvStatus(request.getEnvStatus());
        uiItemChangeRequest.setItemStatus(request.getItemStatus());
        uiItemChangeRequest.setItemId(request.getItemId());
        uiItemChangeRequest.setOpType(OpType.DELIVERED.ordinal());
        uiItemChangeRequest.setNextOrg(request.getNextOrg());
        uiItemChangeRequest.setContact(request.getContact());
        uiItemChangeRequest.setExtraInfo("");
        return itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
    }


}
