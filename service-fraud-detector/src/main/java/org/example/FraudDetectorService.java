package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {

    private final KafkaCreator<Order> orderSender = new KafkaCreator<>();

    public static void main(String[] args) {
        var fraudDetectorService = new FraudDetectorService();
            try(var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(),
                    "FRANZ_COMMERCE_NEW_ORDER",
                    fraudDetectorService::parse,
                    Order.class,
                    new HashMap<>())) {;
            service.run();
        }
    }

    void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
        System.out.println("--Processing new order, checking for fraud--");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value().getValue());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        System.out.println("---------------------------");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var order = record.value();
        if(isFraud(order)){
            System.out.println("DANGER: Fraud detected! " + order);
            orderSender.send("FRANZ_COMM_ORDER_REJECTED", order.getEmail(), order);
        } else {
            System.out.println("Order approved: " + order);
            orderSender.send("FRANZ_COMM_ORDER_APPROVED", order.getEmail()  , order);
        }

        System.out.println("Order processed");
    }

    private static boolean isFraud(Order order) {
        return (order.getValue().compareTo(new BigDecimal("4500")) >= 0);
    }

}
