package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserDao user = new UserDaoHibernateImpl();
        user.createUsersTable();

        user.saveUser("Petya", "Petrov", (byte) 9);
        user.saveUser("Pavlik", "Morozov", (byte) 12);
        user.saveUser("Vasia", "Pupkin", (byte) 99);
        user.saveUser("Alex", "Ivanov", (byte) 50);

        for (User s : user.getAllUsers()) {
            System.out.println(s);
        }

        user.cleanUsersTable();
        user.dropUsersTable();
    }
}