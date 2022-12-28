package ex05;

public class User {
    private Integer id;
    private String name;
    private Integer remainingFunds;

    public class IllegalBalance extends RuntimeException {
        public IllegalBalance(String mesage) {
            super(mesage);
        }
    }

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
            throw new IllegalBalance("The balance must be non-negative");
        }
    }
}
