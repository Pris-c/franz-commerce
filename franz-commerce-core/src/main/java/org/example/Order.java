package org.example;

import java.math.BigDecimal;

public class Order {

    private final String orderId;
    private final BigDecimal value;
    private final String email;


    public Order(String orderId, BigDecimal value, String email) {
        this.orderId = orderId;
        this.value = value;
        this.email = email;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", value=" + value +
                ", email='" + email + '\'' +
                '}';
    }
}
