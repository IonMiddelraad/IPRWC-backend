package com.IonMiddelraad.iprwcbackend.dao;

import com.IonMiddelraad.iprwcbackend.model.Order;
import com.IonMiddelraad.iprwcbackend.repositories.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {

    private final OrderRepository orderRepository;

    public OrderDAO(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getById(Integer id) {
        Optional<Order> order = this.orderRepository.findById(id);
        return order.orElse(null);
    }

    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    public void store(Order order) {
        this.orderRepository.save(order);
    }

    public void delete(Order order) {
        this.orderRepository.delete(order);
    }
}
