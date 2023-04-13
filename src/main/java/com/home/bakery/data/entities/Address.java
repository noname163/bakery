package com.home.bakery.data.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Adresses")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "address_sequence")
    private long id;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
    @ManyToOne 
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "address")
    private List<Customer> customers;

}
