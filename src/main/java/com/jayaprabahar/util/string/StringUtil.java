package com.jayaprabahar.util.string;

import org.apache.commons.lang3.StringUtils;

/**
 * <p> Project : com.jayaprabahar.util.string </p>
 * <p> Title : StringUtil.java </p>
 * <p> Description: Some utility functions for strings </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class StringUtil {

	private static final String REPLACE_PATTERN = "{}";

	/**
	 * 
	 */
	private StringUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Gets the formatted string like slf4j log.info("{}", replacementString)
	 * 
	 * @param String sourcePatternedString
	 * @param String replacementString
	 * @return String - formatted String
	 */
	public static String getFormattedString(String sourcePatternedString, Object... replacementString) {
		if (StringUtils.countMatches(sourcePatternedString, REPLACE_PATTERN) == replacementString.length) {
			StringBuilder sb = new StringBuilder(sourcePatternedString);

			for (int i = 0; i < replacementString.length; i++) {
				int indexOfReplaceString = sb.indexOf(REPLACE_PATTERN);
				sb.replace(indexOfReplaceString, indexOfReplaceString + REPLACE_PATTERN.length(), String.valueOf(replacementString[i]));
			}

			return sb.toString();
		}
		return null;
	}

	/**
	 * Appends the endString to the source string, if source string does not ends
	 * with endString
	 * 
	 * @param String sourceString
	 * @param String endString
	 * @return String suffixedString
	 */
	public static String appendIfNotEndsWith(String source, String endString) {
		if (StringUtils.isNotBlank(source) && !StringUtils.endsWithIgnoreCase(source, endString)) {
			return StringUtils.join(source, endString);
		}

		return source;
	}

}
