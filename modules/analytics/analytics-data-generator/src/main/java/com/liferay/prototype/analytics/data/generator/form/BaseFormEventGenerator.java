package com.liferay.prototype.analytics.data.generator.form;

import com.liferay.prototype.analytics.data.binding.stubs.AdditionalInfo;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Properties;
import com.liferay.prototype.analytics.data.generator.internal.EventBuilder;
import com.liferay.prototype.analytics.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration;

import java.text.DateFormat;

import java.util.List;
import java.util.OptionalInt;
import java.util.Random;

/**
 * @author Michael C. Han
 */
public abstract class BaseFormEventGenerator implements FormEventGenerator {

	public long addFormEvents(
		Random random, List<Event> events, DateFormat format, long timestamp) {

		timestamp = getNextTimestamp(random, timestamp);

		int entityId = random.nextInt(50000);

		EventBuilder formEnterEventBuilder = createFormEventBuilder(
			format, "form-enter", timestamp, entityId);

		events.add(formEnterEventBuilder.getEvent());

		float percentage = random.nextFloat();

		boolean completForm = false;

		if (percentage > getFormCompletionPercentage()) {
			completForm = true;
		}

		timestamp = createFormFieldEvents(
			random, events, format, timestamp, completForm, entityId);

		timestamp = getNextTimestamp(random, timestamp);

		if (completForm) {
			EventBuilder formExitEventBuilder = createFormEventBuilder(
				format, "form-submit", timestamp, entityId);

			OptionalInt optionalInt = random.ints(1, 300, 1200).findAny();

			int formTime = optionalInt.orElse(300);

			formExitEventBuilder.setAdditionalInfoTime(
				random.nextInt(formTime));

			events.add(formExitEventBuilder.getEvent());
		}

		return timestamp;
	}

	protected long addFormCancelEvent(
		String lastFieldId, String lastFieldName, int entityId,
		List<Event> events, DateFormat dateFormat, long timestamp,
		Random random) {

		timestamp = getNextTimestamp(random, timestamp);

		EventBuilder formEventBuilder = createFormEventBuilder(
			dateFormat, "form-cancel", timestamp, entityId);

		Properties properties = formEventBuilder.getProperties();

		properties.setLastElementId(lastFieldId);
		properties.setLastElementName(lastFieldName);

		formEventBuilder.setAdditionalInfoTime(random.nextInt(600));

		events.add(formEventBuilder.getEvent());

		return timestamp;
	}

	protected long addFormFieldEventsPair(
		String fieldId, String fieldName, int entityId, List<Event> events,
		Random random, DateFormat dateFormat, long timestamp, int duration) {

		timestamp = getNextTimestamp(random, timestamp);

		EventBuilder formFieldEnterEventBuilder = createFormEventBuilder(
			dateFormat, "form-field-enter", timestamp, entityId);

		Properties fieldEnterProperties =
			formFieldEnterEventBuilder.getProperties();

		fieldEnterProperties.setElementId(fieldId);
		fieldEnterProperties.setElementName(fieldName);

		events.add(formFieldEnterEventBuilder.getEvent());

		timestamp = getNextTimestamp(random, timestamp);

		EventBuilder formFieldExitEventBuilder = createFormEventBuilder(
			dateFormat, "form-field-exit", timestamp, entityId);

		Properties fieldExitProperties =
			formFieldExitEventBuilder.getProperties();

		fieldExitProperties.setElementId(fieldId);
		fieldExitProperties.setElementName(fieldName);

		formFieldExitEventBuilder.setAdditionalInfoTime(duration);

		events.add(formFieldEnterEventBuilder.getEvent());

		return timestamp;
	}

	protected Event createFormEvent(
		DateFormat dateFormat, String eventType, long timestamp, int entityId) {

		EventBuilder eventBuilder = createFormEventBuilder(
			dateFormat, eventType, timestamp, entityId);

		Properties properties = eventBuilder.getProperties();

		properties.setElementName(getFormName());

		return eventBuilder.getEvent();
	}

	protected EventBuilder createFormEventBuilder(
		DateFormat dateFormat, String eventType, long timestamp, int entityId) {

		EventBuilder eventBuilder = new EventBuilder(
			analyticsEventsGeneratorConfiguration, dateFormat);

		eventBuilder.setEventType(eventType);
		eventBuilder.setTimestamp(timestamp);

		Properties properties = eventBuilder.getProperties();

		properties.setEntityName(getFormName());
		properties.setEntityId(entityId);

		return eventBuilder;
	}

	protected abstract long createFormFieldEvents(
		Random random, List<Event> events, DateFormat format, long timestamp,
		boolean completForm, int entityId);

	protected abstract float getFormCompletionPercentage();

	protected long getNextTimestamp(Random random, long timestamp) {
		timestamp += Math.round(random.nextDouble() * 10000);

		return timestamp;
	}

	protected volatile AnalyticsEventsGeneratorConfiguration
		analyticsEventsGeneratorConfiguration;

}