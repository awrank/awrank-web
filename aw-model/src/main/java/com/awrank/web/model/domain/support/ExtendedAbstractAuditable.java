package com.awrank.web.model.domain.support;

import com.awrank.web.model.domain.User;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Extension of {@ling AbstractAuditable} with overridden mapping and defined user.
 */
@AttributeOverrides({
        @AttributeOverride(name="createdDate", column=@Column(name="created_at")),
        @AttributeOverride(name="lastModifiedDate", column=@Column(name="last_updated_at"))
})
@AssociationOverrides({
        @AssociationOverride(name="createdBy", joinColumns=@JoinColumn(name="created_by_user_id")),
        @AssociationOverride(name="lastModifiedBy", joinColumns=@JoinColumn(name="last_updated_by_user_id"))

})
@MappedSuperclass
public class ExtendedAbstractAuditable<ID extends Serializable> extends AbstractAuditable<User, ID> {
}
