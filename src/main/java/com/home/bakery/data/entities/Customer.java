package com.home.bakery.data.entities;

import com.home.bakery.data.constans.CustomerType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Customers")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @SequenceGenerator(name = "customer_sequence", sequenceName = "customer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_sequence")
    private long id;
    @Column(name = "name", unique = false)
    private String name;
    @Column(name = "phone", unique = true)
    private String phone;
    @Column(name = "shop_name", unique = false)
    private String shopName;
    @Column(name = "address", unique = false)
    private String addressDetail;
    @Column(name = "type", unique = false)
    private CustomerType type;
    @ManyToOne 
    @JoinColumn(name = "address_id")
    private Address address;
}
