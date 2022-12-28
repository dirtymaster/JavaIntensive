package ex01;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Math.floor;
import static java.lang.Math.sqrt;

public class Program {
    private static final List<String> first = new ArrayList<>();
    private static final List<String> second = new ArrayList<>();
    private static final List<Integer> A = new ArrayList<>();
    private static final List<Integer> B = new ArrayList<>();

    private static final Set<String> dictionary = new HashSet<>();
    private static Scanner fileScanner;
    private static PrintWriter printWriter;

    public static void main(String[] args) {
        parseFiles(args);

        countWords();

        System.out.println("Similarity = "
                + floor(calculateSimilarity() * 100) / 100);

        File file = new File("src/ex01/dictionary.txt");
        try {
            printWriter = new PrintWriter(file);
            printWriter.write(dictionary.toString());
        } catch (Exception e) {
            System.err.println("File \"dictionary.txt\" not found");
            System.exit(1);
        } finally {
            fileScanner.close();
            printWriter.close();
        }

    }

    private static void parseFiles(String[] args) {
        if (args.length != 2) {
            System.out.println("Incorrect arguments");
            System.exit(1);
        }
        File file1 = new File(args[0]);
        File file2 = new File(args[1]);
        try {
            fileScanner = new Scanner(file1);
        } catch (Exception e) {
            System.err.println("File " + args[0] + " is incorrect");
            fileScanner.close();
            System.exit(1);
        }
        addWordsToList(first);
        try {
            fileScanner = new Scanner(file2);
        } catch (Exception e) {
            System.err.println("File " + args[1] + " is incorrect");
            fileScanner.close();
            System.exit(1);
        }
        addWordsToList(second);

        dictionary.addAll(first);
        dictionary.addAll(second);
    }

    private static void addWordsToList(List<String> list) {
        while (true) {
            String word;
            try {
                word = fileScanner.next();
            } catch (Exception e) {
                break;
            }
            list.add(word);
        }
        if (list.size() == 0) {
            System.err.println("Incorrect file");
            System.exit(1);
        }
    }

    private static void countWords() {
        for (int i = 0; i < dictionary.size(); ++i) {
            A.add(0);
            B.add(0);
        }
        int cnt = 0;
        for (String word : dictionary) {
            for (String s : first) {
                if ((s.equals(word))) {
                    Integer value = A.get(cnt);
                    A.set(cnt, ++value);
                }
            }
            for (String s : second) {
                if ((s.equals(word))) {
                    Integer value = B.get(cnt);
                    B.set(cnt, ++value);
                }
            }
            ++cnt;
        }
    }

    private static double calculateSimilarity() {
        double numerator = 0;
        for (int i = 0; i < A.size(); ++i) {
            numerator += A.get(i) * B.get(i);
        }
        double E_Ai2 = 0;
        for (Integer value : A) {
            E_Ai2 += value * value;
        }
        double E_Bi2 = 0;
        for (Integer value : B) {
            E_Bi2 += value * value;
        }
        double denominator = sqrt(E_Ai2) * sqrt(E_Bi2);

        return numerator / denominator;
    }
}
