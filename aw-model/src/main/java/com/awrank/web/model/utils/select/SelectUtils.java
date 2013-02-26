package com.awrank.web.model.utils.select;

import com.awrank.web.model.domain.constant.ExtendedAbstractAuditableConst;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import com.awrank.web.model.utils.select.annotation.SelectSubQuery;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: a_polyakov
 */
public class SelectUtils {
	private static Map<Class, String> select_from = new HashMap<Class, String>();

	public static String getClauseSelectFrom(Class wrapperClass) {
		synchronized (select_from) {
			String result = select_from.get(wrapperClass);
			if (result == null) {
				StringBuilder query = new StringBuilder("SELECT ");
				query.append("new ");
				query.append(wrapperClass.getName());
				query.append("(");

				SelectFrom selectFromAnnotation = null;
				Constructor findConstructor = null;
				Constructor constructors[] = wrapperClass.getDeclaredConstructors();
				for (int i = 0; findConstructor == null && i < constructors.length; i++) {
					selectFromAnnotation = (SelectFrom) constructors[i].getAnnotation(SelectFrom.class);
					if (selectFromAnnotation != null) {
						findConstructor = constructors[i];
					}
				}

				int i = 0;
				for (Annotation[] ant : findConstructor.getParameterAnnotations()) {
					if (i > 0) {
						query.append(", ");
					}
					if (ant[0] instanceof SelectField) {
						SelectField field = (SelectField) ant[0];
						query.append(" o.").append(field.value());
					} else if (ant[0] instanceof SelectSubQuery) {
						SelectSubQuery subQuery = (SelectSubQuery) ant[0];
						query.append(" ").append(subQuery.value());
					} else {
						throw new IllegalArgumentException("Error annotation for create query in " + wrapperClass);
					}
					i++;
				}

				query.append(") ");
				query.append("FROM ");
				query.append(selectFromAnnotation.value().getSimpleName());
				query.append(" o ");
				result = query.toString();
				select_from.put(wrapperClass, result);
			}
			return result;
		}
	}

	public static <T> T getWrapper(EntityManager em, Class<T> wrapperClass, Long id) {
		String selectFrom = getClauseSelectFrom(wrapperClass);
		selectFrom += " where o." + ExtendedAbstractAuditableConst.H_ID + "=?1";
		Query query = em.createQuery(selectFrom);
		query.setParameter(1, id);
		T res = (T) query.getSingleResult();
		return res;
	}

	public static <T> List<T> getWrapperList(EntityManager em, Class<T> wrapperClass, String clauseWhereOrderBy, int firstResult, int maxResult) {
		String selectFrom = getClauseSelectFrom(wrapperClass);
		Query query = em.createQuery(selectFrom + clauseWhereOrderBy);
		if (firstResult > 0)
			query.setFirstResult(firstResult);
		if (maxResult > 0)
			query.setMaxResults(maxResult);
		List<T> res = query.getResultList();
		return res;
	}
}

