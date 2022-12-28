package ex01;

public class Program {
    public static void main(String[] args) {
        User foo = new User();
        foo.setName("foo");
        foo.setRemainingFunds(100);

        User bar = new User();
        bar.setName("bar");
        bar.setRemainingFunds(200);

        System.out.println(foo.getId());
        System.out.println(foo.getName());
        System.out.println(foo.getRemainingFunds());
        System.out.println(bar.getId());
        System.out.println(bar.getName());
        System.out.println(bar.getRemainingFunds());
    }
}
