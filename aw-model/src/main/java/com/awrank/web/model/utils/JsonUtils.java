package com.awrank.web.model.utils;

import com.awrank.web.model.domain.AbstractObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 */
public class JsonUtils {

    public static Long getLong(final JsonObject jsonObject, final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null || jsonElement.isJsonNull())
            return null;
        String s = jsonElement.getAsString();
        int index = s.indexOf('.');
        if (index >= 0) {
            s = s.substring(0, index);
        } else {
            index = s.indexOf(',');
            if (index >= 0) {
                s = s.substring(0, index);
            }
        }
        return (s.isEmpty()) ? null : Long.valueOf(s);
    }

    public static Integer getInteger(final JsonObject jsonObject, final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null || jsonElement.isJsonNull())
            return null;
        String s = jsonElement.getAsString();
        int index = s.indexOf('.');
        if (index >= 0) {
            s = s.substring(0, index);
        } else {
            index = s.indexOf(',');
            if (index >= 0) {
                s = s.substring(0, index);
            }
        }
        return (s.isEmpty()) ? null : Integer.valueOf(s);
    }

    public static BigDecimal getBigDecimal(final JsonObject jsonObject, final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null || jsonElement.isJsonNull())
            return null;
        String s = jsonElement.getAsString();
        s = s.replace(',', '.');
        s = s.trim();
        return (s.isEmpty()) ? null : new BigDecimal(s);
    }

    public static String getString(final JsonObject jsonObject, final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null || jsonElement.isJsonNull())
            return null;
        return jsonElement.getAsString();
    }

    //TODO proverit
    public static boolean getBoolean(final JsonObject jsonObject, final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null || jsonElement.isJsonNull())
            return false;
        return ("Y".equals(jsonElement.getAsString())) ? true : jsonElement.getAsBoolean();
    }

    public static Date getDate(final JsonObject jsonObject, final String key) {
        final JsonElement jsonElement = jsonObject.get(key);
        if (jsonElement == null || jsonElement.isJsonNull())
            return null;
        return new Date(jsonElement.getAsLong());
    }

    public static void set(final JsonObject jsonObject, final String key, final Boolean value) {
        jsonObject.addProperty(key, value);
    }

    public static void set(final JsonObject jsonObject, final String key, final Number value) {
        jsonObject.addProperty(key, value);
    }

    public static void set(final JsonObject jsonObject, final String key, final String value) {
        jsonObject.addProperty(key, value);
    }

    public static void set(final JsonObject jsonObject, final String key, final Enum value) {
        jsonObject.addProperty(key, (value != null) ? value.name() : null);
    }

    public static void set(final JsonObject jsonObject, final String key, final Date value) {
        jsonObject.addProperty(key, (value != null) ? value.getTime() : null);
    }

    public static void set(final JsonObject jsonObject, final String key, final AbstractObject value) {
        jsonObject.addProperty(key, (value != null) ? value.getId() : null);
    }
}
