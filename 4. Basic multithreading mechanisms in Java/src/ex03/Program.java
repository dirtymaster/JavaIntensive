package ex03;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Program {
    private static int threadsCount = 0;
    private static Scanner scanner;
    private static final Queue<MyFile> fileQueue = new LinkedList<>();

    public static void main(String[] args) {
        parseArgument(args);

        parseFile();

        int threadId = 0;
        while (threadId < threadsCount) {
            new Thread(new Downloader(fileQueue, ++threadId)).start();
        }
    }

    private static void parseArgument(String[] args) {
        if (args.length != 1 || args[0].length() < 16 || !args[0].startsWith("--threadsCount=")) {
            System.err.println("Incorrect argument");
            System.exit(1);
        }
        scanner = new Scanner(args[0].substring(15));
        try {
            threadsCount = scanner.nextInt();
            if (threadsCount < 1) {
                throw new Exception();
            }
        } catch (Exception e) {
            System.err.println("Incorrect argument");
            System.exit(1);
        } finally {
            scanner.close();
        }
    }

    private static void parseFile() {
        File file = new File("ex03/files_urls.txt");
        if (!file.exists() || file.isDirectory()) {
            System.err.println("File \"files_urls.txt\" is incorrect");
            System.exit(1);
        }
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("File \"files_urls.txt\" not found");
            System.exit(1);
        }
        if (file.length() == 0) {
            System.err.println("File \"files_urls.txt\" is empty");
            System.exit(1);
        }
        Scanner stringScanner;
        String line;
        while (true) {
            try {
                line = scanner.nextLine();
            } catch (Exception e) {
                scanner.close();
                break;
            }
            stringScanner = new Scanner(line);

            int fileId = 0;
            try {
                fileId = stringScanner.nextInt();
            } catch (Exception e) {
                System.err.println("File \"files_urls.txt\" is incorrect");
                System.exit(1);
            }

            String url = null;
            try {
                url = stringScanner.next();
            } catch (Exception e) {
                System.err.println("File \"files_urls.txt\" is incorrect");
                System.exit(1);
            }

            url = url.replace("http://", "https://");

            if (stringScanner.hasNext()) {
                System.err.println("File \"files_urls.txt\" is incorrect");
                System.exit(1);
            }

            fileQueue.add(new MyFile(fileId, url));
        }
    }
}
