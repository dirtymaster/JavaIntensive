package ex05;

import ex05.transactions.Transaction;
import ex05.transactions.TransactionsService;
import ex05.users.exceptions.UserNotFoundException;
import ex05.users.exceptions.IllegalBalanceException;
import ex05.users.User;

import java.util.Scanner;

public class Program {
    private static boolean devMode = false;
    private static final Scanner scanner = new Scanner(System.in);
    private static final TransactionsService transactionsService
            = new TransactionsService();
    private static String menu;
    private static int number = -1;

    public static void main(String[] args) throws IllegalAccessException {
        initMenu(args);

        endlessCycle();
    }

    private static void printLine() {
        System.out.println(
                "---------------------------------------------------------");
    }

    private static void initMenu(String[] args) {
        menu = "1. Add a user\n"
                + "2. View user balances\n"
                + "3. Perform a transfer\n"
                + "4. View all transactions for a specific user\n";
        if (args.length == 1 && args[0].equals("--profile=dev")) {
            devMode = true;
            menu += "5. DEV – remove a transfer by ID\n"
                    + "6. DEV – check transfer validity\n"
                    + "7. Finish execution";
        } else {
            menu += "5. Finish execution";
        }
    }

    private static void endlessCycle() {
        while ((devMode && number != 7) || (!devMode && number != 5)) {
            System.out.println(menu);
            try {
                number = scanner.nextInt();
            } catch (Exception e) {
                System.err.println("The input is incorrect");
                System.exit(1);
            }

            switch (number) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewUserBalances();
                    break;
                case 3:
                    performTransfer();
                    break;
                case 4:
                    viewAllTransactionsForSpecificUser();
                    break;
                case 5:
                    removeTransferById();
                    break;
                case 6:
                    checkTransferValidity();
                    break;
                case 7:
                    finishExecution();
                    break;
                default:
                    System.err.println("The input is incorrect");
                    System.exit(1);
            }
        }
    }

    private static void addUser() {
        System.out.println("Enter a user name and a balance");
        String name = null;
        int balance = -1;
        try {
            name = scanner.next();
            balance = scanner.nextInt();
        } catch (Exception e) {
            System.err.println("The input is incorrect");
            System.exit(1);
        }
        try {
            transactionsService.addUser(name, balance);
            System.out.println("User with id = "
                    + transactionsService.getNumberOfUsers()
                    + " is added");
        } catch (IllegalBalanceException e) {
            System.err.println(e.getMessage());
        }
        printLine();
    }

    private static void viewUserBalances() {
        System.out.println("Enter a user ID");
        int id = -1;
        try {
            id = scanner.nextInt();
        } catch (Exception e) {
            System.err.println("The input is incorrect");
            System.exit(1);
        }
        try {
            User user = transactionsService.getUserById(id);
            System.out.println(user.getName() + " - "
                    + user.getRemainingFunds());
        } catch (UserNotFoundException e) {
            System.err.println(e.getMessage());
        }
        printLine();
    }

    private static void performTransfer() {
        System.out.println(
                "Enter a sender ID, a recipient ID,"
                        + " and a transfer amount");
        int senderId = -1;
        int recipiendId = -1;
        int amount = -1;
        try {
            senderId = scanner.nextInt();
            recipiendId = scanner.nextInt();
            amount = scanner.nextInt();
        } catch (Exception e) {
            System.err.println("The input is incorrect");
            System.exit(1);
        }
        try {
            transactionsService.performTransaction(senderId,
                    recipiendId, amount);
            System.out.println("The transfer is completed");
        } catch (UserNotFoundException |
                 IllegalAccessException e) {
            System.err.println(e.getMessage());
            printLine();
        }
    }
    
    private static void viewAllTransactionsForSpecificUser() {
        System.out.println("Enter a user ID");
        int id = -1;
        try {
            id = scanner.nextInt();
        } catch (Exception e) {
            System.err.println("The input is incorrect");
            System.exit(1);
        }
        Transaction[] transactions;
        try {
            transactions
                    = transactionsService.getTransactionsOfUser(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            printLine();
            return;
        }
        for (Transaction transaction : transactions) {
            if (transaction.getCategory()
                    == Transaction.Category.debit) {
                System.out.print("To ");
            } else {
                System.out.print("From ");
            }
            System.out.println(
                    transaction.getRecipient().getName()
                            + "(id = "
                            + transaction.getRecipient().getId()
                            + ") " + transaction.getTransferAmount()
                            + " with id = " + transaction.getId());
        }
        printLine();
    }
    
    private static void removeTransferById() {
        if (devMode) {
            System.out.println("Enter a user ID and a transfer ID");
            int userId = -1;
            String transactionId = null;
            try {
                userId = scanner.nextInt();
                transactionId = scanner.next();
            } catch (Exception e) {
                System.err.println("The input is incorrect");
                System.exit(1);
            }
            Transaction deletedTransaction = null;
            try {
                for (Transaction tmp
                        : transactionsService.getTransactionsOfUser(
                        userId)) {
                    if (tmp.getId().equals(transactionId)) {
                        deletedTransaction = new Transaction(tmp);
                        break;
                    }
                }
                transactionsService.removeTransactionForUser(
                        transactionId, userId);
                User recipient = deletedTransaction.getRecipient();
                if (deletedTransaction.getCategory()
                        == Transaction.Category.debit) {
                    System.out.print("Transfer To ");
                } else {
                    System.out.print("Transfer From ");
                }
                System.out.println(recipient.getName() + "(id = "
                        + recipient.getId() + ") "
                        + (deletedTransaction.getCategory()
                        == Transaction.Category.debit
                        ? -deletedTransaction.getTransferAmount()
                        : deletedTransaction.getTransferAmount())
                        + " removed");
            } catch (Exception e) {
                System.err.println(
                        "There is not transaction with these "
                                + "parameters");
            }
        } else {
            System.exit(0);
        }
    }
    
    private static void checkTransferValidity() {
        if (devMode) {
            Transaction[] invalidTransactions
                    = transactionsService
                    .checkValidityOfTransactions();
            for (Transaction transaction : invalidTransactions) {
                System.out.print(
                        transaction.getSender().getName()
                                + "(id = "
                                + transaction.getSender().getId()
                                + ") has an unacknowledged transfer"
                                + " id = " + transaction.getId());
                if (transaction.getCategory()
                        == Transaction.Category.credit) {
                    System.out.print(" from ");
                } else {
                    System.out.print(" to ");
                }
                System.out.println(transaction.getRecipient().
                        getName() + "(id = "
                        + transaction.getRecipient().getId()
                        + ") for "
                        + transaction.getTransferAmount());
            }
            printLine();
        } else {
            System.err.println("The input is incorrect");
            System.exit(1);
        }
    }
    
    private static void finishExecution() {
        if (devMode) {
            System.exit(0);
        } else {
            System.err.println("The input is incorrect");
            System.exit(1);
        }
    }
}
