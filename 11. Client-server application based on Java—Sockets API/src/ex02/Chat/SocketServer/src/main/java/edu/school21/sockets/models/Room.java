package edu.school21.sockets.models;

public class Room {
    private Long id;
    private String name;

    public Room(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
