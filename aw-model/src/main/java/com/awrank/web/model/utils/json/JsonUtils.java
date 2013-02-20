package com.awrank.web.model.utils.json;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * User: a_polyakov
 */
public class JsonUtils {

	public static Long getLong(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		if (jsonNode.isLong())
			return jsonNode.getLongValue();
		String s = jsonNode.getValueAsText();
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
			return jsonNode.getIntValue();
		String s = jsonNode.getValueAsText();
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
			return jsonNode.getDecimalValue();
		String s = jsonNode.getValueAsText();
		s = s.replace(',', '.');
		s = s.trim();
		return (s.isEmpty()) ? null : new BigDecimal(s);
	}

	public static String getString(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		return jsonNode.getValueAsText();
	}

	public static boolean getBoolean(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return false;
		return jsonNode.getBooleanValue();
	}

	//---- Thought project we changed Date to joda's LocalDateTime, put attention here!
	
	public static Date getDate(final ObjectNode jsonObject, final String key) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		return new Date(jsonNode.getLongValue());
	}

	public static <T extends Enum<T>> T getEnum(final ObjectNode jsonObject, final String key, Class<T> enumClass) {
		final JsonNode jsonNode = jsonObject.get(key);
		if (jsonNode == null || jsonNode.isNull())
			return null;
		return Enum.valueOf(enumClass, jsonNode.getTextValue());
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
