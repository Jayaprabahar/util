/**
 * 
 */
package com.jayaprabahar.util.db;

import java.sql.Clob;
import java.sql.SQLException;

/**
 * <p> Project : com.jayaprabahar.util.db </p>
 * <p> Title : DBUtil.java </p>
 * <p> Description: DB related Utilities </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class DBUtil {

	public static String getValidStringFromClob(Clob clob) {
		try {
			if (clob != null && clob.length() > 0) {
				return clob.getSubString(1, (int) clob.length());
			}
		} catch (SQLException e) {
			// DO NOTHING
		}
		return null;
	}

}
