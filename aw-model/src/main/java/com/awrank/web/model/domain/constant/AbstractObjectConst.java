package com.awrank.web.model.domain.constant;

import com.awrank.web.model.utils.json.IJsonObject;

/**
 *
 */
public interface AbstractObjectConst extends IJsonObject {//extends Auditable<User, Long> {

    public static final String S_ID = "id";
    public static final String H_ID = "id";

    public static final String S_OBJECT_TYPE = "object_type";
    public static final String H_OBJECT_TYPE = "objectType";

    public static final String S_VERSION = "version";
    public static final String H_VERSION = "version";

    public static final String S_CREATED_DATE = "created_at";
    public static final String H_CREATED_DATE = "createdDate";

    public static final String S_CREATED_BY = "creator_id";
    public static final String H_CREATED_BY = "createdBy";

    public static final String S_LAST_MODIFIED_DATE = "updated_at";
    public static final String H_LAST_MODIFIED_DATE = "lastModifiedDate";

    public static final String S_LAST_MODIFIED_BY = "updater_id";
    public static final String H_LAST_MODIFIED_BY = "lastModifiedBy";

    public static final String S_ENDED_DATE = "ended_at";
    public static final String H_ENDED_DATE = "endedDate";

    public static final String SQL_PRICE_COLUMN_DEFINITION = "DECIMAL(8,2)";
}
