package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.Language;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code DictionaryDao} is a data-centric service for the {@link Dictionary} entry.
 * <p/>
 * It provides the basic methods to get/delete a {@link Dictionary} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface DictionaryDao extends PagingAndSortingRepository<Dictionary, Long> {

	@Query("select d from Dictionary d where d.language = :language and d.code = :code")
	public Dictionary findOneByLanguageAndCode(@Param("language") Language language, @Param("code") String code);

	@Query("select d.text from Dictionary d where d.language = :language and d.code = :code")
	public String getTextByLanguageAndCode(@Param("language") Language language, @Param("code") String code);
}
