package ex00;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        User foo = new User();
        foo.setId(1);
        foo.setName("foo");
        foo.setRemainingFunds(100);

        User bar = new User();
        bar.setId(2);
        bar.setName("bar");
        bar.setRemainingFunds(200);

        Transaction transaction1 = new Transaction();
        transaction1.setId(UUID.randomUUID().toString());
        transaction1.setRecipient(foo);
        transaction1.setSender(bar);
        transaction1.setCategory(Transaction.Category.debit);
        transaction1.setTransferAmount(-50);

        Transaction transaction2 = new Transaction();
        transaction2.setId(UUID.randomUUID().toString());
        transaction2.setRecipient(bar);
        transaction2.setSender(foo);
        transaction2.setCategory(Transaction.Category.credit);
        transaction2.setTransferAmount(50);

        System.out.println(foo.getId());
        System.out.println(foo.getName());
        System.out.println(foo.getRemainingFunds());
        System.out.println(bar.getId());
        System.out.println(bar.getName());
        System.out.println(bar.getRemainingFunds());

        System.out.println(transaction1.getId());
        System.out.println(transaction1.getRecipient());
        System.out.println(transaction1.getSender());
        System.out.println(transaction1.getCategory());
        System.out.println(transaction1.getTransferAmount());
        System.out.println(transaction2.getId());
        System.out.println(transaction2.getRecipient());
        System.out.println(transaction2.getSender());
        System.out.println(transaction2.getCategory());
        System.out.println(transaction2.getTransferAmount());
    }
}
