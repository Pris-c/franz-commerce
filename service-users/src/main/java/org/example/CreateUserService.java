package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class CreateUserService {

    private final Connection connection;

    public CreateUserService() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/franzcommerce";
        String user = "postgres";
        String password = "12345";
        this.connection = DriverManager.getConnection(url, user, password);
        Statement stmt = this.connection.createStatement();
        stmt.execute(
                "CREATE TABLE IF NOT EXISTS Users (" +
                        "uuid VARCHAR(200) PRIMARY KEY, " +
                        "email VARCHAR(200))"
        );
    }

    public static void main(String[] args) throws SQLException {
        var createUserService = new CreateUserService();
        try(var service = new KafkaService<>(CreateUserService.class.getSimpleName(),
                "FRANZ_COMMERCE_NEW_ORDER",
                createUserService::parse,
                Order.class,
                new HashMap<>())) {;
            service.run();
        }
    }

    void parse(ConsumerRecord<String, Order> record) throws SQLException {
        System.out.println("--Processing new order, checking for new user--");
        System.out.println("value: " + record.value().getValue());
        System.out.println("\n\n");
        var order = record.value();

        if (isNewUser(order.getEmail())){
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(String email) throws SQLException {
        var insertStatement = connection.prepareStatement("INSERT INTO Users (uuid, email) VALUES (?,?)");
        insertStatement.setString(1, "uuid");
        insertStatement.setString(2, email);
        insertStatement.execute();
        System.out.println("User uuid, email: " + email + " added successfully");
    }

    private boolean isNewUser(String email) throws SQLException {
        var existStatement = connection.prepareStatement("SELECT uuid FROM Users " +
                "WHERE email = ? " +
                "LIMIT 1");
        existStatement.setString(1, email);
        var result = existStatement.executeQuery();
        return !result.next();
    }

}
