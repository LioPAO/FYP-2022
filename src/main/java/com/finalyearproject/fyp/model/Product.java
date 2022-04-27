package com.finalyearproject.fyp.model;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "PRODUCT")
public class Product implements Comparable<Product> {
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

    @OneToOne
    @JoinColumn(name = "inventory_id")
    private ProductInventory inventory;

    @ManyToMany()
    @JoinTable(name = "Product_Category", joinColumns = {@JoinColumn(name = "product_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> category;

    public Product(ProductRequestDTO productRequestDTO) {
        this.inventory = new ProductInventory();
        this.inventory.setQuantity(productRequestDTO.getInventoryQuantity());
        this.name = productRequestDTO.getName();
        this.price = productRequestDTO.getPrice();
        this.description = productRequestDTO.getDescription();
        this.imageUrl = productRequestDTO.getImageUrl();
        this.cost = productRequestDTO.getCost();
        this.category = new TreeSet<>();
    }

    public void addInventoryQuantityBy(int productQuantity) {
        int newQuantity = inventory.getQuantity() + productQuantity;
        inventory.setQuantity(newQuantity);
    }

    public void subtractInventoryQuantityBy(int productQuantity) {
        int newQuantity = inventory.getQuantity() - productQuantity;
        inventory.setQuantity(newQuantity);
    }

    public int getInventoryQuantity() {
        if (inventory == null) return 0;
        return inventory.getQuantity();
    }

    public void addCategory(Category category) {this.category.add(category);}

    public void removeCategory(Category category) {this.category.remove(category);}

    public void setInventoryQuantityBy(int productQuantity) {inventory.setQuantity(productQuantity);}

    @Override
    public int compareTo(Product o) {
        return (int) (o.getId() - this.getId());
    }
}