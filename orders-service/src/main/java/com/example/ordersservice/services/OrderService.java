package com.example.ordersservice.services;

import com.example.ordersservice.client.UserClient;
import com.example.ordersservice.models.Order;
import com.example.ordersservice.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private final UserClient userClient;
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository, UserClient userClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id)
                .map(order -> {
                    order.setProduct(updatedOrder.getProduct());
                    order.setQuantity(updatedOrder.getQuantity());
                    order.setPrice(updatedOrder.getPrice());
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    // Agregaciona metoda
    public Map<String, Object> getOrderDetails(Long orderId) {
        Order order = getOrderById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found");
        }

        // Poziv drugog servisa (users-service) preko Feign klijenta
        Object user = userClient.getUserById(order.getUserId());

        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("user", user);
        return result;
    }
}
