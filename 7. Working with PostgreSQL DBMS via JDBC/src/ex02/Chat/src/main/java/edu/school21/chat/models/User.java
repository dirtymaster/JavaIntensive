package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private final long id;
    private final String login;
    private final String password;
    private final List<Chatroom> createdChatrooms;
    private final List<Chatroom> socializationChatrooms;

    public User(long id, String login, String password,
                List<Chatroom> createdChatrooms,
                List<Chatroom> socializationChatrooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatrooms = createdChatrooms;
        this.socializationChatrooms = socializationChatrooms;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Chatroom> getCreatedChatrooms() {
        return createdChatrooms;
    }

    public List<Chatroom> getSocializationChatrooms() {
        return socializationChatrooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && login.equals(user.login)
                && password.equals(user.password)
                && Objects.equals(createdChatrooms, user.createdChatrooms)
                && Objects.equals(socializationChatrooms,
                user.socializationChatrooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdChatrooms,
                socializationChatrooms);
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ",login=\"" + login + "\",password=" + password
                + "\",createdRooms=" + createdChatrooms +
                ",rooms=" + socializationChatrooms +
                '}';
    }
}
