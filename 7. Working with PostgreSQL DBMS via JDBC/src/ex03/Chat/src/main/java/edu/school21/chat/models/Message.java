package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private Timestamp time;

    public Message(Long id, User author, Chatroom room, String text, Timestamp time) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && author.equals(message.author)
                && room.equals(message.room) && text.equals(message.text)
                && time.equals(message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, time);
    }

    @Override
    public String toString() {
        return "Message : {\n  id=" + id + ",\n  author=" + author
                + ",\n  room=" + room + ",\n  text=\"" + text
                + "\",\n  dateTime=" + time + "\n}";
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
