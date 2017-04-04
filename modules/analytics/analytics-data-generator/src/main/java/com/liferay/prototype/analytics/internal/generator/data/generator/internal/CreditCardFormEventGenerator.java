package com.liferay.prototype.analytics.internal.generator.data.generator.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Properties;
import com.liferay.prototype.analytics.internal.generator.EventBuilder;
import com.liferay.prototype.analytics.internal.generator.configuration.AnalyticsEventsGeneratorConfiguration;
import com.liferay.prototype.analytics.internal.generator.data.generator.FormEventGenerator;

import java.text.DateFormat;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.prototype.analytics.internal.generator.configuration.AnalyticsEventsGeneratorConfiguration",
	immediate = true, service = FormEventGenerator.class
)
public class CreditCardFormEventGenerator implements FormEventGenerator {

	public long addFormEvents(
		Random random, List<Event> events, DateFormat format, long timestamp) {

		timestamp = getNextTimestamp(random, timestamp);

		Event formEnter = createFormEvent(
			random, format, "form-enter", timestamp, getFormName());

		events.add(formEnter);

		float percentage = random.nextFloat();

		boolean submitForm = false;

		if (percentage > 0.7) {
			submitForm = true;
		}

		int numFields = 20;

		if (submitForm) {
			timestamp = createFormFieldEvents(
				random, events, format, timestamp, numFields);

			timestamp = getNextTimestamp(random, timestamp);

			Event formExit = createFormEvent(
				random, format, "form-submit", timestamp, getFormName());

			events.add(formExit);
		}
		else {
			numFields = Math.round(numFields * random.nextFloat());

			timestamp = createFormFieldEvents(
				random, events, format, timestamp, numFields);

			timestamp = getNextTimestamp(random, timestamp);

			Event formExit = createFormEvent(
				random, format, "form-cancel", timestamp, getFormName());

			events.add(formExit);
		}

		return timestamp;
	}

	@Override
	public String getFormName() {
		return "Credit Card Application Form";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		_analyticsEventsGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsEventsGeneratorConfiguration.class, properties);
	}

	protected EventBuilder createEventBuilder(
		DateFormat dateformat, String eventType, long timestamp) {

		EventBuilder eventBuilder = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, dateformat);

		eventBuilder.setEventType(eventType);

		eventBuilder.setTimestamp(timestamp);

		return eventBuilder;
	}

	protected Event createFormEvent(
		Random random, DateFormat dateFormat, String eventType, long timestamp,
		String formName) {

		EventBuilder eventBuilder = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, dateFormat);

		eventBuilder.setEventType(eventType);
		eventBuilder.setTimestamp(timestamp);

		Properties properties = eventBuilder.getProperties();

		properties.setElementName(formName);
		properties.setEntityName(formName);
		properties.setEntityId(random.nextInt(50000));

		return eventBuilder.getEvent();
	}

	protected Event createFormEvent(
		Random random, DateFormat format, String eventType, long timestamp,
		String formFieldId, String formName) {

		EventBuilder eventBuilder = createEventBuilder(
			format, eventType, timestamp);

		Properties properties = eventBuilder.getProperties();

		properties.setElementId(formFieldId);

		return eventBuilder.getEvent();
	}

	protected long createFormFieldEvents(
		Random random, List<Event> events, DateFormat format, long timestamp,
		int numFields) {

		for (int i = 0; i < numFields; i++) {
			timestamp = getNextTimestamp(random, timestamp);

			String fieldName = "field" + i;

			Event formFieldEnter = createFormEvent(
				random, format, "form-field-enter", timestamp, fieldName);

			events.add(formFieldEnter);

			Event formFieldExit = createFormEvent(
				random, format, "form-field-exit", timestamp, fieldName);

			events.add(formFieldExit);
		}

		return timestamp;
	}

	protected long getNextTimestamp(Random random, long timestamp) {
		timestamp += Math.round(random.nextDouble() * 10000);

		return timestamp;
	}

	private volatile AnalyticsEventsGeneratorConfiguration
		_analyticsEventsGeneratorConfiguration;

}