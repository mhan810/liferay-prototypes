package com.liferay.prototype.analytics.data.generator.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "prototype")
@Meta.OCD(
	id = "com.liferay.prototype.analytics.internal.generator.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration",
	name = "Analytics Events Generator Configuration"
)
public interface AnalyticsEventsGeneratorConfiguration {

	@Meta.AD(deflt = "OnlineBanking", required = false)
	public String applicationId();

	@Meta.AD(deflt = "mobile", required = false)
	public String channel();

	@Meta.AD(deflt = "20160", required = false)
	public int companyId();

	@Meta.AD(deflt = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", required = false)
	public String dateFormat();

	@Meta.AD(deflt = "android|apple", required = false)
	public String[] deviceTypes();

	@Meta.AD(deflt = "12440", required = false)
	public int groupId();

	@Meta.AD(deflt = "FORMS", required = false)
	public String messageFormat();

	@Meta.AD(
		deflt = "2017-04-15T21:10:25.004-0000",
		description = "2017-04-15T21:10:25.004-0000", required = false
	)
	public String timestampEnd();

	@Meta.AD(deflt = "2000", required = false)
	public long timestampInterval();

	@Meta.AD(
		deflt = "2017-03-15T21:10:25.004-0000",
		description = "2017-03-15T21:10:25.004-0000", required = false
	)
	public String timestampStart();

}