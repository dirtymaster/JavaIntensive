package edu.school21.program;

import edu.school21.entities.User;
import edu.school21.dao.DAO;
import edu.school21.manager.OrmManager;

import java.sql.SQLException;

public class Program {
    private static DAO dao = DAO.getInstance();

    public static void main(String[] args) throws SQLException {
        OrmManager ormManager = new OrmManager(dao.getConnection(),
                "src/main/java/edu/school21/entities");

        User user1 = new User();
        user1.setAge(20);
        user1.setFirstName("hahaha");
        ormManager.save(user1);

        User user2 = new User();
        user2.setFirstName("hehehe");
        ormManager.save(user2);

        user1.setId(1L);
        user1.setLastName("hihihi");
        ormManager.update(user1);

        System.out.println(ormManager.findById(2L, User.class));
    }
}
