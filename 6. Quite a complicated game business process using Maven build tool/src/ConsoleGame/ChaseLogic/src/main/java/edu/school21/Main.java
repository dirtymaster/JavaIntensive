package edu.school21;

import com.beust.jcommander.JCommander;

public class Main {
    static ArgsParser argsParser = new ArgsParser();
    static FileParser fileParser = new FileParser();
    static Field field = new Field();
    static Printer printer = new Printer();
    static PlayerMover playerMover = new PlayerMover();
    static EnemiesMover enemiesMover = new EnemiesMover();

    public Main(String[] args) {
        JCommander.newBuilder().addObject(argsParser).build().parse(args);
    }

    public void parseFile() {
        fileParser.parseFile();
    }

    public void setColors() {
        printer.setColors();
    }

    public void generateField() {
        field.generateField();
    }

    public void outputField() {
        printer.outputField();
    }

    public boolean movePlayer() {
        return playerMover.movePlayer();
    }

    public boolean moveEnemies() {
        return enemiesMover.moveEnemies();
    }

//    public static void main(String[] args) {
//        while (true) {
//            printer.outputField();
//
//            if (playerMover.movePlayer()) {
//
//                System.out.println("YOU WON!");
//                break;
//            }
//
//            if (enemiesMover.moveEnemies()) {
//                printer.outputField();
//                System.out.println("YOU LOST!");
//                break;
//            }
//        }
//    }
}