package ex05.transactions;

public interface TransactionsList {
    void addTransaction(Transaction transaction);

    void removeTransactionById(String id);

    Transaction[] toArray();
}
