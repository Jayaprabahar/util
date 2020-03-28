package com.jayaprabahar.util.infinispan;

import java.io.File;

import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryActivated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryCreated;
import org.infinispan.notifications.cachelistener.annotation.CacheEntryModified;
import org.infinispan.notifications.cachelistener.event.CacheEntryEvent;

/**
 * <p> Project : com.jayaprabahar.util.infinispan </p>
 * <p> Title : InfinispanCacheListener.java </p>
 * <p> Description: InfinispanCacheListener </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
@Listener(clustered = true)
public class InfinispanCacheListener {

	/**
	 * When there is a update in cache entry (on which the listener is added), then this method performs the event on the other node
	 *   
	 * @param event
	 */
	@CacheEntryCreated
	@CacheEntryModified
	@CacheEntryActivated
	public void cacheEntryStateChange(CacheEntryEvent<String, File> event) {
		if (!event.isPre() && !event.isOriginLocal()) {
			System.out.println("-- Entry modified by another node in the cluster " + event.toString());
			// Call the method to execute change on other node
			// Used entries are event.getKey(), event.getValue();
		}
	}
}
