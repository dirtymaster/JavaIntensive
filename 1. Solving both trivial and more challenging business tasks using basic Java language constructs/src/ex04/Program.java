package ex04;

import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);

    private static final int[] arr = new int[65535];
    private static int max = 0;

    private static final String[] string_arr = new String[65535];

    public static void main(String[] args) {
        parseString();

        fillInStrings();

        sortStrings();

        output();
    }

    private static void parseString() {
        String string = scanner.nextLine();
        string = string.substring(0, string.length() - 2);

        for (char c : string.toCharArray()) {
            arr[c] += 1;
            if (arr[c] > max) {
                max = arr[c];
            }
        }
    }

    private static void fillInStrings() {
        for (int i = 0; i < string_arr.length; ++i) {
            if (arr[i] != 0) {
                char[] char_arr = new char[max / 4 + 3];

                char_arr[0] = (char) i;
                char_arr[max / 4 + 2] = (char) arr[i];
                for (int j = 1; j < max / 4 + 2; ++j) {
                    if (arr[i] / 4. >= j) {
                        char_arr[j] = '#';
                    } else {
                        char_arr[j] = ' ';
                    }
                }
                string_arr[i] = new String(char_arr);
            } else {
                string_arr[i] = null;
            }
        }
    }

    private static void sortStrings() {
        for (int i = 0; i < string_arr.length - 1; ++i) {
            for (int j = 0; j < string_arr.length - i - 1; ++j) {
                if (string_arr[j + 1] != null && (string_arr[j] == null ||
                        (string_arr[j].toCharArray()[max / 4 + 2]
                                < string_arr[j + 1].toCharArray()
                                [max / 4 + 2]))) {
                    String tmp = string_arr[j];
                    string_arr[j] = string_arr[j + 1];
                    string_arr[j + 1] = tmp;
                }
            }
        }
    }

    private static void output() {
        for (int i = max / 4 + 1; i >= 0; --i) {
            for (int j = 0; j < arr.length && j < 10; ++j) {
                if (string_arr[j] != null) {
                    if (string_arr[j].toCharArray()[i] == ' '
                            && (string_arr[j].toCharArray()[i - 1] == '#'
                            || i == 1)) {
                        System.out.print(String.format("%2d",
                                (int) string_arr[j].toCharArray()
                                        [max / 4 + 2]) + "  ");
                    } else {
                        System.out.print(" "
                                + string_arr[j].toCharArray()[i] + "  ");
                    }
                }
            }
            System.out.print("\n");
        }
    }
}
