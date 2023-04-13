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

@Table(name = "Cities")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @SequenceGenerator(name = "city_sequence", sequenceName = "city_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "city_sequence")
    private long id;
    @Column(name = "city")
    private String name;
    @OneToMany(mappedBy = "city")
    private List<Address> addresses;
}
