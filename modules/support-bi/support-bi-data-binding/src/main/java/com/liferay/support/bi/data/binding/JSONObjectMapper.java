package com.liferay.support.bi.data.binding;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public interface JSONObjectMapper<T> {

	public Collection<String> convert(InputStream inputStream)
		throws IOException;

}