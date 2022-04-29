package com.finalyearproject.fyp.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
