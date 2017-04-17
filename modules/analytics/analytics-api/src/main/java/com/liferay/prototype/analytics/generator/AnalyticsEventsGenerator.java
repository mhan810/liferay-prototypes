package com.liferay.prototype.analytics.generator;

/**
 * @author Michael C. Han
 */
public interface AnalyticsEventsGenerator<T> {

	public T generateEvents(int mode);

}