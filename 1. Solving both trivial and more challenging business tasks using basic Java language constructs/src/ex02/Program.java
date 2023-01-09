package ex02;

import java.util.Scanner;

import static java.lang.Math.sqrt;

public class Program {
    private static int number = 0;
    private static int cnt = 0;

    public static void main(String[] args) {
        while (number != 42) {
            Scanner scanner = new Scanner(System.in);
            number = scanner.nextInt();

            int sum_of_digits = 0;
            int tmp = number;
            while (tmp > 0) {
                sum_of_digits += tmp % 10;
                tmp /= 10;
            }
            boolean is_prime = true;
            for (int i = 2; i <= sqrt(sum_of_digits); ++i) {
                if (sum_of_digits % i == 0) {
                    is_prime = false;
                    break;
                }
            }
            if (is_prime) {
                ++cnt;
            }
        }
        System.out.print("Count of coffee-request – " + cnt);
    }
}