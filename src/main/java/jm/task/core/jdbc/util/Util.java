package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    // "?serverTimezone=UTC" - эта запись для установки часового пояса MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/idea?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getMySQLConnection() {

        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Соединение с БД не установлено");
            e.printStackTrace();
        }
        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null ) {
            try {
                Properties properties = new Properties();
                Configuration configuration = new Configuration();

                properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
                properties.setProperty("hibernate.hbm2ddl", "update");
                properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
                properties.setProperty("hibernate.connection.username", "root");
                properties.setProperty("hibernate.connection.password", "");
                properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/idea?allowPublicKeyRetrieval=true&serverTimezone=UTC");
                properties.setProperty("hibernate.show_sql", "true");
                properties.setProperty("hibernate.current_session_context_class", "thread");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                System.out.println("Couldn't create session factory!");
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}

