package ex05.transactions;

import ex05.users.User;
import ex05.users.UsersArrayList;
import ex05.users.UsersList;

import java.util.UUID;

public class TransactionsService {
    private final UsersList usersList = new UsersArrayList();

    public void addUser(String name, int remainingFunds) {
        User user = new User();
        user.setName(name);
        user.setRemainingFunds(remainingFunds);
        usersList.addUser(user);
    }

    public int getUsersBalance(int id) {
        return usersList.getUserById(id).getRemainingFunds();
    }

    public void performTransaction(int senderId, int recipientId, int amount)
            throws IllegalAccessException {
        User user1 = usersList.getUserById(senderId);
        User user2 = usersList.getUserById(recipientId);
        if (user1.getRemainingFunds() < amount) {
            throw new IllegalAccessException(
                    "Amount is more than the sender has");
        }

        String id = UUID.randomUUID().toString();

        Transaction transaction1 = new Transaction();
        Transaction transaction2 = new Transaction();

        transaction1.setId(id);
        transaction1.setSender(user1);
        transaction1.setRecipient(user2);
        transaction1.setCategory(Transaction.Category.debit);
        transaction1.setTransferAmount(-amount);

        transaction2.setId(id);
        transaction2.setSender(user2);
        transaction2.setRecipient(user1);
        transaction2.setCategory(Transaction.Category.credit);
        transaction2.setTransferAmount(amount);

        user1.transactionsList.addTransaction(transaction1);
        user2.transactionsList.addTransaction(transaction2);

        user1.setRemainingFunds(user1.getRemainingFunds() - amount);
        user2.setRemainingFunds(user2.getRemainingFunds() + amount);
    }

    public Transaction[] getTransactionsOfUser(int userId) {
        return usersList.getUserById(userId).transactionsList.toArray();
    }

    public void removeTransactionForUser(String transactionId, int userId) {
        if (usersList.getNumberOfUsers() != 0) {
            User user = usersList.getUserById(userId);
            for (Transaction transaction : user.transactionsList.toArray()) {
                if (transaction.getId().equals(transactionId)) {
                    user.transactionsList.removeTransactionById(transactionId);
                }
            }
        }
    }

    public Transaction[] checkValidityOfTransactions() {
        Transaction[] array = new Transaction[0];
        for (int i = 0; i < usersList.getNumberOfUsers(); ++i) {
            User user = usersList.getUesrByIndex(i);
            for (Transaction user1Transaction
                    : user.transactionsList.toArray()) {
                boolean success = false;
                for (Transaction user2Transaction
                        : user1Transaction.getRecipient()
                        .transactionsList.toArray()) {
                    if (user1Transaction.getId().equals(
                            user2Transaction.getId())) {
                        success = true;
                        break;
                    }
                }
                if (!success) {
                    Transaction[] tmpArray = array;
                    array = new Transaction[array.length + 1];
                    System.arraycopy(tmpArray, 0, array, 0, tmpArray.length);
                    array[array.length - 1] = user1Transaction;
                }

            }
        }
        return array;
    }

    public int getNumberOfUsers() {
        return usersList.getNumberOfUsers();
    }

    public User getUserById(int id) {
        return usersList.getUserById(id);
    }
}
