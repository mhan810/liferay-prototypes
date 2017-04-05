package com.liferay.prototype.analytics.data.generator.internal.form;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.prototype.analytics.data.binding.stubs.AdditionalInfo;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Properties;
import com.liferay.prototype.analytics.data.generator.form.FormEventGenerator;
import com.liferay.prototype.analytics.data.generator.internal.EventBuilder;
import com.liferay.prototype.analytics.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration;

import java.text.DateFormat;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Random;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.prototype.analytics.internal.generator.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration",
	immediate = true, service = FormEventGenerator.class
)
public class CreditCardFormEventGenerator implements FormEventGenerator {

	public long addFormEvents(
		Random random, List<Event> events, DateFormat format, long timestamp) {

		timestamp = getNextTimestamp(random, timestamp);

		int entityId = random.nextInt(50000);

		Event formEnter = createFormEvent(
			format, "form-enter", timestamp, entityId);

		events.add(formEnter);

		float percentage = random.nextFloat();

		boolean completForm = false;

		if (percentage > 0.7) {
			completForm = true;
		}

		timestamp = createFormFieldEvents(
			random, events, format, timestamp, completForm, entityId);

		timestamp = getNextTimestamp(random, timestamp);

		Event formExit = null;

		if (completForm) {
			formExit  = createFormEvent(
				format, "form-submit", timestamp, entityId);

			AdditionalInfo additionalInfo = formExit.getAdditionalInfo();

			OptionalInt optionalInt = random.ints(1, 300, 1200).findAny();

			int formTime = optionalInt.orElse(300);

			additionalInfo.setTime(random.nextInt(formTime));
		}

		events.add(formExit);

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
			_analyticsEventsGeneratorConfiguration, dateFormat);

		eventBuilder.setEventType(eventType);
		eventBuilder.setTimestamp(timestamp);

		Properties properties = eventBuilder.getProperties();

		properties.setEntityName(getFormName());
		properties.setEntityId(entityId);

		return eventBuilder;
	}

	protected long createFormFieldEvents(
		Random random, List<Event> events, DateFormat dateFormat,
		long timestamp, boolean completeForm, int entityId) {

		int duration = random.nextInt(5000);

		timestamp = addFormFieldEventsPair(
			"FirstName", "First Name", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"MI", "M.I.", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"LastName", "LastName", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"StreetAddress", "Street Address", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"StreetAddress2", "Street Address 2", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"City", "City", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"State", "State", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"PostalCode", "PostalCode", entityId, events, random,
			dateFormat, timestamp, duration);

		float percentage = random.nextFloat();

		if (!completeForm && (percentage > .9)) {
			timestamp = addFormCancelEvent(
				"PostalCode", "Postal Code", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"AccountsYouOwn", "Accounts You Own", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"TypesOfResidence", "Types of Residence", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"MonthlyPayment", "Monthly Payment", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"GrossAnnualIncome", "Gross Annual Income", entityId, events,
			random, dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"SourceOfIncome", "Source of Income", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"Employer", "Employer", entityId, events, random,
			dateFormat, timestamp, duration);

		if (!completeForm && (percentage > .5)) {
			timestamp = addFormCancelEvent(
				"Employer", "Employer", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"SocialSecurityNumber", "Social Security Number", entityId, events,
			random, dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"MothersMaidenName", "Mother's Maiden Name", entityId, events,
			random, dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"DateOfBirth", "Date of Birth", entityId, events, random,
			dateFormat, timestamp, duration);

		return timestamp;
	}

	private long addFormCancelEvent(
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

	protected long getNextTimestamp(Random random, long timestamp) {
		timestamp += Math.round(random.nextDouble() * 10000);

		return timestamp;
	}

	private volatile AnalyticsEventsGeneratorConfiguration
		_analyticsEventsGeneratorConfiguration;

}