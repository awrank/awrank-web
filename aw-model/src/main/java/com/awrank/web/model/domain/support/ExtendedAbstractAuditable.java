package com.awrank.web.model.domain.support;

import com.awrank.web.model.domain.User;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;

/**
 * Extension of {@link AbstractAuditable} with overridden mapping and defined user.
 *
 * @author Eugene Solomka
 */
@AttributeOverrides({
		@AttributeOverride(name = "createdDate", column = @Column(name = "created_at", nullable = false, updatable = false)),
		@AttributeOverride(name = "lastModifiedDate", column = @Column(name = "last_updated_at", nullable = false))
})
@AssociationOverrides({
		@AssociationOverride(name = "createdBy", joinColumns = @JoinColumn(name = "created_by_user_id")),
		@AssociationOverride(name = "lastModifiedBy", joinColumns = @JoinColumn(name = "last_updated_by_user_id"))
})
@MappedSuperclass
public class ExtendedAbstractAuditable extends AbstractAuditable<User, Long> {
}
