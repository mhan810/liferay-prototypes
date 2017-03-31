package com.liferay.prototype.analytics.web.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.prototype.analytics.data.binding.JSONObjectMapper;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;

import java.io.BufferedReader;
import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"osgi.http.whiteboard.servlet.pattern=/analytics"},
	service = Servlet.class
)
public class AnalyticsServlet extends HttpServlet {

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeAnalyticsMessageProcessor"
	)
	protected void addAnalyticsMessageProcessor(
		AnalyticsMessageProcessor analyticsMessageProcessor,
		Map<String, Object> properties) {

		String[] messageFormats = null;

		if (properties.get("messageFormat") instanceof String) {
			messageFormats =
				new String[] {(String)properties.get("messageFormat")};
		}
		else {
			messageFormats = (String[])properties.get("messageFormat");
		}

		for (String messageFormat : messageFormats) {
			_analyticsMessageProcessors.put(
				messageFormat, analyticsMessageProcessor);
		}
	}

	protected void removeAnalyticsMessageProcessor(
		AnalyticsMessageProcessor analyticsMessageProcessor,
		Map<String, Object> properties) {

		String[] messageFormats = null;

		if (properties.get("messageFormat") instanceof String) {
			messageFormats =
				new String[] {(String)properties.get("messageFormat")};
		}
		else {
			messageFormats = (String[])properties.get("messageFormat");
		}

		for (String messageFormat : messageFormats) {
			_analyticsMessageProcessors.remove(messageFormat);
		}
	}

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		StringBundler sb = new StringBundler();

		String value = null;

		BufferedReader reader = httpServletRequest.getReader();

		while ((value = reader.readLine()) != null) {
			sb.append(value);
		}

		String analyticsMessage = sb.toString();

		try {
			AnalyticsEvents analyticsEvents = jsonObjectMapper.convert(
				httpServletRequest.getInputStream());

			String messageFormat = analyticsEvents.getMessageFormat();

			AnalyticsMessageProcessor<AnalyticsEvents>
				analyticsMessageProcessor = _analyticsMessageProcessors.get(
					messageFormat);

			if (analyticsMessageProcessor == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("No message processor for : " + messageFormat);
				}

				return;
			}

			analyticsMessageProcessor.processMessage(analyticsEvents);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not parse: " + analyticsMessage, e);
			}
		}
	}

	@Reference(
		target = "(model=com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents)"
	)
	protected JSONObjectMapper<AnalyticsEvents> jsonObjectMapper;

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsServlet.class);

	private Map<String, AnalyticsMessageProcessor<AnalyticsEvents>>
		_analyticsMessageProcessors = new ConcurrentHashMap<>();

}