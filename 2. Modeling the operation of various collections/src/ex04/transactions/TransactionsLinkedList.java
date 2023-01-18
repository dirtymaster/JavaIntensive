package ex04.transactions;

import ex04.transactions.exceptions.TransactionNotFoundException;

public class TransactionsLinkedList implements TransactionsList {
    private static class Node {
        Transaction transaction;
        Node next = null;

        Node(Transaction transaction) {
            this.transaction = transaction;
        }
    }

    private Node head = null;
    private int size = 0;

    @Override
    public void addTransaction(Transaction transaction) {
        if (head == null) {
            head = new Node(transaction);
        } else {
            Node tmp = head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = new Node(transaction);
        }
        ++size;
    }

    @Override
    public void removeTransactionById(String id) {
        if (head == null) {
            throw new TransactionNotFoundException(
                    "The list of transactions is empty");
        } else if (head.transaction.getId().equals(id)) {
            head = head.next;
            --size;
            return;
        } else if (head.next == null) {
            throw new TransactionNotFoundException(
                    "There is no transaction with this id");
        }
        Node tmp1 = head;
        Node tmp2 = head.next;
        while (tmp2 != null) {
            if (tmp2.transaction.getId().equals(id)) {
                tmp1.next = tmp2.next;
                --size;
                return;
            }
            tmp1 = tmp2;
            tmp2 = tmp2.next;
        }
        throw new TransactionNotFoundException(
                "There is no transaction with this id");
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[size];
        if (head != null) {
            Node tmp = head;
            for (int i = 0; tmp != null; ++i) {
                array[i] = tmp.transaction;
                tmp = tmp.next;
            }
        }
        return array;
    }
}
