package org.example;

public class EmailMessage {
    private final String subject, body;

    public EmailMessage(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    @Override
    public String toString() {
        return "EmailMessage{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
