package com.jayaprabahar.util.infinispan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang3.BooleanUtils;
import org.infinispan.AdvancedCache;
import org.infinispan.Cache;
import org.infinispan.context.Flag;

/**
 * This class is provide access to Infinispan cache through JNDI.
 * 
 * @author Jayaprabahar
 * @version 4.0
 * @since 21-July-2017.
 */
public final class InfinispanCacheUtil {

	private static InfinispanCacheUtil instance;

	private Cache<String, Object> normalCache;

	/**
	 * Implementing Singleton design pattern.
	 * 
	 * @return InfinispanServiceImpl
	 */
	public static synchronized InfinispanCacheUtil getSingleInstance() {
		synchronized (InfinispanCacheUtil.class) {
			if (instance == null) {
				instance = new InfinispanCacheUtil();
			}
		}
		return instance;
	}

	/**
	 * Private Constructor
	 * 
	 * @return InfinispanServiceImpl
	 */
	@SuppressWarnings("unchecked")
	private InfinispanCacheUtil() {
		try {
			System.out.println("Reading infinispan cache from JNDI");
			normalCache = (Cache<String, Object>) new InitialContext().lookup("<<Infinispan_JNDI_NAME>>");
		} catch (NamingException e) {
			System.err.println("Error reading jndi lookup for cache");
		}
	}

	/**
	 * Return normal cache
	 * 
	 * @return
	 */
	public Cache<String, Object> getNormalCache() {
		return normalCache;
	}

	/**
	 * Return advanced cache. Useful for storing non string objects.
	 * 
	 * @return
	 */
	public AdvancedCache<String, Object> getAdvancedCache() {
		if (normalCache != null) {
			return normalCache.getAdvancedCache().withFlags(Flag.IGNORE_RETURN_VALUES);
		}
		return null;
	}

	/**
	 * Check whether cachekey exist in cache
	 * 
	 * @param cacheKey
	 * @return
	 */
	public boolean hasCacheData(String cacheKey) {
		return normalCache.containsKey(cacheKey);
	}

	/**
	 * Get the CacheData from cache
	 * 
	 * @param cacheKey
	 * @return
	 */
	public Object getCacheData(String cacheKey) {
		return normalCache.get(cacheKey);
	}

	/**
	 * Get Switch configuration details from Cache/DB
	 * 
	 * @param configKey
	 * @return
	 */
	public boolean getCacheDataAsBoolean(String configKey) {
		return BooleanUtils.toBoolean((String) getCacheData(configKey));
	}

	/**
	 * Put CacheData into cache
	 * 
	 * @param cacheKey
	 * @param cacheData
	 * @return
	 */
	public void putCacheData(String cacheKey, Object cacheData) {
		getAdvancedCache().put(cacheKey, cacheData, 1, TimeUnit.MINUTES);
	}

	/**
	 * Clear all values in the cache
	 */
	public void purgeAllCache() {
		normalCache.clear();
	}

	/**
	 * Clear the specific value in the cache
	 * 
	 * @param cacheKey
	 */
	public void purgeUsedCache(String cacheKey) {
		normalCache.remove(cacheKey);
	}

	/**
	 * Returns the cache keys in sorted format. Used in infinispan cache page.
	 * 
	 * @param configKey
	 * @return
	 */
	public Set<String> getSortedKeySet() {
		return new TreeSet<>(getAdvancedCache().keySet());
	}

	/**
	 * Returns the cache keys in sorted format. Used in infinispan cache page.
	 * 
	 * @param configKey
	 * @return
	 */
	public Set<String> filterCacheKeys(String filterKey) {
		return getSortedKeySet().stream().filter(cacheKey -> cacheKey.contains(filterKey)).collect(Collectors.toSet());
	}

	/**
	 * Clear all matching cache values based on wildcard search
	 */
	public void purgeRelatedCache(String wildcardString) {
		filterCacheKeys(wildcardString).forEach(key -> normalCache.remove(key));
	}

	/**
	 * Returns all the cache values as key value pair
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> getAllCache() {
		Map<String, String> allCacheMap = new HashMap<>();
		normalCache.keySet().forEach(e -> allCacheMap.put(e, normalCache.get(e).toString()));

		return allCacheMap;
	}

}
