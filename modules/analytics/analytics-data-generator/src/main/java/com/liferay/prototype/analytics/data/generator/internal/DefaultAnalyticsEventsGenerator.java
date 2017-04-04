package com.liferay.prototype.analytics.data.generator.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.data.binding.stubs.Location;
import com.liferay.prototype.analytics.data.binding.stubs.MessageContext;
import com.liferay.prototype.analytics.data.binding.stubs.Properties;
import com.liferay.prototype.analytics.data.generator.FormEventGenerator;
import com.liferay.prototype.analytics.data.generator.internal.configuration.AnalyticsEventsGeneratorConfiguration;
import com.liferay.prototype.analytics.generator.AnalyticsEventsGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	public AnalyticsEvents generateEvents() {
		AnalyticsEvents analyticsEvents = new AnalyticsEvents();

		Random random = new Random();

		analyticsEvents.setApplicationId(
			_analyticsEventsGeneratorConfiguration.applicationId());

		analyticsEvents.setChannel(
			_analyticsEventsGeneratorConfiguration.channel());

		analyticsEvents.setMessageContext(createMessageContext(random));

		List<Event> events = createEvents(random);

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

	protected List<Event> createEvents(Random random) {
		List<Event> events = new ArrayList<>();

		long timestampStart = randomTimestampStart(random);

		long timestamp = timestampStart;

		EventBuilder appStartEventBuilder = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, _dateFormat);

		appStartEventBuilder.setEventType("app-start");

		appStartEventBuilder.setTimestamp(timestamp);

		events.add(appStartEventBuilder.getEvent());

		timestamp = createViewEvents(random, events, timestamp);

		float percentage = random.nextFloat();

		String formName = null;

		if (percentage > 0.6) {
			percentage = random.nextFloat();

			if (percentage < 0.4) {
				formName = "Savings Account Application";
			}
			else if ((percentage >= 0.4) && (percentage < 0.8)) {
				formName = "Credit Card Application";
			}

			if (percentage >= 0.8) {
				formName = "Auto Loan Application";
			}
		}

		if (Validator.isNotNull(formName)) {
			FormEventGenerator formEventGenerator = _formEventGenerators.get(
				formName);

			timestamp = formEventGenerator.addFormEvents(
				random, events, _dateFormat, timestamp);

			timestamp = createViewEvents(random, events, timestamp);
		}

		timestamp += Math.round(random.nextDouble() * 10000);

		EventBuilder appCloseEvent = new EventBuilder(
			_analyticsEventsGeneratorConfiguration, _dateFormat);

		appCloseEvent.setEventType("app_close");

		appCloseEvent.setTimestamp(timestamp);

		events.add(appCloseEvent.getEvent());

		return events;
	}

	protected MessageContext createMessageContext(Random random) {
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
		messageContext.setLocation(randomLocation(random));
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

	protected String randomDeviceType(Random random) {
		String[] deviceTypes =
			_analyticsEventsGeneratorConfiguration.deviceTypes();

		int index = random.nextInt(deviceTypes.length);

		return deviceTypes[index];
	}

	protected Location randomLocation(Random random) {
		Location location = new Location();

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

				location.setLongitude(bigDecimal.negate().doubleValue());
			});

		return location;
	}

	protected long randomTimestampStart(Random random) {
		OptionalLong optionalLong = random.longs(
			1, _timestampStart.getTime(), _timestampEnd.getTime()).findAny();

		return optionalLong.orElse(System.currentTimeMillis());
	}

	protected int randomUserId(Random random) {
		return random.nextInt(5000);
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