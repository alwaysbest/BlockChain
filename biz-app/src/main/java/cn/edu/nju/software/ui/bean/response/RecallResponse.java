package cn.edu.nju.software.ui.bean.response;

import cn.edu.nju.software.fabricservice.protomsg.Persistence;
import cn.edu.nju.software.ui.temp.entity.Item;
import cn.edu.nju.software.ui.temp.entity.ItemType;
import lombok.Data;

import java.util.List;

/**
 * @author Daniel
 * @since 2018/5/14 0:04
 */
@Data
public class RecallResponse {
    String typeName;
    String typeClass;
    List<RecallItem> recallItems;
}
