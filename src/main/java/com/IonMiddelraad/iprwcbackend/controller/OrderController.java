package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dao.OrderDAO;
import com.IonMiddelraad.iprwcbackend.dao.UserDAO;
import com.IonMiddelraad.iprwcbackend.dto.OrderDTO;
import com.IonMiddelraad.iprwcbackend.model.ApiResponse;
import com.IonMiddelraad.iprwcbackend.model.Order;
import com.IonMiddelraad.iprwcbackend.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(value = "/api/user/order")

public class OrderController {

    private OrderDAO orderDAO;
    private UserDAO userDAO;

    public OrderController(OrderDAO orderDAO, UserDAO userDAO) {
        this.orderDAO = orderDAO;
        this.userDAO = userDAO;
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity getAllOrders() {
        List<Order> orderList = this.orderDAO.getAll();

        List<Order> safeOrderList = new ArrayList<>();
        for (Order order : orderList) {
            safeOrderList.add(new Order(order.getId(),
                    new User(order.getUser().getId(), order.getUser().getName()),
                    order.getProductList()));
        }

        return new ApiResponse<>(HttpStatus.OK, safeOrderList).getResponse();
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

    @PostMapping(value = "/new")
    @ResponseBody
    public ResponseEntity addOrder(@Valid @NotNull @RequestBody OrderDTO orderDTO) {
        User user = this.userDAO.getEmployeeDetails();
        Order newOrder = orderDTO.toOrder(user);
        this.orderDAO.store(newOrder);
        return new ApiResponse<>(HttpStatus.CREATED, newOrder).getResponse();
    }
}
