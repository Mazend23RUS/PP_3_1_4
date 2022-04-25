package com.mf.spring.springmvsboot.DAO;

import com.mf.spring.springmvsboot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    public UserDAOImpl(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select distinct u from User u left join fetch u.roles", User.class).getResultList();
    }

    @Override
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUser(Long id) {
        TypedQuery<User> tq = entityManager.createQuery("select distinct u from User u left join fetch u.roles WHERE u.id=:param", User.class);
        return Optional.of(tq.setParameter("param", id).getSingleResult()).orElse(null);
    }

    @Override
    public void deleteUser(Long id) throws EntityNotFoundException {
        User user = entityManager.getReference(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public User getUserByUsername(String name) {
        TypedQuery<User> tq = entityManager.createQuery("select distinct u from User u left join fetch u.roles WHERE u.username=:param", User.class);
        return tq.setParameter("param", name).getSingleResult();
    }
}
