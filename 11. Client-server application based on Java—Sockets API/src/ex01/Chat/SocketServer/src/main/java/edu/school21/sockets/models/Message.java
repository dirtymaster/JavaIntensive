package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {
    private String sender;
    private String message;
    private Timestamp time;

    public Message(String sender, String message, Timestamp time) {
        this.sender = sender;
        this.message = message;
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTime() {
        return time;
    }
}
