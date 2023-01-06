package com.IonMiddelraad.iprwcbackend.repositories;

import com.IonMiddelraad.iprwcbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE product p SET p.name = :name, p.description = :description, p.price = :price WHERE p.id = :product_id")
    void update(String name, String description, float price, int product_id);
}
