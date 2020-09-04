/**
 * 
 */
package com.jayaprabahar.util.yaml;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

/**
 * <p> Project : com.jayaprabahar.util.yaml </p>
 * <p> Title : YamlUtil.java </p>
 * <p> Description: Utility class for YAML related operations </p>
 * <p> Created: Apr 6, 2020</p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public class YamlUtil {

	/**
	 * 
	 */
	private YamlUtil() {
		// DO NOTHING
	}

	/**
	 * Checks wthether the given string is valid Yaml
	 * 
	 * @param sourceString
	 * @return
	 */
	public static boolean isValidYaml(String sourceString) {
		return StringUtils.isEmpty(sourceString) || !(new Yaml().load(sourceString) instanceof Map);
	}

}
