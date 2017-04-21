package com.liferay.support.bi.storage.elasticsearch.internal;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

/**
 * @author Michael C. Han
 */
public class ResourceUtil {

	public static String getResourceAsString(
		Class<?> clazz, String resourceName) {

		try (InputStream inputStream = clazz.getResourceAsStream(
			resourceName)) {

			return StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to load resource: " + resourceName, ioe);
		}
	}

	public static File getResourceAsTempFile(Class<?> clazz, String name)
		throws IOException {

		int index = name.lastIndexOf(CharPool.PERIOD);

		File file = File.createTempFile(
			name.substring(0, index), name.substring(index));

		file.deleteOnExit();

		try (InputStream inputStream = clazz.getResourceAsStream(name)) {
			FileUtils.copyInputStreamToFile(inputStream, file);
		}

		return file;
	}

}