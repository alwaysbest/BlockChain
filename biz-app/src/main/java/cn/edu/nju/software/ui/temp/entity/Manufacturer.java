package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:yangsanyang
 * Time:2018/5/13 4:22 PM.
 * Illustration:
 */
@Entity
@Table(name = "manufacturer")
@Data
@NoArgsConstructor
public class Manufacturer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "address")
    private String address;
    
//    @OneToMany(targetEntity = SellingOrder.class)
//    @JoinColumn(name = "manufacturer_id")
//    private List<SellingOrder> sellingOrderList = new ArrayList<>();
    
    public Manufacturer(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
