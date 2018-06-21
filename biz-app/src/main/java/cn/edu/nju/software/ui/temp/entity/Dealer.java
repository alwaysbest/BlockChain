package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:yangsanyang
 * Time:2018/5/13 5:15 PM.
 * Illustration:
 */
@Entity
@Table(name = "dealer")
@Data
@NoArgsConstructor
public class Dealer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;


    @Column(name = "address")
    private String address;
    
    
    @OneToMany(targetEntity = DealerItem.class)
    @JoinColumn(name = "item_id")
    private List<DealerItem> itemIds = new ArrayList<>();
}
