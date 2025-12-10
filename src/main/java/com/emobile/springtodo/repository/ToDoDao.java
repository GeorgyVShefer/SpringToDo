package com.emobile.springtodo.repository;

import com.emobile.springtodo.model.ToDo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;


public class ToDoDao {

    private SessionFactory sessionFactory;

    public ToDoDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Long save(ToDo todo) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Long id = null;

        try {
            tx = session.beginTransaction();

            todo.setCreatedAt(LocalDateTime.now());
            todo.setUpdatedAt(LocalDateTime.now());

            id = (Long) session.save(todo);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return id;
    }

    public ToDo getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ToDo.class, id);
        }
    }

    public ToDo update(ToDo todo) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            todo.setUpdatedAt(LocalDateTime.now());

            session.update(todo);

            tx.commit();
            return todo;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void deleteById(Long id) {
        Transaction tx = null;

        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            ToDo todo = session.get(ToDo.class, id);
            if (todo != null) {
                session.delete(todo);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
