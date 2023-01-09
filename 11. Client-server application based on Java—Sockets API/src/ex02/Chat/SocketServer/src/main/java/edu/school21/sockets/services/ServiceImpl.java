package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.Room;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.RoomsRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceImpl implements Service {
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final MessagesRepository messagesRepository;
    private final RoomsRepository roomsRepository;

    @Autowired
    public ServiceImpl(PasswordEncoder passwordEncoder,
                       UsersRepository usersRepository,
                       MessagesRepository messagesRepository,
                       RoomsRepository roomsRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.messagesRepository = messagesRepository;
        this.roomsRepository = roomsRepository;
    }

    @Override
    public void signUp(User user) {
        if (usersRepository.findByUsername(user.getUsername()).isPresent()) {
            System.err.println("User already exists");
            System.exit(1);
        }
        user.setEncryptedPassword(
                passwordEncoder.encode(user.getEncryptedPassword()));
        usersRepository.save(user);
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> opt = usersRepository.findByUsername(username);
        return opt.isPresent() && passwordEncoder.matches(password,
                opt.get().getEncryptedPassword());
    }

    @Override
    public void createMessage(String sender, String message, Long roomId) {
        messagesRepository.save(
                new Message(sender, message, null, roomId));
    }

    @Override
    public void createRoom(String name) {
        roomsRepository.save(new Room(null, name));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomsRepository.findAll();
    }

    @Override
    public List<Message> getMessagesByRoomId(Long roomId, int limit) {
        return messagesRepository.findByRoomId(roomId, limit);
    }

    @Override
    public Room getRoomById(Long roomId) {
        return roomsRepository.findById(roomId);
    }

    @Override
    public Room getRoomByName(String name) {
        return  roomsRepository.getRoomByName(name);
    }
}
