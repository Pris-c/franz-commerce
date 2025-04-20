package org.example;

import java.math.BigDecimal;

public class Order {

    private final String userId, orderId;
    private final BigDecimal value;
    private final String email;

    public Order(String userId, String orderId, BigDecimal value, String email) {
        this.userId = userId;
        this.orderId = orderId;
        this.value = value;
        this.email = email;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", value=" + value +
                ", email='" + email + '\'' +
                '}';
    }



}
