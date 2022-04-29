package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.UtilHibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private void applyQuery(String sql) {
        try (Session session = UtilHibernate.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            NativeQuery query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        applyQuery("CREATE TABLE IF NOT EXISTS `users` " +
                "(`ID` BIGINT NOT NULL AUTO_INCREMENT," +
                "`Name` VARCHAR(100) NULL," +
                "`LastName` VARCHAR(100) NULL," +
                " `Age` SMALLINT NULL," +
                "  PRIMARY KEY (`ID`))");
    }

    @Override
    public void dropUsersTable() {
        applyQuery("DROP TABLE IF EXISTS `users`");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        User user = new User(name, lastName, age);
        Transaction transaction = null;

        try (Session session = UtilHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session = UtilHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {

        Transaction transaction = null;
        List<User> users = new ArrayList<>();

        try (Session session = UtilHibernate.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            users = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        applyQuery("DELETE FROM `users`");
    }
}
