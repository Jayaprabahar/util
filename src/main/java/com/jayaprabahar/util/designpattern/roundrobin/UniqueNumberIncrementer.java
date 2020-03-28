/**
 * 
 */
package com.jayaprabahar.util.designpattern.roundrobin;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> Project : com.jayaprabahar.util.collection </p>
 * <p> Title : UniqueNumberIncrementer </p>
 * <p> Description: UniqueNumberIncrementer </p>
 * <p> Created: Mar 24, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public enum UniqueNumberIncrementer {
	INSTANCE;

	private final AtomicInteger atomicInteger = new AtomicInteger(0);

	/**
	 * Returns the next unique number
	 * 
	 * @return Integer
	 */
	public int getNext() {
		if (atomicInteger.get() == Integer.MAX_VALUE)
			atomicInteger.set(0);
		return atomicInteger.getAndIncrement();
	}
}
