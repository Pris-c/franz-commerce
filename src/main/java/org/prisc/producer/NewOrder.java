package org.prisc.producer;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrder {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var producer = new KafkaCreator()) {
            var newOrder = "123, 456, 7890";
            var email = "Your order are being processed";
            for (var i = 0; i < 10; i++) {
                var key = UUID.randomUUID().toString();
                producer.send("FRANZ_COMMERCE_NEW_ORDER", key, newOrder);
                producer.send("FRANZ_SEND_EMAIL", key, email);
            }
            throw new RuntimeException();
        }
    }


}