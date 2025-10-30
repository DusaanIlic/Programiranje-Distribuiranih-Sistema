package com.example.ordersservice.models;

import java.util.List;

public class OrderDetails {
    private Long userId;
    private String username;
    private List<Order> orders;

    // getters i setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }
}
