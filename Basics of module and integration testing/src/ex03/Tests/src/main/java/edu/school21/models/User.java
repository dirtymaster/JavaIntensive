package edu.school21.models;

public class User {
    private long id;
    private String login;
    private String password;
    private boolean authenticationSuccessStatus;

    public User(long id, String login, String password, boolean authenticationSuccessStatus) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.authenticationSuccessStatus = authenticationSuccessStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuthenticationSuccessStatus() {
        return authenticationSuccessStatus;
    }

    public void setAuthenticationSuccessStatus(boolean authenticationSuccessStatus) {
        this.authenticationSuccessStatus = authenticationSuccessStatus;
    }
}
