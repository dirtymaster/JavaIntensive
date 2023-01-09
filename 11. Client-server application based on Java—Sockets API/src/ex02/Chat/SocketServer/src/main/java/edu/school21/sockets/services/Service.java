package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.models.Room;

import java.util.List;

public interface Service {
    boolean signIn(String username, String password);
    void signUp(User user);
    void createMessage(String sender, String message, Long roomId);
    void createRoom(String name);
    List<Room> getAllRooms();
    List<Message> getMessagesByRoomId(Long roomId, int limit);
    Room getRoomById(Long roomId);
    Room getRoomByName(String name);
}
