package ex04.users;

public class UserIdsGenerator {
    private static Integer lastGeneratedId = 0;

    private static final UserIdsGenerator instance = new UserIdsGenerator();

    private UserIdsGenerator() {
    }

    public static UserIdsGenerator getInstance() {
        return instance;
    }

    public int generateId() {
        return ++lastGeneratedId;
    }
}
