package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotDeletedException;

import java.util.List;

/**
 * @author Olga Korokhina
 *         Interface for REST service working with entry points
 */
//@Service
public interface EntryPointService {

	public void add(EntryPoint ep) throws EntryPointNotCreatedException;

	public void delete(EntryPoint ep) throws EntryPointNotDeletedException;

	public void save(EntryPoint ep);

	public List<EntryPoint> findEntryPointForUser(User user);

	/**
	 *  Returns only active! idealy single one OR empty list
	 */
	public List<EntryPoint> findEntryPointForUserByEntryPointType(User user, EntryPointType type);

	public List<EntryPoint> findEntryPointForUserByEntryPointTypeAndPassword(User user, EntryPointType type, String password);

	public String findPasswordForUserByEntryPointType(User user, EntryPointType type);
	
}
