package ex05.users.exceptions;

public class IllegalBalanceException extends RuntimeException {
    public IllegalBalanceException(String mesage) {
        super(mesage);
    }
}
