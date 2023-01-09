package edu.school21;

public class Position {
    public int x;
    public int y;

    public Position(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public boolean equals(Position position) {
        return this.x == position.x && this.y == position.y;
    }
}
