package ex05.users;

import ex05.transactions.TransactionsLinkedList;
import ex05.transactions.TransactionsList;
import ex05.users.exceptions.IllegalBalanceException;

public class User {
    private final Integer id;
    private String name;
    private Integer remainingFunds;

    public TransactionsList transactionsList = new TransactionsLinkedList();

    public User() {
        this.id = UserIdsGenerator.getInstance().generateId();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRemainingFunds() {
        return remainingFunds;
    }

    public void setRemainingFunds(Integer remainingFunds) {
        if (remainingFunds >= 0) {
            this.remainingFunds = remainingFunds;
        } else {
            throw new IllegalBalanceException("The balance must be non-negative");
        }
    }
}
