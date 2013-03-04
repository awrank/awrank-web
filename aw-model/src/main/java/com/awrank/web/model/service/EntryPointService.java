package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotDeletedException;

import java.util.List;

/**
 * Interface for REST service working with entry points
 *
 * @author Olga Korokhina
 */
//@Service
public interface EntryPointService {

	void add(EntryPoint ep) throws EntryPointNotCreatedException;

	void delete(EntryPoint ep) throws EntryPointNotDeletedException;

	void save(EntryPoint ep);

	List<EntryPoint> findEntryPointForUser(User user);

	/**
	 *  Returns only active! ideally single one OR empty list
	 */
	List<EntryPoint> findEntryPointForUserByType(User user, EntryPointType type);

	List<EntryPoint> findEntryPointForUserByTypeAndPassword(User user, EntryPointType type, String password);

	String findPasswordForUserByType(User user, EntryPointType type);

	List<EntryPoint> findForUserByTypeAndUID(User user, EntryPointType type, String uid);

}
