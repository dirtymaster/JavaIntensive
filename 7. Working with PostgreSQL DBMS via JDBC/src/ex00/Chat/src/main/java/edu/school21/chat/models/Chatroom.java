package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private final long id;
    private final String name;
    private final User owner;
    private final List<Message> messages;

    public Chatroom(long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id && name.equals(chatroom.name) &&
                Objects.equals(owner, chatroom.owner) &&
                Objects.equals(messages, chatroom.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "{id=" + id + ",name=\"" + name + "\",creator=" + owner +
                ",messages=" + messages + "}";
    }
}
