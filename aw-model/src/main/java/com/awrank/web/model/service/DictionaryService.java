package com.awrank.web.model.service;

import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;

/**
 * Dictionary Service
 */
public interface DictionaryService extends AbstarctService {
	/**
	 * Returns paginated list of dictionary entries
	 */
	Iterable<Dictionary> findAll();

	/**
	 * Creates new dictionary entry
	 */
	Dictionary create(Dictionary dictionary) throws ObjectNotUniqueException, ObjectFieldException;

	/**
	 * Updates dictionary entry
	 */
	Dictionary update(Long id, Dictionary dictionary) throws ObjectFieldException, ObjectNotUniqueException;

	/**
	 * Deletes dictionary entry
	 */
	void delete(Long id) throws ObjectFieldException;

}
