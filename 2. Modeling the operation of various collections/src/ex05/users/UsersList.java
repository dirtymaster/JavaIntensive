package ex05.users;

public interface UsersList {
    void addUser(User user);

    User getUserById(int id);

    User getUesrByIndex(int index);

    int getNumberOfUsers();
}
