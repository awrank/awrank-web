package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotDeletedException;

/**
 * Interface for REST service working with entry points
 *
 * @author Olga Korokhina
 *         Interface for REST service working with entry points
 */
public interface EntryPointService extends AbstarctService {

	public void add(EntryPoint ep) throws EntryPointNotCreatedException;

	public void delete(EntryPoint ep) throws EntryPointNotDeletedException;

	public void save(EntryPoint ep);

	public EntryPoint findOneByEntryPointTypeAndUid(EntryPointType type, String uid);

}
