package ex03.transactions;

import ex03.users.User;

public class Transaction {
    private String id;
    private User sender;
    private User recipient;
    private Integer transferAmount;

    public enum Category {
        debit, credit
    }

    private Category category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        if ((category == Category.debit && transferAmount < 0) ||
                (category == Category.credit && transferAmount > 0))
            this.transferAmount = transferAmount;
    }
}
