package org.prisc.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.prisc.model.Order;

import java.util.HashMap;

public class FraudDetectorService {

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

    void parse(ConsumerRecord<String, Order> record) {
        System.out.println("--Processing new order, checking for fraud--");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        System.out.println("---------------------------");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed");
    }

}
