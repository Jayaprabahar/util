package com.jayaprabahar.util.collection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * <p> Project : com.jayaprabahar.util.collection </p>
 * <p> Title : CollectionsUtil.java </p>
 * <p> Description: Collections Util </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
@SuppressWarnings("rawtypes")
public final class CollectionsUtil {

	/**
	 * 
	 */
	private CollectionsUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Returns a empty list if the list is null
	 * 
	 * @param sourceList
	 * @return
	 */
	public static List getValidList(List sourceList) {
		return CollectionUtils.isEmpty(sourceList) ? Collections.emptyList() : sourceList;
	}

	/**
	 * Returns a empty set if the list is null
	 * 
	 * @param sourceList
	 * @return
	 */
	public static Set getValidList(Set sourceList) {
		return CollectionUtils.isEmpty(sourceList) ? Collections.emptySet() : sourceList;
	}

	/**
	 * Returns the first object from the list. If the list is null or empty, returns null
	 * 
	 * @param sourceList
	 * @return
	 */
	public static Object getFirstObjectFromList(List sourceList) {
		return CollectionUtils.isEmpty(sourceList) ? null : sourceList.get(0);
	}

	/**
	 * Returns the first object from the set. If the set is null or empty, returns null
	 *  
	 * @param sourceList
	 * @return
	 */
	public static Object getFirstObjectFromList(Set sourceList) {
		return CollectionUtils.isEmpty(sourceList) ? null : sourceList.iterator().next();
	}

	/**
	 * Checks whether the collections contains the entry or not
	 * 
	 * @param sourceList
	 * @param entry
	 * @return
	 */
	public static boolean contains(Collection sourceList, Object entry) {
		return CollectionUtils.isNotEmpty(sourceList) && entry != null && sourceList.contains(entry);
	}

	/**
	 * Returns the value at the index of the list
	 * 
	 * @param sourceList
	 * @param index
	 * @return
	 */
	public static Object getValueAtIndex(List sourceList, int index) {
		return (CollectionUtils.isNotEmpty(sourceList) && sourceList.size() > index) ? sourceList.get(index) : null;
	}

	/**
	 * Same as String.split, but returns List
	 * 
	 * @param sourceString
	 * @param delimitter
	 * @return
	 */
	public static List<String> splitToList(String sourceString, String delimitter) {
		return Stream.ofNullable(StringUtils.split(sourceString, delimitter)).flatMap(Arrays::stream).collect(Collectors.toList());
	}

}
