package edu.school21;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;

public class Printer {
    private ColoredPrinter playerPrinter;
    private ColoredPrinter wallPrinter;
    private ColoredPrinter enemyPrinter;
    private ColoredPrinter targetPrinter;
    private ColoredPrinter nothingPrinter;

    public void setColors() {
        try {
            playerPrinter
                    = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(
                            Main.fileParser.getPlayerColor())).build();
            wallPrinter
                    = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(
                            Main.fileParser.getWallColor())).build();
            enemyPrinter
                    = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(
                            Main.fileParser.getEnemyColor())).build();
            targetPrinter
                    = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(
                            Main.fileParser.getTargetColor())).build();
            nothingPrinter
                    = new ColoredPrinter.Builder(1, false)
                    .background(Ansi.BColor.valueOf(
                            Main.fileParser.getNothingColor())).build();
        } catch (Exception e) {
            throw new IllegalParametersException("Invalid color in properties file");
        }
    }

    public void outputField() {
        if (Main.argsParser.getProfile().equals("production")) {
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
        System.out.println();
        for (Cell[] cellsArray : Main.field.cells) {
            for (Cell cell : cellsArray) {
                switch (cell.getType()) {
                    case player:
                        playerPrinter.print(Main.fileParser.getPlayerChar());
                        break;
                    case wall:
                        wallPrinter.print(Main.fileParser.getWallChar());
                        break;
                    case enemy:
                        enemyPrinter.print(Main.fileParser.getEnemyChar());
                        break;
                    case target:
                        targetPrinter.print(Main.fileParser.getTargetChar());
                        break;
                    case nothing:
                        nothingPrinter.print(Main.fileParser.getNothingChar());
                }
            }
            System.out.println();
        }
    }
}
