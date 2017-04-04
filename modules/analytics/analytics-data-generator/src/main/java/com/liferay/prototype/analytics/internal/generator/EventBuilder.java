package com.liferay.prototype.analytics.internal.generator;

import com.liferay.prototype.analytics.data.binding.stubs.AdditionalInfo;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Properties;
import com.liferay.prototype.analytics.internal.generator.configuration.AnalyticsEventsGeneratorConfiguration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;

/**
 * @author Michael C. Han
 */
public class EventBuilder {

	public EventBuilder(
		AnalyticsEventsGeneratorConfiguration
			analyticsEventsGeneratorConfiguration) {

		this(
			analyticsEventsGeneratorConfiguration,
			new SimpleDateFormat(_DATE_FORMAT_STRING));
	}

	public EventBuilder(
		AnalyticsEventsGeneratorConfiguration
			analyticsEventsGeneratorConfiguration, DateFormat dateFormat) {

		_analyticsEventsGeneratorConfiguration =
			analyticsEventsGeneratorConfiguration;

		_event.setGroupId(_analyticsEventsGeneratorConfiguration.groupId());

		_dateFormat = dateFormat;
	}

	public Event getEvent() {
		return _event;
	}

	public Properties getProperties() {
		Properties properties = _event.getProperties();

		if (properties == null) {
			properties = new Properties();

			_event.setProperties(properties);
		}

		return properties;
	}

	public void setAdditionalInfoProperty(String name, Object value) {
		AdditionalInfo additionalInfo = getAdditionalInfo();

		additionalInfo.setAdditionalProperty(name, value);
	}

	public void setAdditionalInfoTime(int time) {
		AdditionalInfo additionalInfo = getAdditionalInfo();

		additionalInfo.setTime(time);
	}

	public void setEventType(String eventType) {
		_event.setEvent(eventType);
	}

	public void setTimestamp(long timestamp) {
		Date date = new Date(timestamp);

		_event.setTimestamp(_dateFormat.format(date));
	}

	protected AdditionalInfo getAdditionalInfo() {
		AdditionalInfo additionalInfo = _event.getAdditionalInfo();

		if (additionalInfo == null) {
			additionalInfo = new AdditionalInfo();

			_event.setAdditionalInfo(additionalInfo);
		}

		return additionalInfo;
	}

	private static final String _DATE_FORMAT_STRING =
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private final AnalyticsEventsGeneratorConfiguration
		_analyticsEventsGeneratorConfiguration;
	private final DateFormat _dateFormat;
	private Event _event = new Event();

}