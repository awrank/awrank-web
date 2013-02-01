package com.awrank.web.model.dao.user;

import com.awrank.web.model.dao.AbstractDaoImpl;
import com.awrank.web.model.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 * User: a_polyakov
 */
@Repository
public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {

    private static final String HQ_SELECT = "select o from " + User.class.getSimpleName() + " o " +
            "where o." + User.H_EMAIL + "=?1";

    @Override
    public User select(String email) {
        Query query = em.createQuery(HQ_SELECT);
        query.setParameter(1, email);
        User user = null;
        try {
            user = (User) query.getSingleResult();
        } catch (NoResultException e) {
        }
        return user;
    }
}
