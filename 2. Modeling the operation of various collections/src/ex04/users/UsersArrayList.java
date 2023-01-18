package ex04.users;

import ex04.users.exceptions.UserNotFoundException;

public class UsersArrayList implements UsersList {
    private int capacity = 10;
    private User[] array = new User[capacity];
    private int size = 0;

    @Override
    public void addUser(User user) {
        if (size != capacity) {
            array[size++] = user;
        } else {
            User[] tmp_array = array;
            int old_capacity = capacity;
            capacity *= 1.5;
            array = new User[capacity];
            System.arraycopy(tmp_array, 0, array, 0, old_capacity);
            addUser(user);
        }
    }

    @Override
    public User getUserById(int id) {
        for (int i = 0; i < size; ++i) {
            if (array[i].getId() == id) {
                return array[i];
            }
        }
        throw new UserNotFoundException("There is no user with this id");
    }

    @Override
    public User getUesrByIndex(int index) {
        if (index >= 0 && index < size) {
            return array[index];
        }
        throw new UserNotFoundException("The index is incorrect");
    }

    @Override
    public int getNumberOfUsers() {
        return size;
    }
}
