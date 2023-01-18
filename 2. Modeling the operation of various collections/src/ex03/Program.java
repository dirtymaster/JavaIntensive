package ex03;

import ex03.transactions.Transaction;
import ex03.transactions.exceptions.TransactionNotFoundException;
import ex03.users.User;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User foo = new User();
        User bar = new User();
        foo.setRemainingFunds(100000);
        bar.setRemainingFunds(100000);
        String[] ids = new String[100];

        String tmpId = null;
        for (int i = 0; i < 100; ++i) {
            Transaction transaction = new Transaction();

            if (i % 2 == 0) {
                tmpId = UUID.randomUUID().toString();
                ids[i] = tmpId;
                transaction.setId(ids[i]);
                transaction.setSender(foo);
                transaction.setRecipient(bar);
                transaction.setCategory(Transaction.Category.debit);
                transaction.setTransferAmount(-50);
                foo.transactionsList.addTransaction(transaction);
            } else {
                ids[i] = tmpId;
                transaction.setId(ids[i]);
                transaction.setSender(bar);
                transaction.setRecipient(foo);
                transaction.setCategory(Transaction.Category.credit);
                transaction.setTransferAmount(50);
                bar.transactionsList.addTransaction(transaction);
            }
        }

        Transaction[] transactionsArray = foo.transactionsList.toArray();
        for (int i = 0; i < 3; ++i) {
            System.out.println(transactionsArray[i].getId());
        }
        System.out.println();
        foo.transactionsList.removeTransactionById(ids[2]);
        transactionsArray = foo.transactionsList.toArray();
        for (int i = 0; i < 3; ++i) {
            System.out.println(transactionsArray[i].getId());
        }
        System.out.println();

        try {
            foo.transactionsList.removeTransactionById("aboba");
        } catch (TransactionNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}
