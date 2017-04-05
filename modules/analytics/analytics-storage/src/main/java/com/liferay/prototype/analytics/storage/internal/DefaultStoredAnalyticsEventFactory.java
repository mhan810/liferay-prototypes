package com.liferay.prototype.analytics.storage.internal;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.data.binding.stubs.Event;
import com.liferay.prototype.analytics.storage.StoredAnalyticsEventFactory;
import com.liferay.prototype.analytics.storage.stubs.AdditionalInfo;
import com.liferay.prototype.analytics.storage.stubs.MessageContext;
import com.liferay.prototype.analytics.storage.stubs.Location;
import com.liferay.prototype.analytics.storage.stubs.Properties;
import com.liferay.prototype.analytics.storage.stubs.StoredAnalyticsEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = StoredAnalyticsEventFactory.class)
public class DefaultStoredAnalyticsEventFactory
	implements StoredAnalyticsEventFactory {

	@Override
	public Collection<StoredAnalyticsEvent> create(
		AnalyticsEvents analyticsEvents) {

		List<Event> events = analyticsEvents.getEvents();

		if (ListUtil.isEmpty(events)) {
			return Collections.emptyList();
		}

		Collection<StoredAnalyticsEvent> storedAnalyticsEvents =
			new ArrayList<>(events.size());

		for (Event event : events) {
			StoredAnalyticsEvent storedAnalyticsEvent =
				new StoredAnalyticsEvent();

			storedAnalyticsEvents.add(storedAnalyticsEvent);

			storedAnalyticsEvent.setAdditionalInfo(
				convert(event.getAdditionalInfo()));

			storedAnalyticsEvent.setApplicationId(
				analyticsEvents.getApplicationId());

			storedAnalyticsEvent.setChannel(analyticsEvents.getChannel());

			storedAnalyticsEvent.setMessageContext(
				convert(analyticsEvents.getMessageContext()));

			storedAnalyticsEvent.setEvent(event.getEvent());
			storedAnalyticsEvent.setGroupId(event.getGroupId());
			storedAnalyticsEvent.setMessageFormat(
				analyticsEvents.getMessageFormat());

			storedAnalyticsEvent.setProperties(convert(event.getProperties()));
			storedAnalyticsEvent.setTimestamp(event.getTimestamp());
		}

		return storedAnalyticsEvents;
	}

	protected AdditionalInfo convert(
		com.liferay.prototype.analytics.data.binding.stubs.AdditionalInfo
			additionalInfo) {

		if (additionalInfo == null) {
			return null;
		}

		AdditionalInfo storageAdditionalInfo = new AdditionalInfo();

		storageAdditionalInfo.setTime(additionalInfo.getTime());

		Map<String, Object> additionalProperties =
			additionalInfo.getAdditionalProperties();

		additionalProperties.forEach(
			storageAdditionalInfo::setAdditionalProperty);

		return storageAdditionalInfo;
	}

	protected MessageContext convert(
		com.liferay.prototype.analytics.data.binding.stubs.MessageContext
			messageContext) {

		if (messageContext == null) {
			return null;
		}

		MessageContext storedMessageContext = new MessageContext();

		storedMessageContext.setCompanyId(messageContext.getCompanyId());
		storedMessageContext.setDeviceId(messageContext.getDeviceId());
		storedMessageContext.setIpAddress(messageContext.getIpAddress());
		storedMessageContext.setLanguageId(messageContext.getLanguageId());
		storedMessageContext.setSignedIn(messageContext.getSignedIn());
		storedMessageContext.setSessionId(messageContext.getSessionId());
		storedMessageContext.setUserId(messageContext.getUserId());
		storedMessageContext.setUserName(messageContext.getUserName());

		storedMessageContext.setLocation(convert(messageContext.getLocation()));

		Map<String, Object> additionalProperties =
			messageContext.getAdditionalProperties();

		additionalProperties.forEach(
			storedMessageContext::setAdditionalProperty);

		return storedMessageContext;
	}

	protected Location convert(
		com.liferay.prototype.analytics.data.binding.stubs.Location location) {

		if (location == null) {
			return null;
		}

		Location storedLocation = new Location();

		storedLocation.setLat(location.getLatitude());
		storedLocation.setLon(location.getLongitude());

		Map<String, Object> additionalProperties =
			location.getAdditionalProperties();

		additionalProperties.forEach(storedLocation::setAdditionalProperty);

		return storedLocation;
	}

	protected Properties convert(
		com.liferay.prototype.analytics.data.binding.stubs.Properties
			properties) {

		if (properties == null) {
			return null;
		}

		Properties storedProperties = new Properties();

		storedProperties.setElementId(properties.getElementId());
		storedProperties.setElementName(properties.getElementName());
		storedProperties.setEntityId(properties.getEntityId());
		storedProperties.setEntityName(properties.getEntityName());
		storedProperties.setEntityType(properties.getEntityType());
		storedProperties.setLastElementId(properties.getLastElementId());
		storedProperties.setLastElementName(properties.getLastElementName());
		storedProperties.setReferrers(properties.getReferrers());

		Map<String, Object> additionalProperties =
			properties.getAdditionalProperties();

		additionalProperties.forEach(storedProperties::setAdditionalProperty);

		return storedProperties;
	}

}