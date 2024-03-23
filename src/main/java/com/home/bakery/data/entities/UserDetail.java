package com.home.bakery.data.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "User_Details")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {
    @Id
    @SequenceGenerator(name = "user_detail_sequence", sequenceName = "user_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_detail_sequence")
    private long id;
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
    @OneToOne
    private User user;
    @Column(name = "created_date")
    @Default
    private LocalDate createdDate = LocalDate.now();
    @Column(name = "updated_date")
    @Default
    private LocalDate updatedDate = LocalDate.now();
    @OneToMany(mappedBy = "userDetail")
    private List<Bill> bills;
}
