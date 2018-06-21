package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Type;

/**
 * Author:yangsanyang
 * Time:2018/5/14 10:05 AM.
 * Illustration:
 */
@Entity
@Table(name = "dealer_item")
@NoArgsConstructor
@Data
public class DealerItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "item_id")
    private String itemId;
    
    @Column(name = "dealer_item_type_id")
    private int dealerItemTypeId;
    
    @Column(name = "dealer_id")
    private int dealerId;
    
    public DealerItem(int dealer_id , String itemId, int dealerItemTypeId ) {
        this.dealerId = dealer_id;
        this.itemId = itemId;
        this.dealerItemTypeId = dealerItemTypeId;
    }
}
