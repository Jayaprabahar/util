/**
 * 
 */
package com.jayaprabahar.util.http;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;

/**
 * <p> Project : com.jayaprabahar.util.http </p>
 * <p> Title : HTTPUtil.java </p>
 * <p> Description: HTTPUtil </p>
 * <p> Created: Mar 28, 2020 </p>
 * 
 * @version 1.0.0
 * @author <a href="mailto:jpofficial@gmail.com">Jayaprabahar</a>
 */
public final class HTTPUtil {

	/**
	 * 
	 */
	private HTTPUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Returns content URI from the given URL
	 * 
	 * @param HttpServletRequest request
	 * @return String uri
	 */
	public static String getContentURI(HttpServletRequest request) {
		return request.getRequestURI().substring(request.getContextPath().length() + 1);
	}

	/**
	 * Returns root URI from the given URL
	 * 
	 * @param HttpServletRequest request
	 * @return String root uri
	 */
	public static String getRootURI(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		return url.substring(0, url.indexOf(getContentURI(request)));
	}

	/**
	 * Get HTTP paramerts as a Map object
	 * 
	 * @param request
	 * @return Map<String, String>
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Map<String, String> getParameterMap(HttpServletRequest request) {
		return ((Map<String, String[]>) request.getParameterMap()).entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()[0]));
	}

	/**
	 * Returns list of multipart items
	 * 
	 * @param request
	 * @return
	 */
	public static List<FileItem> getMultipartValuesAsList(HttpServletRequest request) {
		try {
			return new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		} catch (FileUploadException e) {
			// DO NOTHING
		}
		return Collections.emptyList();
	}

	/**
	 * Returns the specific multipart item 
	 * 
	 * @param HttpServletRequest request 
	 * @param String fieldName
	 * @return String
	 */
	public static FileItem getMultipartFieldValue(HttpServletRequest request, String fieldName) {
		return getMultipartValuesAsList(request).stream().filter(e -> StringUtils.equalsIgnoreCase(fieldName, e.getFieldName())).findFirst().orElse(null);
	}

	/**
	 * Returns the specific multipart item as a String 
	 * 
	 * @param HttpServletRequest request 
	 * @param String fieldName
	 * @return String
	 */
	public static String getMultipartFieldValueAsString(HttpServletRequest request, String fieldName) {
		FileItem fI = getMultipartFieldValue(request, fieldName);
		return fI != null ? fI.get().toString() : "";
	}

}
