package com.home.bakery.data.entities;

import java.util.List;

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

@Table(name = "Districts")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class District {
    @Id
    @SequenceGenerator(name = "district_sequence", sequenceName = "district_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "district_sequence")
    private long id;
    @Column(name = "district")
    private String name;
    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;
    @OneToMany(mappedBy = "district")
    private List<Address> addresses;
}
