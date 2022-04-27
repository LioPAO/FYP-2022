package com.finalyearproject.fyp.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product implements Comparable<Product>{
    @Id
    @SequenceGenerator(name = "productID",
            sequenceName = "productID",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "productID")
    @Setter(AccessLevel.NONE)
    @Column(name = "product_id", nullable = false)
    private Long id;
    @NotEmpty
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private int cost;

    @ManyToMany()
    @JoinTable(name = "Category_Id",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private List<Category> category;

    @OneToOne
    @JoinColumn(name = "inventory_id")
    private ProductInventory inventory;

    public void addInventoryQuantityBy(int productQuantity) {
        int newQuantity = inventory.getQuantity() + productQuantity;
        inventory.setQuantity(newQuantity);
    }

    public void subtractInventoryQuantityBy(int productQuantity) {
        int newQuantity = inventory.getQuantity() - productQuantity;
        inventory.setQuantity(newQuantity);
    }

    public void setInventoryQuantityBy(int productQuantity) {inventory.setQuantity(productQuantity);}

    public int getInventoryQuantity() {
        if(inventory==null) return 0;
        return inventory.getQuantity();
    }

    public void addCategory(Category category) {this.category.add(category);}

    public void removeCategory(Category category) {this.category.remove(category);
    }

    @Override
    public int compareTo(Product o) {
        return (int) (o.getId() - this.getId());
    }
}