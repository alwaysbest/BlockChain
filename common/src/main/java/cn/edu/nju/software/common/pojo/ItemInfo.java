package cn.edu.nju.software.common.pojo;

import lombok.Data;

/**
 * @author Daniel
 * @since 2018/4/28 16:41
 * 商品属性，从出厂后就不能更改
 */
@Data
public class ItemInfo {
    String name;
    String class_;
    String batchNum;
    String manufactureDate;
    String note;
}
