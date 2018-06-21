package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Author:yangsanyang
 * Time:2018/5/14 9:59 AM.
 * Illustration:
 */
@Entity
@Table(name = "dealer_item_type")
@Data
@NoArgsConstructor
public class DealerItemType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "item_class")
    private String itemClass;
    
    @Column(name = "item_name")
    private String itemName;
    
    public DealerItemType(String itemClass, String itemName) {
        this.itemClass = itemClass;
        this.itemName = itemName;
    }
    
}
