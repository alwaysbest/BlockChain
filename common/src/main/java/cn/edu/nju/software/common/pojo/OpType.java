package cn.edu.nju.software.common.pojo;

public enum OpType {
    //出厂
    CREATED,
    //销售订单
    BIZORDER,
    //物流订单
    LOGISORDER,
    //出发
    DEPARTURE,
    //到达
    ARRIVED,
    //送达
    DELIVERED,
    //分销商入库
    INSTOCK;


    public static OpType getOpTypeByIndex(int index) {
        OpType[] types = OpType.values();
        if (index >= types.length)
            return null;
        return types[index];
    }

}

