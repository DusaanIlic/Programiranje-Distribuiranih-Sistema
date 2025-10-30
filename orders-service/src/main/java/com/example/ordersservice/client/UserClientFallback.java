package com.example.ordersservice.client;

import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public User getUserById(Long id) {
        // fallback vrednost kada Users-service nije dostupan
        User fallbackUser = new User();
        fallbackUser.setId(id);
        fallbackUser.setUsername("Unknown User");
        fallbackUser.setEmail("unknown@example.com");
        return fallbackUser;
    }
}
