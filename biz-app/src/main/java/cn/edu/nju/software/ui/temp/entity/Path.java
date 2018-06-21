package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:yangsanyang
 * Time:2018/5/13 4:46 PM.
 * Illustration:
 */
@Entity
@Table(name = "logistics_path")
@Data
@NoArgsConstructor
public class Path {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "logistics_site_id")
    private int logisticsSiteId;
    
    @Column(name = "date")
    private Date date;
    
    @Column(name = "orderState")
    private OrderState orderState;
    
    public Path(int logisticsSiteId, Date date, OrderState orderState) {
        this.logisticsSiteId = logisticsSiteId;
        this.date = date;
        this.orderState = orderState;
    }
}
