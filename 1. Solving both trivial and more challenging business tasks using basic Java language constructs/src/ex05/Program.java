package ex05;

import java.util.Scanner;

public class Program {
    private static String string;
    private static Scanner scanner = new Scanner(System.in);

    private static String[] students = new String[10];
    private static int number_of_students = 0;

    private static int[][] lessons = new int[10][2];
    private static int number_of_lessons = 0;

    private static int[][] cells;
    private static int number_of_cells = 0;

    public static void main(String[] args) {
        initStudents();

        initLessons();

        initCells();

        fillInCells();

        outputHeader();

        outputRows();
    }

    private static void initStudents() {
        while (true) {
            string = scanner.nextLine();
            if (string.length() > 10) {
                System.err.print("Illegal Argument");
                System.exit(-1);
            }
            if (string.equals("."))
                break;
            students[number_of_students++] = string;
        }
        for (int i = number_of_students; i < students.length; ++i) {
            students[i] = null;
        }
    }

    private static void initLessons() {
        while (true) {
            string = scanner.nextLine();
            if (string.equals("."))
                break;
            lessons[number_of_lessons][0] = string.charAt(0) - '0';
            switch (string.substring(2, 4)) {
                case "MO":
                    lessons[number_of_lessons][1] = 0;
                    break;
                case "TU":
                    lessons[number_of_lessons][1] = 1;
                    break;
                case "WE":
                    lessons[number_of_lessons][1] = 2;
                    break;
                case "TH":
                    lessons[number_of_lessons][1] = 3;
                    break;
                case "FR":
                    lessons[number_of_lessons][1] = 4;
                    break;
                case "SA":
                    lessons[number_of_lessons][1] = 5;
                    break;
                case "SU":
                    lessons[number_of_lessons][1] = 6;
                    break;
                default:
                    System.err.print("Illegal Argument");
                    System.exit(-1);
            }
            ++number_of_lessons;
        }
    }

    private static void initCells() {
        cells = new int[30 * number_of_students * number_of_lessons][4];
        for (int i = 0; i < 30; ++i) {
            for (int j = 0; j < number_of_lessons; ++j) {
                if (lessons[j][1] == (i + 1) % 7) {
                    for (int k = 0; k < number_of_students; ++k) {
                        cells[number_of_cells][0] = i;
                        cells[number_of_cells][1] = k;
                        cells[number_of_cells][2] = j;
                        cells[number_of_cells][3] = 0;
                        ++number_of_cells;
                    }
                }
            }
        }
    }

    private static void fillInCells() {
        while (true) {
            string = scanner.nextLine();
            if (string.equals("."))
                break;
            Scanner string_scanner = new Scanner(string);
            String student_name = string_scanner.next();
            int student_index = 0;
            for (int i = 0; i < number_of_students; ++i) {
                if (students[i].equals(student_name)) {
                    student_index = i;
                    break;
                }
            }
            int time = string_scanner.nextInt();
            int day_of_month_index = string_scanner.nextInt() - 1;
            String string_is_here = string_scanner.next();
            int is_here = 0;
            if (string_is_here.equals("HERE"))
                is_here = 1;
            else if (string_is_here.equals("NOT_HERE"))
                is_here = -1;

            for (int i = 0; i < number_of_cells; ++i) {
                if (cells[i][1] == student_index
                        && cells[i][0] == day_of_month_index
                        && lessons[cells[i][2]][0] == time) {
                    cells[i][3] = is_here;
                    break;
                }
            }
        }
    }

    private static void outputHeader() {
        System.out.print("          ");
        for (int i = 0; i < 30; ++i) {
            for (int j = 0; j < number_of_cells; j += number_of_students) {
                if (cells[j][0] == i) {
                    System.out.print(lessons[cells[j][2]][0] + ":00 ");
                    switch (lessons[cells[j][2]][1]) {
                        case 0:
                            System.out.print("MO");
                            break;
                        case 1:
                            System.out.print("TU");
                            break;
                        case 2:
                            System.out.print("WE");
                            break;
                        case 3:
                            System.out.print("TH");
                            break;
                        case 4:
                            System.out.print("FR");
                            break;
                        case 5:
                            System.out.print("SA");
                            break;
                        case 6:
                            System.out.print("SU");
                            break;
                    }
                    System.out.print(" " + String.format("%2d", i + 1) + "|");
                }
            }
        }
        System.out.print("\n");
    }

    private static void outputRows() {
        for (int m = 0; m < number_of_students; ++m) {
            System.out.print(String.format("%10s", students[m]));
            for (int i = 0; i < 30; ++i) {
                for (int j = 0; j < number_of_cells; ++j) {
                    if (cells[j][0] == i && cells[j][1] == m) {
                        if (cells[j][3] == 0)
                            System.out.print("          |");
                        else
                            System.out.print(String.format("%10d",
                                    cells[j][3]) + "|");
                    }
                }
            }
            System.out.print("\n");
        }
    }
}
