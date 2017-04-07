package com.liferay.prototype.analytics.data.generator.internal.form;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.generator.form.BaseFormEventGenerator;
import com.liferay.prototype.analytics.data.generator.form.FormEventGenerator;
import com.liferay.prototype.analytics.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration;

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
	configurationPid = "com.liferay.prototype.analytics.internal.generator.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration",
	immediate = true, service = FormEventGenerator.class
)
public class SavingsAccountFormEventGenerator
	extends BaseFormEventGenerator implements FormEventGenerator {

	@Override
	public String getFormName() {
		return "Savings Account Application Form";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		analyticsEventsGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsEventsGeneratorConfiguration.class, properties);
	}

	protected long createFormFieldEvents(
		Random random, List<Event> events, DateFormat dateFormat,
		long timestamp, boolean completeForm, int entityId) {

		int duration = random.nextInt(5000);

		timestamp = addFormFieldEventsPair(
			"FirstName", "First Name", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"MI", "M.I.", entityId, events, random, dateFormat, timestamp,
			duration);

		timestamp = addFormFieldEventsPair(
			"LastName", "Last Name", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"StreetAddress", "Street Address", entityId, events, random,
			dateFormat, timestamp, duration);

		float percentage = random.nextFloat();

		if (!completeForm && (percentage > .95)) {
			timestamp = addFormCancelEvent(
				"StreetAddress", "Street Address", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"StreetAddress2", "Street Address 2", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"City", "City", entityId, events, random, dateFormat, timestamp,
			duration);

		timestamp = addFormFieldEventsPair(
			"State", "State", entityId, events, random, dateFormat, timestamp,
			duration);

		timestamp = addFormFieldEventsPair(
			"PostalCode", "PostalCode", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"EmailAddress", "Email Address", entityId, events, random,
			dateFormat, timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .8)) {
			timestamp = addFormCancelEvent(
				"EmailAddress", "Email Address", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"PhoneNumber", "Phone Number", entityId, events, random, dateFormat,
			timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .78)) {
			timestamp = addFormCancelEvent(
				"PhoneNumber", "Phone Number", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"SourceOfIncome", "Source of Income", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"Employer", "Employer", entityId, events, random, dateFormat,
			timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .8)) {
			timestamp = addFormCancelEvent(
				"Employer", "Employer", entityId, events, dateFormat, timestamp,
				random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"SocialSecurityNumber", "Social Security Number", entityId, events,
			random, dateFormat, timestamp, duration);

		if (!completeForm) {
			timestamp = addFormCancelEvent(
				"SocialSecurityNumber", "Social Security Number", entityId,
				events, dateFormat, timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"MothersMaidenName", "Mother's Maiden Name", entityId, events,
			random, dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"DateOfBirth", "Date of Birth", entityId, events, random,
			dateFormat, timestamp, duration);

		return timestamp;
	}

	protected float getFormCompletionPercentage() {
		return 0.6f;
	}

}