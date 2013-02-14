package com.awrank.web.model.service;

import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Dictionary Service
 */
public interface DictionaryService {
    /**
     * Returns paginated list of dictionary entries
     */
    Page<Dictionary> findAll(Pageable pageable);

    /**
     * Creates new dictionary entry
     */
    Dictionary create(Dictionary dictionary) throws ObjectNotUniqueException, ObjectFieldException;

    /**
     * Updates dictionary entry
     */
    Dictionary update(Dictionary dictionary) throws ObjectFieldException;

    /**
     * Deletes dictionary entry
     */
    void delete(Long id);

}
