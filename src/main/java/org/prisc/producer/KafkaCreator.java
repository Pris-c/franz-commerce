package org.prisc.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.prisc.util.GsonSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaCreator<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    public KafkaCreator() {
        this.producer = new KafkaProducer<>(properties());
    }

    public void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
        var emailRecord = new ProducerRecord<>(topic, key, value);
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("SUCSESS " + data.topic() + ":::partition " + data.partition() + "/offiset " + data.offset() + "/timestamp " + data.timestamp());
        };
        producer.send(emailRecord, callback).get();

    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return properties;
    }

    @Override
    public void close() {
        producer.close();
    }
}
