package edu.school21;

public class Cell {
    private final Position position;

    public enum Type {
        player, wall, enemy, target, nothing
    }

    private Type type;

    public Cell(int yIndex, int xIndex, Type type) {
        position = new Position(yIndex, xIndex);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }
}
