package com.example.ordersservice.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users-service")
public interface UserClient {

    @GetMapping("/users/{id}")
    @CircuitBreaker(name = "userService", fallbackMethod = "getUserFallback")
    @Retry(name = "userService")
    User getUserById(@PathVariable Long id);

    default User getUserFallback(Long id, Throwable ex) {
        User u = new User();
        u.setId(id);
        u.setUsername("Unknown user");
        return u;
    }
}

