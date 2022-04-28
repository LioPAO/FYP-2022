package com.finalyearproject.fyp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotEmpty()
    private String name;
    private String description;
    private String imageUrl;
    @ManyToMany(mappedBy = "category",cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private Set<Product> products = new TreeSet<>();

}
