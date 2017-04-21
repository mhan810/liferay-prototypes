package com.liferay.support.bi.storage.elasticsearch;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collection;

/**
 * @author Michael C. Han
 */
public interface MessageStorage {

	public void store(Collection<String> jsonObjects) throws PortalException;

}