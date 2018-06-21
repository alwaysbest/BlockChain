package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Author:yangsanyang
 * Time:2018/5/13 4:20 PM.
 * Illustration:
 */
@Entity
@Table(name = "item_type")
@Data
@NoArgsConstructor
public class ItemType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "item_class")
    private String itemClass;
    
    @Column(name = "item_name")
    private String itemName;
    
    public ItemType(String itemClass, String itemName) {
        this.itemClass = itemClass;
        this.itemName = itemName;
    }
}
