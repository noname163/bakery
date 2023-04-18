package com.home.bakery.data.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
