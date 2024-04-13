package com.home.bakery.data.entities;

import java.time.LocalDate;
import java.util.List;

import com.home.bakery.data.constans.BillStatus;

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

@Table(name = "Bills")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
    @Id
    @SequenceGenerator(name = "bill_sequence", sequenceName = "bill_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "bill_sequence")
    private long id;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User user;
    @Column(name = "created_date")
    private LocalDate createdDate;
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    @Column(name = "paid_date")
    private LocalDate paidDate;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserDetail userDetail;
    @Column(name = "status")
    private BillStatus status;
    @OneToMany(mappedBy = "bill")
    private List<BillDetail> billDetails;
}
