package edu.school21;

import java.util.Scanner;

public class PlayerMover {
    private final Scanner scanner = new Scanner(System.in);
    private Position newPlayerPosition;

    public boolean movePlayer() {
        String line;
        do {
            System.out.println("Enter your move (w, a, s, d)");
            line = scanner.nextLine();
        } while (line.length() != 1);

        switch (line) {
            case "2":
            case "w":
                if (Main.field.playersPosition.y != 0
                        && (Main.field.cells[Main.field.playersPosition.y - 1]
                        [Main.field.playersPosition.x]
                        .getType() == Cell.Type.nothing
                        || Main.field.cells[Main.field.playersPosition.y - 1]
                        [Main.field.playersPosition.x]
                        .getType() == Cell.Type.target)) {
                    newPlayerPosition = new Position(
                            Main.field.playersPosition.y - 1,
                            Main.field.playersPosition.x);
                }
                break;
            case "4":
            case "s":
                if (Main.field.playersPosition.y != Main.field.size - 1
                        && (Main.field.cells[Main.field.playersPosition.y + 1]
                        [Main.field.playersPosition.x]
                        .getType() == Cell.Type.nothing
                        || Main.field.cells[Main.field.playersPosition.y + 1]
                        [Main.field.playersPosition.x]
                        .getType() == Cell.Type.target)) {
                    newPlayerPosition = new Position(
                            Main.field.playersPosition.y + 1,
                            Main.field.playersPosition.x);
                }
                break;
            case "1":
            case "a":
                if (Main.field.playersPosition.x != 0
                        && (Main.field.cells[Main.field.playersPosition.y]
                        [Main.field.playersPosition.x - 1]
                        .getType() == Cell.Type.nothing
                        || Main.field.cells
                        [Main.field.playersPosition.y]
                        [Main.field.playersPosition.x - 1]
                        .getType() == Cell.Type.target)) {
                    newPlayerPosition = new Position(
                            Main.field.playersPosition.y,
                            Main.field.playersPosition.x - 1);
                }
                break;
            case "3":
            case "d":
                if (Main.field.playersPosition.x != Main.field.size - 1
                        && (Main.field.cells[Main.field.playersPosition.y]
                        [Main.field.playersPosition.x + 1]
                        .getType() == Cell.Type.nothing
                        || Main.field.cells
                        [Main.field.playersPosition.y]
                        [Main.field.playersPosition.x + 1]
                        .getType() == Cell.Type.target)) {
                    newPlayerPosition = new Position(
                            Main.field.playersPosition.y,
                            Main.field.playersPosition.x + 1);
                }
                break;
            case "9":
                System.out.println("YOU LOST!");
                System.exit(0);
            default:
                return movePlayer();
        }
        Main.field.cells[Main.field.playersPosition.y]
                [Main.field.playersPosition.x].setType(Cell.Type.nothing);
        Main.field.cells[newPlayerPosition.y]
                [newPlayerPosition.x].setType(Cell.Type.player);
        Main.field.playersPosition.y = newPlayerPosition.y;
        Main.field.playersPosition.x = newPlayerPosition.x;

        return newPlayerPosition.equals(Main.field.positionOfTarget);
    }
}
