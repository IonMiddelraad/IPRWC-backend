package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dao.OrderDAO;
import com.IonMiddelraad.iprwcbackend.model.ApiResponse;
import com.IonMiddelraad.iprwcbackend.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(value = "/api/user/order")
public class OrderController {

    private  OrderDAO orderDAO;

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity getAllOrders() {
        List<Order> orderList = this.orderDAO.getAll();
        return new ApiResponse<>(HttpStatus.OK, orderList).getResponse();
    }

    @GetMapping(value = "/{order_id}")
    @ResponseBody
    public ResponseEntity getOneOrder(@PathVariable("order_id") int order_id) {
        Order order = this.orderDAO.getById(order_id);

        if (isNull(order)) {
            return new ApiResponse<>(HttpStatus.NOT_FOUND, "The order could not be found").getResponse();
        }
        return new ApiResponse<>(HttpStatus.OK, order).getResponse();
    }

    @PostMapping(value = "/add")
    @ResponseBody
    public ResponseEntity addOrder(@RequestBody Order newOrder) {
        this.orderDAO.store(newOrder);
        return new ApiResponse<>(HttpStatus.CREATED, newOrder).getResponse();
    }
}
