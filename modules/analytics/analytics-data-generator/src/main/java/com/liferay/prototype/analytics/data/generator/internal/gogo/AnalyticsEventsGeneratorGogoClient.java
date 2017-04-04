package com.liferay.prototype.analytics.data.generator.internal.gogo;

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.prototype.analytics.data.binding.JSONObjectMapper;
import com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents;
import com.liferay.prototype.analytics.generator.AnalyticsEventsGenerator;
import com.liferay.prototype.analytics.processor.AnalyticsMessageProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URI;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"osgi.command.function=generate", "osgi.command.function=load",
		"osgi.command.scope=analytics"
	},
	service = AnalyticsEventsGeneratorGogoClient.class
)
public class AnalyticsEventsGeneratorGogoClient {

	public void generate(int count) {
		try {
			for (int i = 0; i < count; i++) {
				AnalyticsEvents analyticsEvents =
					_analyticsEventsGenerator.generateEvents();

				_analyticsMessageProcessor.processMessage(analyticsEvents);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void load(String fileURI) {
		URI uri = URI.create(fileURI);

		try (InputStream in = uri.toURL().openStream()) {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in));

			String jsonString = null;

			while ((jsonString = bufferedReader.readLine()) != null) {
				AnalyticsEvents analyticsEvents = jsonObjectMapper.convert(
					jsonString);

				_analyticsMessageProcessor.processMessage(analyticsEvents);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Reference(
		target = "(model=com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents)"
	)
	protected JSONObjectMapper<AnalyticsEvents> jsonObjectMapper;

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsEventsGeneratorGogoClient.class);

	@Reference(
		target = "(model=com.liferay.prototype.analytics.data.binding.stubs.AnalyticsEvents)"
	)
	private AnalyticsEventsGenerator<AnalyticsEvents> _analyticsEventsGenerator;

	@Reference(target = "(messageFormat=FORMS)")
	private AnalyticsMessageProcessor<AnalyticsEvents>
		_analyticsMessageProcessor;

	@Reference
	private JSONFactory _jsonFactory;

}