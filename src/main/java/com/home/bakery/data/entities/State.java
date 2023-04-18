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

@Table(name = "States")
@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State {
    @Id
    @SequenceGenerator(name = "state_sequence", sequenceName = "state_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "state_sequence")
    private long id;
    @Column(name = "state")
    private String name;
    @OneToMany(mappedBy="state")
    private List<Address> addresses;
}

