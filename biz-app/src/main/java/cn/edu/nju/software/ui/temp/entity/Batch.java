package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:yangsanyang
 * Time:2018/5/13 4:16 PM.
 * Illustration:
 */
@Entity
@Table(name = "batch")
@Data
@NoArgsConstructor
public class Batch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "batch_num")
    private String batchNum;
    
    @Column(name = "manufacture_date")
    private Date date;
    
    @Column(name = "item_type_id")
    private int itemTypeId;
    
    @Column(name = "manufacture_id")
    private int manufacturerId;
    
    public Batch(String batchNum, Date date, int itemTypeId, int manufacturerId) {
        this.batchNum = batchNum;
        this.date = date;
        this.itemTypeId = itemTypeId;
        this.manufacturerId = manufacturerId;
    }
}
