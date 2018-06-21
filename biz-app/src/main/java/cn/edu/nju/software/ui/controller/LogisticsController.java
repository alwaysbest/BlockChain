package cn.edu.nju.software.ui.controller;

import cn.edu.nju.software.common.pojo.EnvStatus;
import cn.edu.nju.software.common.pojo.ItemStatus;
import cn.edu.nju.software.common.pojo.OpType;
import cn.edu.nju.software.common.pojo.bizservice.request.UIItemChangeRequest;
import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.common.util.DateUtil;
import cn.edu.nju.software.ui.bean.SessionKey;
import cn.edu.nju.software.ui.bean.request.LogisticsArrivedRequest;
import cn.edu.nju.software.ui.bean.request.LogisticsDepartureRequest;
import cn.edu.nju.software.ui.bean.request.LogisticsOrderRequest;
import cn.edu.nju.software.ui.bean.request.LogisticsSignRequest;
import cn.edu.nju.software.ui.bean.response.LogisSiteResponse;
import cn.edu.nju.software.ui.bean.response.ToArriveOrder;
import cn.edu.nju.software.ui.bean.response.ToDepartureOrder;
import cn.edu.nju.software.ui.bizservice.ItemTracingService;
import cn.edu.nju.software.ui.temp.dao.ItemTypeDao;
import cn.edu.nju.software.ui.temp.dao.LogisticsSiteDao;
import cn.edu.nju.software.ui.temp.entity.*;
import cn.edu.nju.software.ui.temp.service.LogisticsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Daniel
 * @since 2018/5/13 0:19
 */
@RestController
@RequestMapping("/logis")
public class LogisticsController {

    @Autowired
    LogisticsService logisticsService;


    @Autowired
    ItemTracingService itemTracingService;

    @Autowired
    LogisticsSiteDao logisticsSiteDao;

    @RequestMapping(value = "/itemDeparture", method = RequestMethod.POST)
    @ApiOperation(value = "商品出发，站点库存减少订单")
    public BizResponse carDeparture(
            @ApiParam
            @RequestBody LogisticsDepartureRequest departureRequest, HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);


        //-----------------上链------------------------------
        LogisticsSite site = logisticsSiteDao.findById(user.getOrganizationId()).get();
        List<String> itemIds = logisticsService.getAllItemIds(departureRequest.getOrders()).getRespData();

        for (String itemId : itemIds) {
            UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
            uiItemChangeRequest.setOpType(OpType.DEPARTURE.ordinal());
            uiItemChangeRequest.setItemId(itemId);
            uiItemChangeRequest.setNextOrg("Org1MSP");
            uiItemChangeRequest.setItemStatus(ItemStatus.DEFAULT_STATUS);
            uiItemChangeRequest.setEnvStatus(new EnvStatus(departureRequest.getAddressInfo()));
            uiItemChangeRequest.setExtraInfo(site.getName() + "发出商品");
            uiItemChangeRequest.setContact("");
            itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
        }
        //-----------------上链结束----------------------------

        for (String s : departureRequest.getOrders()) {
            logisticsService.transitOrder(s, user.getOrganizationId(), departureRequest.getDepartureAddress());
        }
        return BizResponse.createSuccess(null, "success");
    }

    @RequestMapping(value = "/itemArrived", method = RequestMethod.POST)
    @ApiOperation(value = "商品到达，站点库存增加订单")
    public BizResponse carArrived(
            @ApiParam
            @RequestBody LogisticsArrivedRequest arrivedRequest, HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);

        //-----------------上链------------------------------
        LogisticsSite site = logisticsSiteDao.findById(user.getOrganizationId()).get();
        List<String> itemIds = logisticsService.getAllItemIds(arrivedRequest.getOrders()).getRespData();

        for (String itemId : itemIds) {
            UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
            uiItemChangeRequest.setOpType(OpType.ARRIVED.ordinal());
            uiItemChangeRequest.setItemId(itemId);
            uiItemChangeRequest.setNextOrg("Org1MSP");
            uiItemChangeRequest.setItemStatus(ItemStatus.DEFAULT_STATUS);
            uiItemChangeRequest.setEnvStatus(new EnvStatus(arrivedRequest.getAddressInfo()));
            uiItemChangeRequest.setExtraInfo(site.getName() + "收入商品");
            uiItemChangeRequest.setContact("");
            itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
        }
        //-----------------上链结束----------------------------


        for (String s : arrivedRequest.getOrders()) {
            logisticsService.receiveOrder(s, user.getOrganizationId());
        }
        return BizResponse.createSuccess(null, "success");
    }

    @RequestMapping(value = "/toDepartureOrder", method = RequestMethod.POST)
    @ApiOperation(value = "获得可以出发的订单")
    public BizResponse<List<ToDepartureOrder>> getToDepartureOrders(HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);

        BizResponse<List<Order>> toDeparture = logisticsService.getToDeparture(user.getOrganizationId());

        List<ToDepartureOrder> collect = toDeparture.getRespData().stream().map(e -> {
            ToDepartureOrder toDepartureOrder = new ToDepartureOrder();
            toDepartureOrder.setOrderNum(e.getOrderNum());
            toDepartureOrder.setAddress(e.getDestination());
            toDepartureOrder.setOrderDesc(e.getDescription());
            return toDepartureOrder;
        }).collect(Collectors.toList());

        return BizResponse.createSuccess(collect, "success");
    }

    @RequestMapping(value = "/toArriveOrder", method = RequestMethod.POST)
    @ApiOperation(value = "获得可以到达的订单")
    public BizResponse<List<ToArriveOrder>> getToArriveOrders(HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);
        BizResponse<List<Order>> toArrive = logisticsService.getToArrive(user.getOrganizationId());
        List<ToArriveOrder> collect = toArrive.getRespData().stream().map(e -> {
            ToArriveOrder toArriveOrder = new ToArriveOrder();
            Path pathLt = e.getPaths().get(e.getPaths().size() - 1);
            Path pathLs = e.getPaths().get(e.getPaths().size() - 2);
            int siteId = pathLs.getLogisticsSiteId();
            toArriveOrder.setFrom(logisticsService.getLogisticsSite(siteId).getRespData().getAddress());
            toArriveOrder.setFromTime(DateUtil.formatDate(pathLt.getDate()));
            toArriveOrder.setOrderNum(e.getOrderNum());
            toArriveOrder.setOrderDesc(e.getDescription());
            return toArriveOrder;
        }).collect(Collectors.toList());
        return BizResponse.createSuccess(collect, "success");
    }

    @RequestMapping(value = "/toDepartureNode", method = RequestMethod.POST)
    @ApiOperation(value = "获得所有可以出发的节点")
    public BizResponse<List<LogisSiteResponse>> getAllDepartureStations(HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);
        BizResponse<List<LogisticsSite>> nodes = logisticsService.getAllLogisticsSite(user
                .getOrganizationId());
        List<LogisSiteResponse> collect = nodes.getRespData().stream().map(e -> {
            LogisSiteResponse siteResponse = new LogisSiteResponse();
            siteResponse.setId(e.getId());
            siteResponse.setName(e.getName());
            return siteResponse;
        }).collect(Collectors.toList());
        return BizResponse.createSuccess(collect, "success");
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    @ApiOperation(value = "物流订单")
    public BizResponse logisOrder(
            @ApiParam
            @RequestBody LogisticsOrderRequest orderRequest, HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);

        //-----------------上链------------------------------
        LogisticsSite logisticsSite = logisticsSiteDao.findById(user.getOrganizationId()).get();

        String[] itemIds = orderRequest.getItems().split(Separator.SEPARATOR_PETTERN);
        for (String itemId : itemIds) {
            UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
            uiItemChangeRequest.setOpType(OpType.LOGISORDER.ordinal());
            uiItemChangeRequest.setItemId(itemId);
            uiItemChangeRequest.setNextOrg("Org1MSP");
            uiItemChangeRequest.setItemStatus(ItemStatus.DEFAULT_STATUS);
            uiItemChangeRequest.setEnvStatus(new EnvStatus(orderRequest.getAddressInfo()));
            uiItemChangeRequest.setExtraInfo(logisticsSite.getName() + "收入商品");
            uiItemChangeRequest.setContact("");
            itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
        }
        //-----------------上链结束----------------------------

        return logisticsService.addOrder(user.getOrganizationId(), orderRequest
                        .getDepartureAddress(),
                orderRequest.getItems(), orderRequest.getOrderDesc());
    }

    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @ApiOperation(value = "商品签收，订单完成，如果为分销商订单则分销商的库存需要增加")
    public BizResponse orderSign(
            @ApiParam
            @RequestBody LogisticsSignRequest logisticsSignRequest, HttpSession session) {
        User user = (User) session.getAttribute(SessionKey.USR);

        //-----------------上链------------------------------
        List<String> itemIds = logisticsService.getAllItemIds(Arrays.asList(logisticsSignRequest
                .getOrder())).getRespData();
        for (String itemId : itemIds) {
            UIItemChangeRequest uiItemChangeRequest = new UIItemChangeRequest();
            uiItemChangeRequest.setOpType(OpType.DELIVERED.ordinal());
            uiItemChangeRequest.setItemId(itemId);
            uiItemChangeRequest.setNextOrg("Org1MSP");
            uiItemChangeRequest.setItemStatus(ItemStatus.DEFAULT_STATUS);
            uiItemChangeRequest.setEnvStatus(new EnvStatus(logisticsSignRequest.getAddressInfo()));
            uiItemChangeRequest.setExtraInfo(logisticsSignRequest.getName() + "签收商品");
            uiItemChangeRequest.setContact(logisticsSignRequest.getEmail());
            itemTracingService.changeItemTracingInfo(uiItemChangeRequest);
        }
        //-----------------上链结束----------------------------


        return logisticsService.signOrder(logisticsSignRequest.getOrder(), user.getOrganizationId());
    }


}
