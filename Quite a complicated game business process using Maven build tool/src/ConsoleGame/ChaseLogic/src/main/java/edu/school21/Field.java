package edu.school21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Field {
    Cell[][] cells;
    int size;
    private int cellsCount;
    private final Random random = new Random();
    List<Cell.Type> types;
    Position playersPosition;
    Position positionOfTarget;

    public void generateField() {
        this.size = Main.argsParser.getSize();
        this.cellsCount = size * size;
        int enemiesCount = Main.argsParser.getEnemiesCount();
        int wallsCount = Main.argsParser.getWallsCount();
        if (enemiesCount < 0 || wallsCount < 0 || size > 50
                || 2 + enemiesCount + wallsCount > cellsCount
                || (!Main.argsParser.getProfile().equals("dev")
                && !Main.argsParser
                .getProfile().equals("production"))) {
            throw new IllegalParametersException("Need to specify arguments: " +
                    "--enemiesCount=% --wallsCount=% --size=% --profile=production/dev");
        }
        int nothingsCount = cellsCount - 1 - enemiesCount - wallsCount;
        cells = new Cell[size][size];

        types = new ArrayList<>(cellsCount);
        types.add(Cell.Type.player);
        for (int i = 0; i < enemiesCount; ++i) {
            types.add(Cell.Type.enemy);
        }
        for (int i = 0; i < wallsCount; ++i) {
            types.add(Cell.Type.wall);
        }
        for (int i = 0; i < nothingsCount; ++i) {
            types.add(Cell.Type.nothing);
        }

        Collections.shuffle(types);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                cells[i][j] = new Cell(i, j, types.get(i * size + j));
                if (types.get(i * size + j) == Cell.Type.player) {
                    playersPosition = new Position(i, j);
                }
            }
        }
        generateTarget();
        if (playersPosition.x == positionOfTarget.x
                && playersPosition.y == positionOfTarget.y) {
            generateField();
        }
    }

    private void generateTarget() {
        positionOfTarget = new Position(playersPosition);
        for (int i = 0; i < cellsCount * 2; ++i) {
            int direction = random.nextInt(4);
            switch (direction) {
                case 0:  // вверх
                    if (positionOfTarget.y != 0
                            && cells[positionOfTarget.y - 1][positionOfTarget.x]
                            .getType() == Cell.Type.nothing) {
                        positionOfTarget.y -= 1;
                    }
                    break;
                case 1:  // вниз
                    if (positionOfTarget.y != size - 1
                            && cells[positionOfTarget.y + 1][positionOfTarget.x]
                            .getType() == Cell.Type.nothing) {
                        positionOfTarget.y += 1;
                    }
                    break;
                case 2:  // влево
                    if (positionOfTarget.x != 0
                            && cells[positionOfTarget.y][positionOfTarget.x - 1]
                            .getType() == Cell.Type.nothing) {
                        positionOfTarget.x -= 1;
                    }
                    break;
                case 3:  // вправо
                    if (positionOfTarget.x != size - 1
                            && cells[positionOfTarget.y][positionOfTarget.x + 1]
                            .getType() == Cell.Type.nothing) {
                        positionOfTarget.x += 1;
                    }
                    break;
            }
        }
        cells[positionOfTarget.y]
                [positionOfTarget.x].setType(Cell.Type.target);
    }
}
