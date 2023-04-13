package com.home.bakery.data.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Provinces")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Province {
    @Id
    @SequenceGenerator(name = "province_sequence", sequenceName = "province_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "province_sequence")
    private long id;
    @Column(name = "province")
    private String name;
    @OneToMany(mappedBy="province")
    @Column(unique = true)
    private List<Address> addresses;
}
