package cn.edu.nju.software.ui.temp.service.impl;

import cn.edu.nju.software.common.pojo.bizservice.response.BizResponse;
import cn.edu.nju.software.ui.temp.dao.DealerDao;
import cn.edu.nju.software.ui.temp.dao.LogisticsSiteDao;
import cn.edu.nju.software.ui.temp.dao.OrderDao;
import cn.edu.nju.software.ui.temp.entity.*;
import cn.edu.nju.software.ui.temp.service.LogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:yangsanyang
 * Time:2018/5/14 9:18 AM.
 * Illustration:
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    private final LogisticsSiteDao logisticsSiteDao;

    private final OrderDao orderDao;

    @Autowired
    public LogisticsServiceImpl(LogisticsSiteDao logisticsSiteDao, OrderDao orderDao) {
        this.logisticsSiteDao = logisticsSiteDao;
        this.orderDao = orderDao;
    }

    @Override
    public BizResponse<List<String>> getAllItemIds(List<String> orderNums) {
        List<String> itemIds = new ArrayList<>();
        orderNums.stream().forEach(e -> {
            itemIds.addAll(orderDao.findByOrderNum(e).getItemIds());
        });
        return BizResponse.defaultResponse(itemIds);
    }

    @Override
    public BizResponse<List<LogisticsSite>> getAllLogisticsSite(int organizationId) {
        List<LogisticsSite> list = logisticsSiteDao.findAll().stream()
                .filter(site -> site.getId() != organizationId)
                .collect(Collectors.toList());
        return BizResponse.defaultResponse(list);
    }

    @Override
    public BizResponse addOrder(int organizationId, String destination, String itemIdString, String description) {

        String orderNum = generateOrderNum();
        Order order = new Order(orderNum, description, destination, Arrays.asList(itemIdString.split(Separator.SEPARATOR_PETTERN)));
        order.getPaths().add(new Path(organizationId, new Date(), OrderState.arrive));

        orderDao.save(order);
        return BizResponse.defaultResponse(null);

    }

    @Override
    public BizResponse receiveOrder(String orderNum, int organizationId) {

        Order order = orderDao.findByOrderNum(orderNum);

        if (order == null)
            return BizResponse.createWithoutData(-1, "no order found!");

        List<Path> paths = order.getPaths();
        if (paths.size() == 0)
            return BizResponse.createWithoutData(-2, "bad state");

        Path latestPath = paths.get(paths.size() - 1);
        if (!latestPath.getOrderState().equals(OrderState.transit) || latestPath.getLogisticsSiteId() != organizationId)
            return BizResponse.createWithoutData(-2, "bad state");

        paths.add(new Path(organizationId, new Date(), OrderState.arrive));
        orderDao.save(order);

        return BizResponse.defaultResponse(null);

    }

    @Override
    public BizResponse transitOrder(String orderNum, int organizationId, int nextLogisticsSiteId) {

        Order order = orderDao.findByOrderNum(orderNum);

        if (order == null)
            return BizResponse.createWithoutData(-1, "no order found!");

        List<Path> paths = order.getPaths();
        if (paths.size() == 0)
            return BizResponse.createWithoutData(-2, "bad state");

        Path latestPath = paths.get(paths.size() - 1);
        if (!latestPath.getOrderState().equals(OrderState.arrive) || latestPath.getLogisticsSiteId() != organizationId)
            return BizResponse.createWithoutData(-2, "bad state");

        paths.add(new Path(nextLogisticsSiteId, new Date(), OrderState.transit));
        orderDao.save(order);

        return BizResponse.defaultResponse(null);

    }

    @Override
    public BizResponse signOrder(String orderNum, int organizationId) {

        Order order = orderDao.findByOrderNum(orderNum);

        if (order == null)
            return BizResponse.createWithoutData(-1, "no order found!");

        List<Path> paths = order.getPaths();
        if (paths.size() == 0)
            return BizResponse.createWithoutData(-2, "bad state");

        Path latestPath = paths.get(paths.size() - 1);
        if (!latestPath.getOrderState().equals(OrderState.arrive) || latestPath.getLogisticsSiteId() != organizationId)
            return BizResponse.createWithoutData(-2, "bad state");

        paths.add(new Path(0, new Date(), OrderState.sign));
        orderDao.save(order);

        return BizResponse.defaultResponse(null);

    }

    private String generateOrderNum() {
        String generateNum = String.valueOf((long) (99999999 * Math.random()));
        for (int i = 0; i < 8 - generateNum.length(); i++)
            generateNum = '0' + generateNum;
        return generateNum;
    }

    @Override
    public BizResponse<List<Order>> getToArrive(int organizationId) {

        List<Order> orderList = orderDao.findAll().stream().filter(order -> {
            List<Path> paths = order.getPaths();
            if (paths.size() == 0)
                return false;
            Path latestPath = paths.get(paths.size() - 1);
            return latestPath.getLogisticsSiteId() == organizationId && latestPath.getOrderState().equals(OrderState.transit);
        }).collect(Collectors.toList());

        return BizResponse.defaultResponse(orderList);

    }

    @Override
    public BizResponse<List<Order>> getToDeparture(int organizationId) {

        List<Order> orderList = orderDao.findAll().stream().filter(order -> {
            List<Path> paths = order.getPaths();
            if (paths.size() == 0)
                return false;
            Path latestPath = paths.get(paths.size() - 1);
            return latestPath.getLogisticsSiteId() == organizationId && latestPath.getOrderState().equals(OrderState.arrive);
        }).collect(Collectors.toList());

        return BizResponse.defaultResponse(orderList);

    }

    @Override
    public BizResponse<LogisticsSite> getLogisticsSite(int id) {
        return BizResponse.defaultResponse(logisticsSiteDao.getOne(id));
    }


}
