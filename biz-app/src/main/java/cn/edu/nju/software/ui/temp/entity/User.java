package cn.edu.nju.software.ui.temp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author:yangsanyang
 * Time:2018/5/13 10:13 PM.
 * Illustration:
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User {
    
    @Id
    private int id;
    
    @Column(name = "userName")
    private String userName;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "user_type")
    private UserType userType;
    
    @Column(name = "organization_id")
    private int organizationId;
    
    
}
