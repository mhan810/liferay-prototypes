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
public class HomeMortgageFormEventGenerator
	extends BaseFormEventGenerator implements FormEventGenerator {

	@Override
	public String getFormName() {
		return "Home Mortgage Application Form";
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
			"YourGoals", "Your Goals", entityId, events, random, dateFormat,
			timestamp, duration);

		float percentage = random.nextFloat();

		if (!completeForm && (percentage > .5)) {
			timestamp = addFormCancelEvent(
				"YourGoals", "Your Goals", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		timestamp = addFormFieldEventsPair(
			"FirstName", "First Name", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"LastName", "LastName", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"State", "State", entityId, events, random, dateFormat, timestamp,
			duration);

		timestamp = addFormFieldEventsPair(
			"ZipCode", "Zip Code", entityId, events, random, dateFormat,
			timestamp, duration);

		timestamp = addFormFieldEventsPair(
			"PhoneNumber", "Phone Number", entityId, events, random, dateFormat,
			timestamp, duration);

		percentage = random.nextFloat();

		if (!completeForm && (percentage > .9)) {
			timestamp = addFormCancelEvent(
				"PhoneNumber", "Phone Number", entityId, events, dateFormat,
				timestamp, random);

			return timestamp;
		}

		return timestamp;
	}

	protected float getFormCompletionPercentage() {
		return 0.4f;
	}

}