package com.finalyearproject.fyp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "CATEGORY")
public class Category {

    @Id
    @SequenceGenerator( name = "categoryID" ,
            sequenceName = "categoryID",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="categoryID")
    @Column(name = "category_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotEmpty()
    private String name;
    private String description;
    private String imageUrl;
    @ManyToMany(mappedBy = "category")
    private Set<Product> product = new TreeSet<>();

    public void addProduct(Product product) {this.product.add(product);}

    public void removeProduct(Product product) {this.product.remove(product);
    }
}
