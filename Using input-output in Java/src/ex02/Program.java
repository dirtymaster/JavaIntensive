package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Program {
    private static File currentDirectory;
    private static final Scanner inputScanner = new Scanner(System.in);
    private static String command = "";
    private static Scanner stringScanner;

    public static void main(String[] args) {
        init(args);

        endlessCycle();
    }

    private static void init(String[] args) {
        if (args.length != 1 || args[0].length() < 18
                || !args[0].startsWith("--current-folder=")) {
            System.err.println("Invalid arguments");
            System.exit(1);
        }
        String mainFolderPath = args[0].substring(17);
        File mainDirectory = new File(mainFolderPath);
        if (!mainDirectory.exists()) {
            System.err.println("The folder does not exist");
            System.exit(1);
        }
        if (!mainDirectory.isDirectory()) {
            System.err.println("The file is not a directory");
            System.exit(1);
        }
        currentDirectory = mainDirectory;
        System.out.println(mainFolderPath);
    }

    private static void endlessCycle() {
        while (true) {
            command = inputScanner.nextLine();
            if (command.equals("exit")) {
                inputScanner.close();
                if (stringScanner != null) {
                    stringScanner.close();
                }
                System.exit(0);
            }
            switch (command.substring(0, 2)) {
                case "ls":
                    ls();
                    break;
                case "mv":
                    mv();
                    break;
                case "cd":
                    cd();
                    break;
                default:
                    reportInvalidCommandAndExit();
                    break;
            }
        }
    }

    private static void ls() {
        if (command.length() > 2) {
            reportInvalidCommandAndExit();
        }
        File[] fileList = currentDirectory.listFiles();
        if (fileList == null) {
            System.err.println("The directory is deleted");
            System.exit(1);
        }
        for (File file : fileList) {
            try {
                System.out.println(file.getName() + " "
                        + Files.size(file.toPath()) / 1024 + " KB");
            } catch (IOException ignored) {
            }
        }
    }

    private static void mv() {
        if (command.length() < 6) {
            reportInvalidCommandAndExit();
        }
        stringScanner = new Scanner(command.substring(3));
        if (!stringScanner.hasNext()) {
            reportInvalidCommandAndExit();
        }
        String fileName = stringScanner.next();
        if (!stringScanner.hasNext()) {
            reportInvalidCommandAndExit();
        }
        String newFileName = stringScanner.next();
        if (stringScanner.hasNext()) {
            reportInvalidCommandAndExit();
        }
        if (fileName.charAt(0) == '/'
                || (fileName.startsWith("~/"))) {
            System.err.println("Absolute paths are not supported");
            System.exit(1);
        }
        fileName = currentDirectory + "/" + fileName;
        File file = new File(fileName);
        if (!file.exists()) {
            System.err.println("Invalid file");
            System.exit(1);
        }
        if (newFileName.charAt(0) == '/'
                || newFileName.startsWith("~/")) {
            System.err.println("Absolute paths are not supported");
            System.exit(1);
        }
        newFileName = currentDirectory + "/" + newFileName;
        File newFile = new File(newFileName);
        if (newFile.exists() && newFile.isDirectory()) {
            if (!file.renameTo(
                    new File(newFile + "/" + file.getName()))) {
                System.err.println("Moving failed");
            }
        } else {
            if (!file.renameTo(new File(newFileName))) {
                System.err.println("Renaming failed");
            }
        }
    }

    private static void cd() {
        if (command.length() < 4) {
            reportInvalidCommandAndExit();
        }
        stringScanner = new Scanner(command.substring(3));
        if (!stringScanner.hasNext()) {
            reportInvalidCommandAndExit();
        }
        String directoryName = stringScanner.next();
        directoryName = currentDirectory + "/" + directoryName;
        File directory = new File(directoryName);
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("The directory does not exist");
            System.exit(1);
        }
        currentDirectory = directory;
        System.out.println(currentDirectory);
    }

    private static void reportInvalidCommandAndExit() {
        System.err.println("Invalid command");
        System.exit(1);
    }
}
