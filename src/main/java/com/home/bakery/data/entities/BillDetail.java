package com.home.bakery.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Bill_Details")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {
    @Id
    @SequenceGenerator(name = "bill_detail_sequence", sequenceName = "bill_detail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "bill_detail_sequence")
    private long id;
    @Column(name = "quantity", unique = false)
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
