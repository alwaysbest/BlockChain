package cn.edu.nju.software.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Daniel
 * @since 2018/4/28 16:43
 * 商品状态，表示商品当前的状态，可以通过内部传感器采集
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemStatus {
    public static final ItemStatus DEFAULT_STATUS = new ItemStatus(true, "正常");
    boolean normal;
    String logs;
}
