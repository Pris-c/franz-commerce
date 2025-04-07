package org.prisc.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class FranzCommerceProducer {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("SUCSESS " + data.topic() + ":::partition " + data.partition() + "/offiset " + data.offset() + "/timestamp " + data.timestamp());
        };

        var newOrder = "123, 456, 7890";
        var email = "Your order are being processed";
        var record = new ProducerRecord<>("FRANZ_COMMERCE_NEW_ORDER", newOrder, newOrder);
        var emailRecord = new ProducerRecord<>("FRANZ_SEND_EMAIL", email, email);
        producer.send(record, callback).get();
        producer.send(emailRecord, callback).get();
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}