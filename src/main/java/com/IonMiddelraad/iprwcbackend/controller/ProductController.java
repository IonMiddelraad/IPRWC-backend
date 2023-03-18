package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dao.ProductDAO;
import com.IonMiddelraad.iprwcbackend.dto.ProductDTO;
import com.IonMiddelraad.iprwcbackend.model.ApiResponse;
import com.IonMiddelraad.iprwcbackend.model.Order;
import com.IonMiddelraad.iprwcbackend.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(value = "/api/user/product")
public class ProductController {

    private ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity getAllProducts() {
        List<Product> productList = this.productDAO.getAll();
        return new ApiResponse<>(HttpStatus.OK, productList).getResponse();
    }

    @GetMapping(value = "/{product_id}")
    @ResponseBody
    public ResponseEntity getOneProduct(@PathVariable("product_id") int product_id) {
        Product product = this.productDAO.getById(product_id);

        if (isNull(product)) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "The product could not be found").getResponse();
        }
        return new ApiResponse<>(HttpStatus.OK, product).getResponse();
    }

    @PostMapping(value = "/new")
    @ResponseBody
    public ResponseEntity addProduct(@Valid @NotNull @RequestBody ProductDTO productDTO) {
        Product newProduct = productDTO.toProduct();
        this.productDAO.store(newProduct);
        return new ApiResponse<>(HttpStatus.CREATED, newProduct).getResponse();
    }

    @DeleteMapping(value = "/{product_id}")
    @ResponseBody
    public ResponseEntity deleteProduct(@PathVariable("product_id") int product_id) {
        Product product = this.productDAO.getById(product_id);
        if (isNull(product)) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "The product could not be found").getResponse();
        }
        this.productDAO.delete(product);
        return new ApiResponse<>(HttpStatus.NO_CONTENT, "The product has been removed").getResponse();
    }

}
