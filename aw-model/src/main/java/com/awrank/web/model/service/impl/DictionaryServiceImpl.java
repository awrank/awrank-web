package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;
import com.awrank.web.model.service.DictionaryService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryServiceImpl extends AbstractServiceImpl implements DictionaryService {
	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = true)
	public Iterable<Dictionary> findAll() {
		return dictionaryDao.findAll(new Sort("code"/*DictionaryConst.H_CODE*/, "language"/*DictionaryConst.H_LANGUAGE*/));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Dictionary create(Dictionary dictionary) throws ObjectNotUniqueException, ObjectFieldException {
		if (dictionary.getLanguage() == null)
			throw new ObjectFieldException(Message.MISSING_DICTIONARY_LANGUAGE, Dictionary.class,/*EObjectType.DICTIONARY,*/ null, dictionary.getId(), "language"/*DictionaryConst.S_LANGUAGE*/);
		if (StringUtils.isEmptyOrWhitespaceOnly(dictionary.getCode()))
			throw new ObjectFieldException(Message.MISSING_DICTIONARY_CODE, Dictionary.class,/*EObjectType.DICTIONARY, */null, dictionary.getId(), "code"/*DictionaryConst.S_CODE*/);
		if (StringUtils.isEmptyOrWhitespaceOnly(dictionary.getText()))
			throw new ObjectFieldException(Message.MISSING_DICTIONARY_TEXT, Dictionary.class,/*EObjectType.DICTIONARY,*/ null, dictionary.getId(), "text"/*DictionaryConst.S_TEXT*/);

		Dictionary existing = dictionaryDao.findByCodeAndLanguage(dictionary.getCode(), dictionary.getLanguage());

		if (existing != null)
			throw new ObjectNotUniqueException(Dictionary.class,/*EObjectType.DICTIONARY, */null, dictionary.getId(), null, existing.getId());

		return dictionaryDao.save(dictionary);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Dictionary update(Long id, Dictionary dictionary) throws ObjectFieldException, ObjectNotUniqueException {
		if (dictionary.getLanguage() == null)
			throw new ObjectFieldException(Message.MISSING_DICTIONARY_LANGUAGE, Dictionary.class,/*EObjectType.DICTIONARY,*/ null, id, "language"/*DictionaryConst.S_LANGUAGE*/);
		if (StringUtils.isEmptyOrWhitespaceOnly(dictionary.getCode()))
			throw new ObjectFieldException(Message.MISSING_DICTIONARY_CODE, Dictionary.class,/*EObjectType.DICTIONARY, */null, id, "code"/*DictionaryConst.S_CODE*/);
		if (StringUtils.isEmptyOrWhitespaceOnly(dictionary.getText()))
			throw new ObjectFieldException(Message.MISSING_DICTIONARY_TEXT, Dictionary.class,/*EObjectType.DICTIONARY, */null, id, "text"/*DictionaryConst.S_TEXT*/);

		Dictionary existing = dictionaryDao.findByCodeAndLanguage(dictionary.getCode(), dictionary.getLanguage());
		if (existing != null && !existing.getId().equals(id))
			throw new ObjectNotUniqueException(Dictionary.class,/*EObjectType.DICTIONARY,*/ null, id, null, existing.getId());

		if (existing == null) {
			existing = dictionaryDao.findOne(id);
		}
		existing.setLanguage(dictionary.getLanguage());
		existing.setCode(dictionary.getCode());
		existing.setText(dictionary.getText());

		return dictionaryDao.save(existing);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Long id) throws ObjectFieldException {
		if (id == null)
			throw new ObjectFieldException(Message.MISSING_OBJECT_ID, Dictionary.class,/*EObjectType.DICTIONARY,*/ null, id, "id"/*DictionaryConst.S_LANGUAGE*/);
		dictionaryDao.delete(id);
	}
}