package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotDeletedException;

/**
 * Interface for REST service working with entry points
 *
 * @author Olga Korokhina
 */
public interface EntryPointService extends AbstractService {

	void add(EntryPoint ep) throws EntryPointNotCreatedException;

	void delete(EntryPoint ep) throws EntryPointNotDeletedException;

	void save(EntryPoint ep);

	@Deprecated
	EntryPoint findOneByEntryPointTypeAndUid(EntryPointType type, String uid);

	/**
	 * Looks for {@code entry_point} record matching given {@code uid} value.
	 * @param uid unique identifier of user.
	 * @return Single instance or null.
	 */
	EntryPoint findOneByUid(String uid);

}
