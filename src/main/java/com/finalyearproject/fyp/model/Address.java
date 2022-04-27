package com.finalyearproject.fyp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address implements Comparable<Address> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String state;
    private String city;
    private String street;


    @Override
    public int compareTo(Address o) {
        return (int) (o.getId()-this.getId());
    }
}
