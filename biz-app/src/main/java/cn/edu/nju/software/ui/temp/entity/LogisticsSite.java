package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Author:yangsanyang
 * Time:2018/5/13 4:40 PM.
 * Illustration:
 */
@Entity
@Table(name = "logistics_site")
@Data
@NoArgsConstructor
public class LogisticsSite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "address")
    private String address;
    
    public LogisticsSite(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
