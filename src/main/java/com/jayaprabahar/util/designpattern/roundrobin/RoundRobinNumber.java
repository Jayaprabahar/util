/**
 * 
 */
package com.jayaprabahar.util.designpattern.roundrobin;

/**
 * <p> Project : com.jayaprabahar.util.collection </p>
 * <p> Title : RoundRobinNumber.java </p>
 * <p> Description: Generates RoundRobinNumber within given size </p>
 * <p> Created: Mar 24, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public class RoundRobinNumber {

	/**
	 *  Generates RoundRobinNumber within given size
	 *  
	 * @param collectionSize
	 * @return Integer
	 */
	public static int getNext(int collectionSize) {
		return UniqueNumberIncrementer.INSTANCE.getNext() % collectionSize;
	}

}
