package edu.school21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EnemiesMover {
    private final Random random = new Random();
    private final List<Position> newEnemiesPositions = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    public enum Direction {
        up, down, left, right,
    }

    // возвращает true, если игрок проиграл
    public boolean moveEnemies() {
        newEnemiesPositions.clear();
        for (int i = 0; i < Main.field.size; ++i) {
            for (int j = 0; j < Main.field.size; ++j) {
                if (Main.field.cells[i][j].getType() == Cell.Type.enemy) {
                    Position enemyPosition
                            = new Position(Main.field.cells[i][j].getPosition());
                    Direction verticalDirection = null;
                    Direction horizontalDirection = null;
                    if (enemyPosition.y > Main.field.playersPosition.y) {
                        verticalDirection = Direction.up;
                    } else if (enemyPosition.y < Main.field.playersPosition.y) {
                        verticalDirection = Direction.down;
                    }
                    if (enemyPosition.x > Main.field.playersPosition.x) {
                        horizontalDirection = Direction.left;
                    } else if (enemyPosition.x < Main.field.playersPosition.x) {
                        horizontalDirection = Direction.right;
                    }

                    if (verticalDirection == null) {
                        moveHorizontally(enemyPosition,
                                horizontalDirection);
                    } else if (horizontalDirection == null) {
                        moveVertically(enemyPosition, verticalDirection);
                    } else {
                        if (random.nextBoolean()) {
                            if (!moveHorizontally(enemyPosition,
                                    horizontalDirection)) {
                                moveVertically(enemyPosition,
                                        verticalDirection);
                            }
                        } else {
                            if (!moveVertically(enemyPosition,
                                    verticalDirection)) {
                                moveHorizontally(enemyPosition,
                                        horizontalDirection);
                            }
                        }
                    }
                }
            }
        }
        boolean playerLost = false;
        for (Position position : newEnemiesPositions) {
            if (position.equals(Main.field.playersPosition)) {
                playerLost = true;
            }
            Main.field.cells[position.y][position.x].setType(Cell.Type.enemy);
            if (Main.argsParser.getProfile().equals("dev")) {
                String line;
                do {
                    System.out.println("Enter 8 to see the enemy's move");
                    line = scanner.nextLine();
                } while (line.length() != 1 && line.charAt(0) != '8');
                Main.printer.outputField();
            }
        }
        return playerLost;
    }

    private boolean moveVertically(Position enemyPosition, Direction direction) {
        if (direction == Direction.up) {
            Cell.Type upperCellType = Main.field.cells
                    [enemyPosition.y - 1][enemyPosition.x].getType();
            if (upperCellType == Cell.Type.nothing
                    || upperCellType == Cell.Type.player) {
                for (Position position : newEnemiesPositions) {
                    if (position.y == enemyPosition.y - 1
                            && position.x == enemyPosition.x) {
                        return false;
                    }
                }
                Main.field.cells[enemyPosition.y][enemyPosition.x]
                        .setType(Cell.Type.nothing);
                newEnemiesPositions.add(
                        new Position(enemyPosition.y - 1, enemyPosition.x));
                return true;
            }
        } else {
            Cell.Type lowerCellType = Main.field.cells
                    [enemyPosition.y + 1][enemyPosition.x].getType();
            if (lowerCellType == Cell.Type.nothing
                    || lowerCellType == Cell.Type.player) {
                for (Position position : newEnemiesPositions) {
                    if (position.y == enemyPosition.y + 1
                            && position.x == enemyPosition.x) {
                        return false;
                    }
                }
                Main.field.cells[enemyPosition.y][enemyPosition.x]
                        .setType(Cell.Type.nothing);
                newEnemiesPositions.add(
                        new Position(enemyPosition.y + 1, enemyPosition.x));
                return true;
            }
        }
        return false;
    }

    private boolean moveHorizontally(Position enemyPosition,
                                     Direction direction) {
        if (direction == Direction.left) {
            Cell.Type leftCellType = Main.field.cells
                    [enemyPosition.y][enemyPosition.x - 1].getType();
            if (leftCellType == Cell.Type.nothing
                    || leftCellType == Cell.Type.player) {
                for (Position position : newEnemiesPositions) {
                    if (position.y == enemyPosition.y
                            && position.x == enemyPosition.x - 1) {
                        return false;
                    }
                }
                Main.field.cells[enemyPosition.y][enemyPosition.x]
                        .setType(Cell.Type.nothing);
                newEnemiesPositions.add(
                        new Position(enemyPosition.y, enemyPosition.x - 1));
                return true;
            }
        } else {
            Cell.Type rightCellType = Main.field.cells
                    [enemyPosition.y][enemyPosition.x + 1].getType();
            if (rightCellType == Cell.Type.nothing
                    || rightCellType == Cell.Type.player) {
                for (Position position : newEnemiesPositions) {
                    if (position.y == enemyPosition.y
                            && position.x == enemyPosition.x + 1) {
                        return false;
                    }
                }
                Main.field.cells[enemyPosition.y][enemyPosition.x]
                        .setType(Cell.Type.nothing);
                newEnemiesPositions.add(
                        new Position(enemyPosition.y, enemyPosition.x + 1));
                return true;
            }
        }
        return false;
    }
}
