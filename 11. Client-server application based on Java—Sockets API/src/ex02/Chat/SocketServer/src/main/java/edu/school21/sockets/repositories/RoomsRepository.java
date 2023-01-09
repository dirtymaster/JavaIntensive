package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Room;

public interface RoomsRepository extends CrudRepository<Room> {
    Room getRoomByName(String name);
}
