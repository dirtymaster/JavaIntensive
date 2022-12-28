package edu.school21;

public class Game {
    public static void main(String[] args) {
        Main main = new Main(args);
        main.parseFile();
        main.setColors();
        main.generateField();
        while (true) {
            main.outputField();

            if (main.movePlayer()) {
                main.outputField();
                System.out.println("YOU WON!");
                break;
            }

            if (main.moveEnemies()) {
                main.outputField();
                System.out.println("YOU LOST!");
                break;
            }
        }
    }
}