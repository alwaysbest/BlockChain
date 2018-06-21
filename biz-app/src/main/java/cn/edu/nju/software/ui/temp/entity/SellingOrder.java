package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:yangsanyang
 * Time:2018/5/14 12:31 PM.
 * Illustration:
 */
@Entity
@Table(name = "selling_order")
@Data
@NoArgsConstructor
public class SellingOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "manufacturer_id")
    private int manufacturerId;
    
    @Column(name = "itemIdString")
    private String itemIdString;
    
    @Column(name = "dealer_id")
    private int dealerId;
    
    @Column(name = "destination")
    private String destination;
    
    @Column(name = "date")
    private Date date;
    
    @Column(name = "email")
    private String email;
    
    
    public SellingOrder(int manufacturerId, String itemIdString, int dealerId, String destination, Date date , String email) {
        this.manufacturerId = manufacturerId;
        this.itemIdString = itemIdString;
        this.dealerId = dealerId;
        this.destination = destination;
        this.date = date;
        this.email = email;
    }
}
