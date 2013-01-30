package com.awrank.web.model.utils.select.annotation;

import com.awrank.web.model.domain.AbstractObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR})
public @interface SelectFrom {
    Class<? extends AbstractObject> value();
}
