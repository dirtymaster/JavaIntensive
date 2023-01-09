package edu.school21.sockets.models;

public class User {
    private Long id;
    private String username;
    private String encryptedPassword;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.encryptedPassword = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                '}';
    }
}
