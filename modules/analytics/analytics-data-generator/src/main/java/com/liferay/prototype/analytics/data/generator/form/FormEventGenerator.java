package com.liferay.prototype.analytics.data.generator.form;

import com.liferay.prototype.analytics.data.binding.stubs.Event;

import java.text.DateFormat;

import java.util.List;
import java.util.Random;

/**
 * @author Michael C. Han
 */
public interface FormEventGenerator {

	public long addFormEvents(
		Random random, List<Event> events, DateFormat format, long timestamp);

	public String getFormName();

}