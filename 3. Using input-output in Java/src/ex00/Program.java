package ex00;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;

public class Program {
    private static final Map<String, String> signatures = new HashMap<>();
    private static int maxSignatureLength = 0;
    private static int minSignatureLength = (int) 1e9;
    private static Scanner fileScanner;
    private static PrintWriter printWriter;

    public static void main(String[] args) {
        readSignatures();

        readFiles();
        printWriter.close();
    }

    private static void readSignatures() {
        File signaturesFile = new File("src/ex00/signatures.txt");
        try {
            fileScanner = new Scanner(signaturesFile);
            fileScanner.useDelimiter(",");
        } catch (Exception e) {
            System.err.println("The file \"signatures.txt\" not found");
            fileScanner.close();
            System.exit(1);
        }
        if (signaturesFile.length() == 0) {
            fileScanner.close();
            System.err.println("The file \"signatures.txt\" is empty");
            System.exit(1);
        }
        while (true) {
            String extension;
            try {
                extension = fileScanner.next();
            } catch (Exception e) {
                break;
            }
            String bytesString = null;
            try {
                bytesString = fileScanner.nextLine().replace(" ", "")
                        .substring(1);
                if (bytesString.length() > maxSignatureLength) {
                    maxSignatureLength = bytesString.length();
                }
                if (bytesString.length() < minSignatureLength) {
                    minSignatureLength = bytesString.length();
                }
            } catch (Exception e) {
                fileScanner.close();
                System.err.println("Invalid signatures file");
                System.exit(1);
            }

            signatures.put(bytesString, extension);
        }
        maxSignatureLength /= 2;
    }

    private static void readFiles() {
        File resultFile = new File("src/ex00/result.txt");
        try {
            printWriter = new PrintWriter(resultFile);
        } catch (Exception e) {
            System.err.println("The file \"result.txt\" not found");
            System.exit(1);
        }
        Scanner scanner = new Scanner(System.in);
        String inputString;
        while (true) {
            inputString = scanner.nextLine();
            if (inputString.equals("42")) {
                scanner.close();
                fileScanner.close();
                printWriter.close();
                System.exit(0);
            }

            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(inputString);
            } catch (Exception e) {
                fileScanner.close();
                System.err.println("File not found");
                System.exit(1);
            }

            byte[] bytes = null;
            try {
                bytes = new byte[fileInputStream.available()];
                fileInputStream.read(bytes);
            } catch (Exception e) {
                fileScanner.close();
                printWriter.close();
                System.err.println("The file is incorrect");
                System.exit(1);
            }
            if (bytes.length < minSignatureLength) {
                fileScanner.close();
                System.err.println(bytes.length);
                System.err.println(minSignatureLength);
                System.err.println("The file is too small");
                System.exit(1);
            }

            int cnt = 0;
            StringBuilder fileSignature = new StringBuilder();
            for (byte aByte : bytes) {
                fileSignature.append(String.format("%02X", aByte));
                if (++cnt == maxSignatureLength)
                    break;
            }
            String definedSignature = null;
            for (String signature : signatures.keySet()) {
                if (signature.equals(fileSignature.substring(
                        0, signature.length()))) {
                    definedSignature = signatures.get(signature);
                    break;
                }
            }
            if (definedSignature == null) {
                System.out.println("UNDEFINED");
            } else {
                printWriter.println(definedSignature);
                System.out.println("PROCESSED");
            }
        }
    }
}
