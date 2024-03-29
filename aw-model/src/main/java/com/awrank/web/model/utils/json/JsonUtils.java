package com.awrank.web.model.utils.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alex Polyakov
 */
public class JsonUtils {

	public static Long getLong(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		if (jsonNode.isLong())
			return jsonNode.longValue();
		String s = jsonNode.asText();
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

	public static Integer getInteger(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		if (jsonNode.isLong())
			return jsonNode.intValue();
		String s = jsonNode.asText();
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

	public static BigDecimal getBigDecimal(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		if (jsonNode.isBigDecimal())
			return jsonNode.decimalValue();
		String s = jsonNode.asText();
		s = s.replace(',', '.');
		s = s.trim();
		return (s.isEmpty()) ? null : new BigDecimal(s);
	}

	public static String getString(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		return jsonNode.asText();
	}

	public static boolean getBoolean(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return false;
		return jsonNode.booleanValue();
	}

	//---- Thought project we changed Date to joda's LocalDateTime, put attention here!

	public static Date getDate(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		return new Date(jsonNode.longValue());
	}

	public static <T extends Enum<T>> T getEnum(final ObjectNode jsonObject, final String key, Class<T> enumClass) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		return Enum.valueOf(enumClass, jsonNode.asText());
	}

	public static void set(final ObjectNode jsonObject, final String key, final Boolean value) {
		if (value == null) {
			jsonObject.putNull(key);
		} else {
			jsonObject.put(key, value);
		}
	}

	public static void set(final ObjectNode jsonObject, final String key, final Integer value) {
		if (value == null) {
			jsonObject.putNull(key);
		} else {
			jsonObject.put(key, value);
		}
	}

	public static void set(final ObjectNode jsonObject, final String key, final Long value) {
		if (value == null) {
			jsonObject.putNull(key);
		} else {
			jsonObject.put(key, value);
		}
	}

	public static void set(final ObjectNode jsonObject, final String key, final BigDecimal value) {
		if (value == null) {
			jsonObject.putNull(key);
		} else {
			jsonObject.put(key, value);
		}
	}

	public static void set(final ObjectNode jsonObject, final String key, final String value) {
		jsonObject.put(key, value);
	}

	public static void set(final ObjectNode jsonObject, final String key, final Enum value) {
		if (value == null) {
			jsonObject.putNull(key);
		} else {
			jsonObject.put(key, value.name());
		}
	}

	public static void set(final ObjectNode jsonObject, final String key, final Date value) {
		if (value == null) {
			jsonObject.putNull(key);
		} else {
			jsonObject.put(key, value.getTime());
		}
	}

//	public static void set(final ObjectNode jsonObject, final String key, final AbstractObject value) {
//		if (value == null) {
//			jsonObject.putNull(key);
//		} else {
//			jsonObject.put(key, value.getId());
//		}
//	}
}
