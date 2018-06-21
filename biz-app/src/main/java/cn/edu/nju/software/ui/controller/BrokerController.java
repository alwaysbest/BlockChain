package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.EnvStatus;
import cn.edu.nju.software.common.pojo.ItemStatus;
import cn.edu.nju.software.common.pojo.OpType;
import cn.edu.nju.software.common.pojo.bizservice.request.UIItemChangeRequest;
import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.bean.SessionKey;
import cn.edu.nju.software.ui.bean.request.BrokerOrderRequest;
import cn.edu.nju.software.ui.bean.request.DealerAddReqeust;
import cn.edu.nju.software.ui.bean.response.StockInfoResponse;
import cn.edu.nju.software.ui.bizservice.ItemTracingService;
import cn.edu.nju.software.ui.temp.dao.DealerDao;
import cn.edu.nju.software.ui.temp.dao.DealerItemTypeDao;
import cn.edu.nju.software.ui.temp.entity.*;
import cn.edu.nju.software.ui.temp.service.DealerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Daniel
 * @since 2018/5/13 0:19
 */
@RestController
@RequestMapping("/broker")
public class BrokerController {
    @Autowired
    DealerService dealerService;

    @Autowired
    DealerItemTypeDao dealerItemTypeDao;

    @Autowired
    ItemTracingService itemTracingService;

    @Autowired
    DealerDao dealerDao;

    @RequestMapping(value = "/inStock", method = RequestMethod.POST)
    @ApiOperation(value = "商品入库")
    public BizResponse addItems(@RequestBody DealerAddReqeust dealerAddReqeust, HttpSession
            session) {
        User user = (User) session.getAttribute(SessionKey.USR);

        //-----------------上链------------------------------
        Dealer dealer = dealerDao.findById(user.getOrganizationId()).get();
        String[] itemIds = dealerAddReqeust.getItems().split(Separator.SEPARATOR_PETTERN);

        for (String itemId : itemIds) {
            UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
            uiItemChangeRequest.setOpType(OpType.INSTOCK.ordinal());
            uiItemChangeRequest.setItemId(itemId);
            uiItemChangeRequest.setNextOrg("Org1MSP");
            uiItemChangeRequest.setItemStatus(ItemStatus.DEFAULT_STATUS);
            uiItemChangeRequest.setEnvStatus(new EnvStatus(dealerAddReqeust.getAddressInfo()));
            uiItemChangeRequest.setExtraInfo(dealer.getName() + "收入商品");
            uiItemChangeRequest.setContact("");
            itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
        }
        //-----------------上链结束----------------------------


        return dealerService.addItem(user.getOrganizationId(), dealerAddReqeust.getItems(),
                dealerAddReqeust.getItemTypeId());
    }

    @RequestMapping(value = "/itemOrder", method = RequestMethod.POST)
    @ApiOperation(value = "商品订单，减库存，然后运输")
    public BizResponse itemOrder(@RequestBody BrokerOrderRequest brokerOrderRequest, HttpSession
            session) {
        User user = (User) session.getAttribute(SessionKey.USR);


        //-----------------上链------------------------------
        String[] itemIds = brokerOrderRequest.getItemIds().split(Separator.SEPARATOR_PETTERN);
        for (String itemId : itemIds) {
            UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
            uiItemChangeRequest.setOpType(OpType.BIZORDER.ordinal());
            uiItemChangeRequest.setItemId(itemId);
            uiItemChangeRequest.setNextOrg("Org1MSP");
            uiItemChangeRequest.setItemStatus(ItemStatus.DEFAULT_STATUS);
            uiItemChangeRequest.setEnvStatus(new EnvStatus(brokerOrderRequest.getAddressInfo()));
            uiItemChangeRequest.setExtraInfo("出售商品");
            uiItemChangeRequest.setContact("");
            itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
        }
        //-----------------上链结束----------------------------

        return dealerService.sellItem(user.getOrganizationId(), brokerOrderRequest.getItemIds());
    }

    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    @ApiOperation(value = "商品库存")
    public BizResponse<List<StockInfoResponse>> stockInfos(HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);
        BizResponse<Map<DealerItemType, Integer>> resp = dealerService.getAllDealerItemTypesAndNumber(user.getOrganizationId());
        List<StockInfoResponse> stockInfoResponses = new ArrayList<>();
        if (!resp.getRespStatus().isSuccess())
            return BizResponse.createWithoutData(-1, resp.respStatus.getMsg());
        resp.getRespData().forEach((k, v) -> {
            StockInfoResponse infoResponse = new StockInfoResponse();
            infoResponse.setItemType(k.getItemName());
            infoResponse.setCount(v);
            stockInfoResponses.add(infoResponse);
        });
        return BizResponse.createSuccess(stockInfoResponses, "success");
    }

    @RequestMapping(value = "/allItemTypes", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有的商品类型")
    public BizResponse<List<DealerItemType>> getAllItemTypes() {
        return dealerService.getAllDealerItemTypes();
    }

    @RequestMapping(value = "/deleteType", method = RequestMethod.POST)
    @ApiOperation(value = "删除商品类型")
    public BizResponse deleteItemTypes(int typeId) {
        try {
            dealerItemTypeDao.deleteById(typeId);
        } catch (Exception e) {
            return BizResponse.createWithoutData(-1, "delete error");
        }
        return BizResponse.createSuccess(null, "success");

    }

    @RequestMapping(value = "/addItemTypes", method = RequestMethod.POST)
    @ApiOperation(value = "增加商品类型")
    public BizResponse addItemTypes(
            String typeName,
            String typeClass) {
        DealerItemType itemTypeEntity = new DealerItemType();
        itemTypeEntity.setItemName(typeName);
        itemTypeEntity.setItemClass(typeClass);
        System.out.println(typeName);
        dealerItemTypeDao.save(itemTypeEntity);
        return BizResponse.createSuccess(itemTypeEntity, "success");
    }


}
