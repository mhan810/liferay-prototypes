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
			"AccountType", "Account Type", entityId, events, random, dateFormat,
			timestamp, duration);

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
			"Address1", "Address 1", entityId, events, random, dateFormat,
			timestamp, duration);

		float percentage = random.nextFloat();

		if (!completeForm && (percentage > .95)) {
			timestamp = addFormCancelEvent(
				"Address1", "Address 1", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"Address2", "Address 2", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"City", "City", entityId, events, random, dateFormat, timestamp,
			duration);

		timestamp = addFormFieldEventsPair(
			"State", "State", entityId, events, random, dateFormat, timestamp,
			duration);

		timestamp = addFormFieldEventsPair(
			"ZipCode", "Zip Code", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"PhoneNumber", "Phone Number", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"EmailAddress", "Email Address", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"SocialSecurityNumber", "Social Security Number", entityId, events,
			random, dateFormat, timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .7)) {
			timestamp = addFormCancelEvent(
				"SocialSecurityNumber", "Social Security Number", entityId,
				events, dateFormat, timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"Citizenship", "Citizenship", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"Identification", "Identification", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"IdNumber", "Id Number", entityId, events, random, dateFormat,
			timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .75)) {
			timestamp = addFormCancelEvent(
				"IdNumber", "Id Number", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"StateOfIssue", "State of Issue", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"IssueDate", "Issue Date", entityId, events, random, dateFormat,
			timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .65)) {
			timestamp = addFormCancelEvent(
				"IssueDate", "Issue Date", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"ExpirationDate", "Expiration Date", entityId, events, random,
			dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"EmploymentStatus", "Employment Status", entityId, events, random,
			dateFormat, timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .9)) {
			timestamp = addFormCancelEvent(
				"Employer", "Employer", entityId, events, dateFormat, timestamp,
				random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"Occupation", "Occupation", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"MothersMaidenName", "Mother's Maiden Name", entityId, events,
			random, dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"FirstSchoolAttended", "First School Attended", entityId, events,
			random, dateFormat, timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"AnticipatedBalance", "Anticipated Balance", entityId, events,
			random, dateFormat, timestamp, duration);

		if (!completeForm) {
			timestamp = addFormCancelEvent(
				"AnticipatedBalance", "Anticipated Balance", entityId, events,
				dateFormat, timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"DoYouPlanToWireMoneyInternationally",
			"Do you plan to wire money internationally", entityId, events,
			random, dateFormat, timestamp, duration);

		return timestamp;
	}

	protected float getFormCompletionPercentage() {
		return 0.6f;
	}

}