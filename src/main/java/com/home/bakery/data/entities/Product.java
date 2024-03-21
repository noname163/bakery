package com.home.bakery.data.entities;

import java.util.List;

import com.home.bakery.data.constans.ProductStatus;

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

@Table(name = "Products")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_sequence")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private String image;
    @Column(name = "expired_date")
    private Integer expiredDate;
    @Column(name = "status")
    private ProductStatus status;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "wholesale_price")
    private Double wholesalePrice;
    @Column(name = "retail_price")
    private Double retailPrice;
    @OneToMany(mappedBy = "product")
    private List<BillDetail> billDetails;
}
