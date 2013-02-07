package com.awrank.web.model.dao.user;

import com.awrank.web.model.dao.AbstractDao;
import com.awrank.web.model.domain.User;

/**
 * User: a_polyakov
 */
public interface UserDao extends AbstractDao<User> {
    public User select(String email);
}
