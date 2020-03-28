/**
 * 
 */
package com.jayaprabahar.util.json;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

/**
 * <p> Project : com.jayaprabahar.util.json </p>
 * <p> Title : JsonUtil.java </p>
 * <p> Description: JsonUtil </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class JsonUtil {

	private JsonUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Returns JSON format of a list
	 * 
	 * @param List list
	 * @return JsonElement
	 */
	public static JsonElement getJsonElementOfList(List<?> list) {
		return new Gson().toJsonTree(list, new TypeToken<List<?>>() {
		}.getType());
	}

	/**
	 * Checks whether the json Element(list) is empty or not
	 * 
	 * @param JsonElement jsonElement
	 * @return boolean
	 */
	public static boolean isJsonElementEmpty(JsonElement jsonElement) {
		return (jsonElement.isJsonNull() || !jsonElement.isJsonArray() || jsonElement.toString().equals("[]"));
	}

}
