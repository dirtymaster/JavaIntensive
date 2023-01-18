package ex02;

import ex02.users.User;
import ex02.users.UsersArrayList;
import ex02.users.exceptions.UserNotFoundException;

public class Program {
    public static void main(String[] args) {
        UsersArrayList usersArrayList = new UsersArrayList();
        for (int i = 1; i <= 100; ++i) {
            User user = new User();
            user.setName(String.valueOf(i));
            usersArrayList.addUser(user);
        }

        System.out.println(usersArrayList.getUserById(42).getName());
        try {
            System.out.println(usersArrayList.getUserById(1942).getName());
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(usersArrayList.getUserByIndex(21).getName());
        try {
            System.out.println(usersArrayList.getUserById(1992).getName());
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }

        System.out.println(usersArrayList.getNumberOfUsers());
    }
}
