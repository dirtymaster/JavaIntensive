package edu.school21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class FileParser {
    private char enemyChar;
    private char playerChar;
    private char wallChar;
    private char targetChar;
    private char nothingChar;
    private String enemyColor;
    private String playerColor;
    private String wallColor;
    private String targetColor;
    private String nothingColor;
    private Scanner scanner;

    public void parseFile() {
        File file;
        if (Objects.equals(Main.argsParser.getProfile(),
                "production")) {
            file = new File(
                    "resources/application-production.properties");
        } else {
            file = new File(
                    "resources/application-dev.properties");
        }
        try {
            scanner = new Scanner(
                    file);
        } catch (FileNotFoundException e) {
            System.err.println("File " + file.getAbsolutePath() + " not found");
            e.printStackTrace();
            System.exit(1);
        }
        String line = null;
        for (int i = 1; i <= 10; ++i) {
            try {
                line = scanner.nextLine();
            } catch (Exception e) {
                System.err.println("Error during parsing file " + file.getAbsolutePath());
                e.printStackTrace();
                scanner.close();
                System.exit(1);
            }
            boolean fail = false;
            switch (i) {
                case 1:
                    if (!line.startsWith("enemy.char = ")
                            || line.length() != 14) {
                        fail = true;
                    } else {
                        enemyChar = line.charAt(line.length() - 1);
                    }
                    break;
                case 2:
                    if (!line.startsWith("player.char = ")
                            || line.length() != 15) {
                        fail = true;
                    } else {
                        playerChar = line.charAt(line.length() - 1);
                    }
                    break;
                case 3:
                    if (!line.startsWith("wall.char = ")
                            || line.length() != 13) {
                        fail = true;
                    } else {
                        wallChar = line.charAt(line.length() - 1);
                    }
                    break;
                case 4:
                    if (!line.startsWith("goal.char = ")
                            || line.length() != 13) {
                        fail = true;
                    } else {
                        targetChar = line.charAt(line.length() - 1);
                    }
                    break;
                case 5:
                    if (!line.startsWith("empty.char = ")
                            || line.length() != 14) {
                        fail = true;
                    } else {
                        nothingChar = line.charAt(line.length() - 1);
                    }
                    break;
                case 6:
                    if (!line.startsWith("enemy.color = ")
                            || line.length() < 15) {
                        fail = true;
                    } else {
                        enemyColor = line.substring(14);
                    }
                    break;
                case 7:
                    if (!line.startsWith("player.color = ")
                            || line.length() < 16) {
                        fail = true;
                    } else {
                        playerColor = line.substring(15);
                    }
                    break;
                case 8:
                    if (!line.startsWith("wall.color = ")
                            || line.length() < 14) {
                        fail = true;
                    } else {
                        wallColor = line.substring(13);
                    }
                    break;
                case 9:
                    if (!line.startsWith("goal.color = ")
                            || line.length() < 14) {
                        fail = true;
                    } else {
                        targetColor = line.substring(13);
                    }
                    break;
                case 10:
                    if (!line.startsWith("empty.color = ")
                            || line.length() < 15) {
                        fail = true;
                    } else {
                        nothingColor = line.substring(14);
                    }
                    break;
            }
            if (fail) {
                System.err.println("Invalid file");
                System.exit(1);
            }
        }
        scanner.close();
    }

    public char getEnemyChar() {
        return enemyChar;
    }

    public char getPlayerChar() {
        return playerChar;
    }

    public char getWallChar() {
        return wallChar;
    }

    public char getTargetChar() {
        return targetChar;
    }

    public char getNothingChar() {
        return nothingChar;
    }

    public String getEnemyColor() {
        return enemyColor;
    }

    public String getPlayerColor() {
        return playerColor;
    }

    public String getWallColor() {
        return wallColor;
    }

    public String getTargetColor() {
        return targetColor;
    }

    public String getNothingColor() {
        return nothingColor;
    }
}
