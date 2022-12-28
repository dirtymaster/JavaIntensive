package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        if (number <= 1) {
            System.out.print("Illegal Argument");
            System.exit(-1);
        }
        int i;
        for (i = 2; i * i <= number; ++i) {
            if (number % i == 0) {
                System.out.print("false " + (i - 1));
                return;
            }
        }
        System.out.print("true " + (i - 1));
    }
}