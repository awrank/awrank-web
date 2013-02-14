package com.awrank.web.model.domain.support;

import java.io.Serializable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.awrank.web.model.domain.User;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractUserRelatedEntityAuditable<ID extends Serializable> extends
		ExtendedAbstractAuditable<ID> {
	
	 
    /**
     * User associated with record.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
