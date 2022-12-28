package ex00;

public class Program {
    public static void main(String[] args) {
        int number = 479598;
        System.out.print(number / 100000 +number / 10000 % 10
                + number / 1000 % 10 + number / 100 % 10
                + number / 10 % 10 + number % 10);
    }
}
