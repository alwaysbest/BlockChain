package cn.edu.nju.software.ui.temp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;

/**
 * Author:yangsanyang
 * Time:2018/5/13 4:12 PM.
 * Illustration:
 */
@Entity
@Table(name = "item")
@Data
@NoArgsConstructor
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "item_id")
    private String itemId;
    
    @Column(name = "batch_num")
    private String batchNum;
    
    @Column(name = "item_status")
    private ItemStatus itemStatus;
    
    public Item(String itemId, String batchNum) {
        this.itemId = itemId;
        this.batchNum = batchNum;
        itemStatus = ItemStatus.unsold;
    }
}
