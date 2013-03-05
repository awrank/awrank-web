package com.awrank.web.model.utils.select.annotation;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: a_polyakov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR})
public @interface SelectFrom {
	Class<? extends ExtendedAbstractAuditable> value();
}
