package com.dotnomi.stranded.util;

import java.lang.reflect.Field;

public class FieldUtils {
	public static String getFieldName(Class<?> clazz, Object fieldReference) {
		for (Field field : clazz.getDeclaredFields()) {
			try {
				field.setAccessible(true);
				if (field.get(null) == fieldReference) {
					return field.getName();
				}
			} catch (IllegalAccessException ignored) {}
		}
		return null;
	}
}
