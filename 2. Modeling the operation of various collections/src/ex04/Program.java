package ex04;

import ex04.transactions.Transaction;
import ex04.transactions.TransactionsService;

public class Program {
    public static void main(String[] args) throws IllegalAccessException {
        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser("foo", 100000);
        transactionsService.addUser("bar", 100000);
        assert (transactionsService.getUsersBalance(1) == 100000);
        assert (transactionsService.getUsersBalance(2) == 100000);

        for (int i = 0; i < 100; ++i) {
            transactionsService.performTransaction(1, 2, 50);
        }
        Transaction[] transactionsArray1
                = transactionsService.getTransactionsOfUser(1);
        Transaction[] transactionsArray2
                = transactionsService.getTransactionsOfUser(2);

        for (int i = 0; i < 10; ++i) {
            assert (transactionsArray1[i].getId().equals(
                    transactionsArray2[i].getId()));
        }

        Transaction[] incorrectTransactions
                = transactionsService.checkValidityOfTransactions();
        assert (incorrectTransactions.length == 0);

        String transactionId
                = transactionsService.getTransactionsOfUser(1)[1].getId();
        transactionsService.removeTransactionForUser(transactionId, 1);

        incorrectTransactions
                = transactionsService.checkValidityOfTransactions();
        System.out.println(incorrectTransactions[0].getId());
    }
}
