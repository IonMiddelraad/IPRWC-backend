package com.IonMiddelraad.iprwcbackend.dto;

import com.IonMiddelraad.iprwcbackend.model.Order;
import com.IonMiddelraad.iprwcbackend.model.Product;
import com.IonMiddelraad.iprwcbackend.model.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderDTO {

    private List<Product> productList;

    public Order toOrder(User user){
        Order newOrder = new Order();
        newOrder.setUser(user);
        newOrder.setProductList(productList);
        return newOrder;
    }
}
