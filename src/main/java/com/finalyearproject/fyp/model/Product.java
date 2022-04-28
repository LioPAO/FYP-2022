package com.finalyearproject.fyp.model;

import com.finalyearproject.fyp.dto.Request.ProductRequestDTO;
import com.finalyearproject.fyp.exceptionHandler.OutOfBoundsException;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "product_id", nullable = false)
    private Long id;

    @NotEmpty
    private String name;
    private int price;
    private String description;
    private String imageUrl;
    private int cost;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private ProductInventory inventory;

    @ManyToMany(cascade =  {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    @JoinTable(name = "Product_Category", joinColumns = {@JoinColumn(name = "product_id")}, inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> category = new TreeSet<>();

    public Product(ProductRequestDTO productRequestDTO) {
        this.inventory = new ProductInventory();
        this.inventory.setQuantity(productRequestDTO.getInventoryQuantity());
        this.name = productRequestDTO.getName();
        this.price = productRequestDTO.getPrice();
        this.description = productRequestDTO.getDescription();
        this.imageUrl = productRequestDTO.getImageUrl();
        this.cost = productRequestDTO.getCost();
    }

    public void addInventoryQuantityBy(int productQuantity) {
        int newQuantity = inventory.getQuantity() + productQuantity;
        inventory.setQuantity(newQuantity);
    }

    public void subtractInventoryQuantityBy(int productQuantity) {
        int newQuantity = inventory.getQuantity() - productQuantity;
        if(newQuantity<0) throw new OutOfBoundsException("GIVEN QUANTITY: "+productQuantity+" SETS INVENTORY BELOW 0 NEW QUANTITY: "+newQuantity);
        inventory.setQuantity(newQuantity);
    }

    public int getInventoryQuantity() {
        return inventory.getQuantity();
    }

    public void addCategory(Category category) {
        this.category.add(category);
        category.getProducts().add(this);
        this.category.forEach(System.out::println);
    }

    public void removeCategory(Category category) {
        this.category.remove(category);
        category.getProducts().remove(this);
        this.category.forEach(System.out::println);
    }

    public Set<Category> getCategory(){
         return this.category;
    }

    public void setInventoryQuantityBy(int productQuantity) {inventory.setQuantity(productQuantity);}

    @Override
    public int compareTo(Product o) {
        return (int) (o.getId() - this.getId());
    }
}