package com.awrank.web.model.constant;

import java.util.Date;

/**
 * User: a_polyakov
 */
public interface DateConst {
    /**
     * дата далеко в прошлом 01.01.1970 00:00:00.000
     */
    public static final Date SMALL_DATE = new Date(0L);
    /**
     * дата далеко в будущем 31.12.2099 23:59:59.999
     */
    public static final Date BIG_DATE = new Date(4102437599999L);
}
