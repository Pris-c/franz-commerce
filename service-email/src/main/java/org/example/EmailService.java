package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var service = new KafkaService<>(EmailService.class.getSimpleName(),
                "FRANZ_SEND_EMAIL",
                emailService::parse,
                EmailMessage.class,
                new HashMap<>()
        )) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, EmailMessage> record) {
        System.out.println("------Sending new email------");
        System.out.println("key: " + record.key());
        System.out.println("value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------Email sent------");
    }

}
