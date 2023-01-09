package edu.school21.sockets.models;

import java.sql.Timestamp;

public class Message {
    private String sender;
    private String text;
    private Timestamp time;
    private Long roomId;

    public Message(String sender, String text, Timestamp time, Long roomId) {
        this.sender = sender;
        this.text = text;
        this.time = time;
        this.roomId = roomId;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTime() {
        return time;
    }

    public Long getRoomId() {
        return roomId;
    }
}
