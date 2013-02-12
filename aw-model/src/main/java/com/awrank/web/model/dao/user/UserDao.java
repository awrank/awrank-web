package com.awrank.web.model.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.awrank.web.model.dao.AbstractDao;
import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;

/**
 * User: a_polyakov
 */
@Repository
public interface UserDao extends AbstractDao<User> {
    public User select(String email);
    
}
