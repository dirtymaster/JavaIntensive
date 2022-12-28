package ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Program {
    private static final Random random = new Random();
    private static final List<Integer> array = new ArrayList<>();
    private static int arraySize = 0;
    private static final List<MyThread> threadList = new ArrayList<>();
    private static int threadsCount = 0;
    private static List<Integer> startIndices = new ArrayList<>();
    private static List<Integer> endIndices = new ArrayList<>();

    public static void main(String[] args) {
        parseArguments(args);

        for (int i = 0; i < arraySize; ++i) {
            array.add(random.nextInt(10));
        }

        initializeStartAndEndIndices(threadsCount);
        for (int id = 1; id <= threadsCount; ++id) {
            threadList.add(new MyThread(id, startIndices.get(id - 1),
                    endIndices.get(id - 1), array));
        }

        calculateAndOutput();
    }

    private static void parseArguments(String[] args) {
        if (args.length != 2 || args[0].length() < 13
                || !args[0].startsWith("--arraySize=")
                || args[1].length() < 16
                || !args[1].startsWith("--threadsCount=")) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        Scanner scanner = new Scanner(args[0].substring(12));
        try {
            arraySize = scanner.nextInt();
            if (arraySize < 2) {
                throw new Exception(
                        "The size of the array must be greater than 1");
            }
        } catch (Exception e) {
            System.err.println("Invalid arguments");
            System.exit(1);
        } finally {
            scanner.close();
        }
        scanner = new Scanner(args[1].substring(15));
        try {
            threadsCount = scanner.nextInt();
            if (threadsCount >= arraySize || threadsCount < 1) {
                throw new Exception("Invalid threads count");
            }
        } catch (Exception e) {
            System.err.println("Invalid arguments");
            System.exit(1);
        } finally {
            scanner.close();
        }
    }

    private static void initializeStartAndEndIndices(int threadsCount) {
        startIndices = new ArrayList<>(threadsCount);
        endIndices = new ArrayList<>(threadsCount);
        for (int i = 0; i < threadsCount; ++i) {
            startIndices.add(0);
            endIndices.add(0);
        }
        startIndices.set(0, 0);
        endIndices.set(threadsCount - 1, array.size());
        for (int i = 1; i < threadsCount; ++i) {
            int bound = (int) (startIndices.get(i - 1)
                    + (double) (array.size()) / threadsCount);

            endIndices.set(i - 1, bound);
            startIndices.set(i, bound);
        }
    }

    private static void calculateAndOutput() {
        long sum = 0;
        for (MyThread myThread : threadList) {
            myThread.start();
        }
        for (MyThread myThread : threadList) {
            try {
                myThread.join();
            } catch (InterruptedException ignored) {
            }
            System.out.println("Thread " + myThread.getTreadId() + ": from "
                    + myThread.getStartIndex() + " to " +
                    (myThread.getEndIndex() - 1) + " sum is "
                    + myThread.getSum());
            sum += myThread.getSum();
        }
        System.out.println("Sum by threads: " + sum);
    }
}
