package com.liferay.prototype.analytics.data.generator.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Location;
import com.liferay.prototype.analytics.data.binding.stubs.MessageContext;
import com.liferay.prototype.analytics.data.binding.stubs.Properties;
import com.liferay.prototype.analytics.data.generator.form.FormEventGenerator;
import com.liferay.prototype.analytics.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration;
import com.liferay.prototype.analytics.generator.AnalyticsEventsGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Random;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.prototype.analytics.internal.generator.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration",
	immediate = true,
	property = {
		"model=com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents"
	},
	service = AnalyticsEventsGenerator.class
)
public class DefaultAnalyticsEventsGenerator
	implements AnalyticsEventsGenerator<AnalyticsEvents> {

	@Override
	public AnalyticsEvents generateEvents(int mode) {
		AnalyticsEvents analyticsEvents = new AnalyticsEvents();

		Random random = new Random();

		analyticsEvents.setApplicationId(
			_analyticsEventsGeneratorConfiguration.applicationId());

		analyticsEvents.setChannel(
			_analyticsEventsGeneratorConfiguration.channel());

		float percentage = random.nextFloat();

		String formName = null;

		if (percentage > 0.6) {
			percentage = random.nextFloat();

			if (percentage < 0.25) {
				formName = "Savings Account Application Form";
			}
			else if ((percentage >= 0.25) && (percentage < 0.85)) {
				formName = "Credit Card Application Form";
			}

			if (percentage >= 0.85) {
				formName = "Home Mortgage Application Form";
			}
		}

		Optional<FormEventGenerator> formEventGeneratorOptional =
			Optional.empty();

		if (Validator.isNotNull(formName)) {
			formEventGeneratorOptional = Optional.ofNullable(
				_formEventGenerators.get(formName + mode));
		}

		analyticsEvents.setMessageContext(
			createMessageContext(random, formEventGeneratorOptional));

		List<Event> events = createEvents(
			random, formEventGeneratorOptional, mode);

		analyticsEvents.setEvents(events);

		analyticsEvents.setMessageFormat(
			_analyticsEventsGeneratorConfiguration.messageFormat());

		return analyticsEvents;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		_analyticsEventsGeneratorConfiguration =
			ConfigurableUtil.createConfigurable(
				AnalyticsEventsGeneratorConfiguration.class, properties);

		_dateFormat = new SimpleDateFormat(
			_analyticsEventsGeneratorConfiguration.dateFormat());

		_timestampStart = _dateFormat.parse(
			_analyticsEventsGeneratorConfiguration.timestampStart());

		_timestampEnd = _dateFormat.parse(
			_analyticsEventsGeneratorConfiguration.timestampEnd());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeFormEventGenerator"
	)
	protected void addFormEventGenerator(
		FormEventGenerator formEventGenerator) {

		_formEventGenerators.put(
			formEventGenerator.getFormName(), formEventGenerator);
	}

	protected Event createEvent(String eventType, long timestamp) {
		EventBuilder eventBuilder = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, _dateFormat);

		eventBuilder.setEventType(eventType);

		eventBuilder.setTimestamp(timestamp);

		return eventBuilder.getEvent();
	}

	protected List<Event> createEvents(
		Random random, Optional<FormEventGenerator> formEventGeneratorOptional,
		int mode) {

		List<Event> events = new ArrayList<>();

		long timestamp = randomTimestampStart(random, mode);

		EventBuilder appStartEventBuilder = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, _dateFormat);

		appStartEventBuilder.setEventType("app-start");

		appStartEventBuilder.setTimestamp(timestamp);

		events.add(appStartEventBuilder.getEvent());

		timestamp = createViewEvents(random, events, timestamp);

		if (formEventGeneratorOptional.isPresent()) {
			timestamp = formEventGeneratorOptional.get().addFormEvents(
				random, events, _dateFormat, timestamp);

			timestamp = createViewEvents(random, events, timestamp);
		}

		timestamp += Math.round(random.nextDouble() * 10000);

		EventBuilder appCloseEvent = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, _dateFormat);

		appCloseEvent.setEventType("app-end");

		appCloseEvent.setTimestamp(timestamp);

		events.add(appCloseEvent.getEvent());

		return events;
	}

	protected MessageContext createMessageContext(
		Random random,
		Optional<FormEventGenerator> formEventGeneratorOptional) {

		MessageContext messageContext = new MessageContext();

		messageContext.setCompanyId(
			_analyticsEventsGeneratorConfiguration.companyId());
		messageContext.setDeviceId(StringUtil.randomId());
		messageContext.setDeviceType(randomDeviceType(random));

		StringBundler sb = new StringBundler();

		random.ints(4, 1, 200).forEach(value -> {
			sb.append(value);
			sb.append(CharPool.PERIOD);
		});

		messageContext.setIpAddress(
			sb.toString().substring(0, sb.length() - 1));

		messageContext.setLanguageId("en_US");
		messageContext.setLocation(
			randomLocation(random, formEventGeneratorOptional));
		messageContext.setSignedIn(true);
		messageContext.setSessionId(portalUUID.generate());
		messageContext.setUserId(randomUserId(random));

		return messageContext;
	}

	protected long createViewEvents(
		Random random, List<Event> events, long timestamp) {

		OptionalInt optionalInt = random.ints(1, 5, 20).findAny();

		int numEvents = optionalInt.orElse(5);

		for (int i = 0; i < numEvents; i++) {
			timestamp += Math.round(random.nextDouble() * 100000);

			EventBuilder viewPageEventBuilder = new EventBuilder(
				_analyticsEventsGeneratorConfiguration, _dateFormat);

			viewPageEventBuilder.setEventType("view");

			viewPageEventBuilder.setTimestamp(timestamp);

			Properties properties = viewPageEventBuilder.getProperties();

			properties.setElementName("page-" + i);

			events.add(viewPageEventBuilder.getEvent());
		}

		return timestamp;
	}

	protected long getTimeInMillis(Date date, int mode) {
		Calendar calendar = new GregorianCalendar(TimeZoneUtil.GMT);

		calendar.setTime(date);
		calendar.add(Calendar.MONTH, mode);

		return calendar.getTimeInMillis();
	}

	protected String randomDeviceType(Random random) {
		String[] deviceTypes =
			_analyticsEventsGeneratorConfiguration.deviceTypes();

		int index = random.nextInt(deviceTypes.length);

		return deviceTypes[index];
	}

	protected Location randomLocation(
		Random random,
		Optional<FormEventGenerator> formEventGeneratorOptional) {

		Location location = new Location();

		formEventGeneratorOptional.ifPresent(
			formEventGenerator -> formEventGenerator.populateLocation(
				random, location));

		formEventGeneratorOptional.orElseGet(
			() -> {
				OptionalDouble lat = random.doubles(1, 30.0, 45.0).findAny();

				lat.ifPresent(
					value -> {
						BigDecimal bigDecimal = new BigDecimal(value);

						bigDecimal = bigDecimal.setScale(3, RoundingMode.FLOOR);

						location.setLatitude(bigDecimal.doubleValue());
					});

				OptionalDouble lon = random.doubles(1, 70, 125.0).findAny();

				lon.ifPresent(
					value -> {
						BigDecimal bigDecimal = new BigDecimal(value);

						bigDecimal = bigDecimal.setScale(3, RoundingMode.FLOOR);

						location.setLongitude(
							bigDecimal.negate().doubleValue());
					});

				return null;
			});

		return location;
	}

	protected long randomTimestampStart(Random random, int mode) {
		long start = _timestampStart.getTime();
		long end = _timestampEnd.getTime();

		if (mode > 0) {
			start = getTimeInMillis(_timestampStart, mode);

			end = getTimeInMillis(_timestampEnd, mode);
		}

		OptionalLong optionalLong = random.longs(1, start, end).findAny();

		return optionalLong.orElse(System.currentTimeMillis());
	}

	protected int randomUserId(Random random) {
		return random.nextInt(5000);
	}

	protected void removeFormEventGenerator(
		FormEventGenerator formEventGenerator) {

			_formEventGenerators.remove(formEventGenerator.getFormName());
	}

	@Reference
	protected PortalUUID portalUUID;

	private static final String _DATE_FORMAT_STRING =
		"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private volatile AnalyticsEventsGeneratorConfiguration
		_analyticsEventsGeneratorConfiguration;
	private volatile DateFormat _dateFormat;
	private Map<String, FormEventGenerator> _formEventGenerators =
		new HashMap<>();
	private Date _timestampEnd;
	private Date _timestampStart;

}