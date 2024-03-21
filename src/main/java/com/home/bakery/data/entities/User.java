package com.home.bakery.data.entities;

import java.util.List;

import com.home.bakery.data.constans.CustomerType;
import com.home.bakery.data.constans.UserRole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Users")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_sequence")
    private long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "password", unique = false)
    private String password;
    @Column(name = "role", unique = false)
    private UserRole role;
    @Column(name = "commission_rate", unique = false)
    private float commissionRate;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "shop_name", unique = false)
    private String shopName;
    @Column(name = "address", unique = false)
    private String addressDetail;
    @ManyToOne 
    @JoinColumn(name = "address_id")
    private Address address;
    @OneToMany(mappedBy = "user")
    private List<Bill> bills;
}
