package com.IonMiddelraad.iprwcbackend.dao;

import com.IonMiddelraad.iprwcbackend.model.Order;
import com.IonMiddelraad.iprwcbackend.model.Product;
import com.IonMiddelraad.iprwcbackend.repositories.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {

    private final ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(Integer id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.orElse(null);
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public void store(Product product) {
        this.productRepository.save(product);
    }

    public void delete(Product product) {
        this.productRepository.delete(product);
    }
}
