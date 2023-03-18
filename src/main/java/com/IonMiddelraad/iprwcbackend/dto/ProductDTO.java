package com.IonMiddelraad.iprwcbackend.dto;

import com.IonMiddelraad.iprwcbackend.model.Product;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductDTO {

    private String name;
    private String description;
    private float price;
    private String image;

    public Product toProduct() {
        Product newProduct = new Product(name, description, price, image);
        return newProduct;
    }
}
