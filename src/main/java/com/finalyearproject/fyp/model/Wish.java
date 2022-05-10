package com.finalyearproject.fyp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Wish implements Comparable<Wish> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    private LocalDate addedOn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    //CONSTRUCTOR
    public Wish(User user, Product product) {
        this.user = user;
        this.product = product;
        this.addedOn = LocalDate.now();
    }

    @Override
    public int compareTo(Wish o) {
        return (int) (o.getId() - this.getId());
    }
}
