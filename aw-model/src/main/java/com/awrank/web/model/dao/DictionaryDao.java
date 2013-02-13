package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Dictionary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The OrderDao is a data-centric service for the {@link Dictionary} entry.
 * <p/>
 * It provides the basic methods to get/delete a {@link Dictionary} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface DictionaryDao extends PagingAndSortingRepository<Dictionary, Long> {
    @Query("select d from Dictionary d where d.code = :code and d.language = :language")
    public Dictionary findByCodeAndLanguage(@Param("code") String code, @Param("language") String language);
}
