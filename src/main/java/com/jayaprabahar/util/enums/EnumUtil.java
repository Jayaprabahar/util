package com.jayaprabahar.util.enums;

import java.util.Arrays;

/**
 * <p> Project : com.jayaprabahar.util.enums </p>
 * <p> Title : EnumUtil.java </p>
 * <p> Description: EnumUtil </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class EnumUtil {

	/**
	 * 
	 */
	private EnumUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String[] getEnumAsStringArray(Class<? extends Enum<?>> e) {
		return getEnumAsDelimittedString(e).split(", ");
	}

	public static String getEnumAsDelimittedString(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "");
	}

}
