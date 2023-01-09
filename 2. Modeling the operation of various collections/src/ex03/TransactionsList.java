package ex03;

public interface TransactionsList {
    public void addTransaction(Transaction transaction);

    public void removeTransactionById(String id);

    public Transaction[] toArray();
}
