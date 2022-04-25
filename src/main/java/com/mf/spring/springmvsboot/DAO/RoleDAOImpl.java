package com.mf.spring.springmvsboot.DAO;

import com.mf.spring.springmvsboot.model.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private final EntityManager entityManager;

    public RoleDAOImpl(EntityManager em) {
        this.entityManager = em;
    }

    @Override
    public List<Role> getAllRoles() {
        return entityManager.createQuery("select r from Role r", Role.class).getResultList();
    }

    @Override
    public void saveRole(Role role) {
        entityManager.merge(role);
    }

    @Override
    public Role getRole(Long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        TypedQuery<Role> tq = entityManager.createQuery("select r from Role as r where r.role=:param", Role.class);
        return tq.setParameter("param", name).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = entityManager.getReference(Role.class, id);
        entityManager.remove(role);
    }
}
