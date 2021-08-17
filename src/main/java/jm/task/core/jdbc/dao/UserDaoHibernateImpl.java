package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (\n" +
                    "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                    "  name VARCHAR(45) NOT NULL,\n" +
                    "  lastName VARCHAR(45) NOT NULL,\n" +
                    "  age TINYINT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (id))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;")
                    .addEntity(User.class);

            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Couldn't create users table");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Query query = session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class);

            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Couldn't delete table");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            System.out.println("User " + name + " был добавлен в таблицу.");

            session.save(user);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Couldn't save new user!");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Query remove = session.createQuery("DELETE FROM User users WHERE users.id=" + id);

            remove.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Couldn't remove user by id!");
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();

        return session.createQuery("FROM " + User.class.getSimpleName()).list();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            session.createQuery("DELETE FROM User users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Couldn't clear users table!");
        }
    }
}