package org.example;


import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrder {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var orderProducer = new KafkaCreator<Order>()) {
            try (var emailProducer = new KafkaCreator<EmailMessage>()) {
                for (var i = 0; i < 10; i++) {

                    var orderId = UUID.randomUUID().toString();
                    var value = BigDecimal.valueOf(Math.random() * 5000 + 1);
                    var email = Math.random() + "@email.com";
                    var order = new Order(orderId, value, email);
                    orderProducer.send("FRANZ_COMMERCE_NEW_ORDER", email, order);

                    var emailMessage = new EmailMessage("Confirmation", "Your order are being processed");
                    emailProducer.send("FRANZ_SEND_EMAIL", email, emailMessage);
                }
            }
        }
    }
}