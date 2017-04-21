package com.liferay.support.bi.storage.elasticsearch.internal;

import com.liferay.portal.kernel.log.Log;

import java.io.IOException;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.common.io.stream.OutputStreamStreamOutput;

/**
 * @author Michael C. Han
 */
public class LogUtil {

	public static void logActionResponse(Log log, ActionResponse actionResponse)
		throws IOException {

		if (!log.isInfoEnabled()) {
			return;
		}

		StringOutputStream stringOutputStream = new StringOutputStream();

		actionResponse.writeTo(
			new OutputStreamStreamOutput(stringOutputStream));

		log.info(stringOutputStream);
	}

	public static void logActionResponse(Log log, BulkResponse bulkResponse)
		throws IOException {

		if (bulkResponse.hasFailures()) {
			log.error(bulkResponse.buildFailureMessage());
		}

		logActionResponse(log, (ActionResponse)bulkResponse);
	}

}