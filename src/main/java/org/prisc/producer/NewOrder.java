package org.prisc.producer;

import org.prisc.model.Order;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrder {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderProducer = new KafkaCreator<Order>()) {
            try (var emailProducer = new KafkaCreator<String>()) {
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var value = BigDecimal.valueOf(Math.random() * 5000 + 1);
                    var order = new Order(userId, orderId, value);
                    orderProducer.send("FRANZ_COMMERCE_NEW_ORDER", userId, order);

                    var email = "Your order are being processed";
                    emailProducer.send("FRANZ_SEND_EMAIL", userId, email);
                }
            }
        }
    }
}