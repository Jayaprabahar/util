/**
 * 
 */
package com.jayaprabahar.util.excel;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * <p> Project : com.jayaprabahar.util.excel </p>
 * <p> Title : ExcelUtil.java </p>
 * <p> Description: ExcelUtil </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class ExcelUtil {

	private ExcelUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Returns the String which is formed by joining the celltypes of all cells with separator
	 * 
	 * @param row
	 * @return
	 */
	public static String getMergedCellTypePerRow(Row row, String separator) {
		if (row != null) {
			StringJoiner joiner = new StringJoiner(separator);
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				if (null != row.getCell(i) && row.getCell(i).getCellType() != CellType.BLANK) {
					joiner.add(row.getCell(i).getCellType().toString());
				}
			}

			return joiner.toString();
		}
		return null;
	}

	/**
	 * Checks the Merged Cell Type of all the cells is same as validCellTypeString. If it is same, then the given row has valid column cells
	 * 
	 * @param row
	 * @param separator
	 * @param validCellTypeString
	 * @return
	 */
	public static boolean isValidColumn(Row row, String separator, String validCellTypeString) {
		return StringUtils.equalsIgnoreCase(getMergedCellTypePerRow(row, separator), validCellTypeString);
	}

}
