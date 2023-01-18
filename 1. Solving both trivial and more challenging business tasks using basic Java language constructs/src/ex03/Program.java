package ex03;

import java.util.Scanner;
import static java.lang.Math.pow;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);
    private static long result = 0;

    public static void main(String[] args) {
        parseStrings();

        checkCorrectness();

        output();
    }

    private static void parseStrings() {
        while (true) {
            String string = scanner.nextLine();
            if (string.length() < 6
                    || !"Week ".equals(string.substring(0, 5))) {
                if (string.equals("42")) {
                    break;
                }
                System.err.print("Illegal Argument");
                System.exit(-1);
            }

            String number_of_week_string = string.substring(5);
            int number_of_week = 0;
            for (int i = 0; i < number_of_week_string.length(); ++i) {
                char c = number_of_week_string.charAt(i);
                if (c < '1' || c > '9') {
                    System.err.print("Illegal Argument");
                    System.exit(-1);
                }
                number_of_week *= 10;
                number_of_week += c - '0';
            }

            int min = 9;
            string = scanner.nextLine();
            if (string.length() != 9) {
                System.err.print("Illegal Argument");
                System.exit(-1);
            }
            for (int i = 0; i < 9; i += 2) {
                int number = string.charAt(i) - '0';
                if (number < min) {
                    min = number;
                }
            }
            result += min * pow(10, number_of_week - 1);
        }
    }

    private static void checkCorrectness() {
        long tmp_result = result;
        while (tmp_result > 0) {
            if (tmp_result % 10 == 0) {
                System.err.print("Illegal Argument");
                System.exit(-1);
            }
            tmp_result /= 10;
        }
    }

    private static void output() {
        for (int week = 1; result > 0; ++week) {
            System.out.print("Week" + week + " ");
            for (int i = 0; i < result % 10; ++i)
                System.out.print("=");
            System.out.print(">");
            if (result > 10) {
                System.out.print("\n");
            }
            result /= 10;
        }
    }
}
