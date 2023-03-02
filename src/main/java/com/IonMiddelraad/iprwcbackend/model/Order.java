package com.IonMiddelraad.iprwcbackend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id")
    )
    @NotBlank
    private List<Product> productList;



    public Order(User userInfo, List<Product> productList) {
        this.user = userInfo;
        this.productList = productList;
    }

    public int getId() {
        return id;
    }

    public User getUserInfo() {
        return user;
    }

    public void setUserInfo(User userInfo) {
        this.user = userInfo;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
