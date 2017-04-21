package com.liferay.support.bi.client.internal.web;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.support.bi.data.binding.JSONObjectMapper;
import com.liferay.support.bi.storage.elasticsearch.MessageStorage;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Collection;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {"osgi.http.whiteboard.servlet.pattern=/support-bi-client/loader"},
	service = Servlet.class
)
public class SupportBIDataLoaderServlet extends HttpServlet {

	@Override
	protected void service(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			Collection<String> jsonObjects = jsonObjectMapper.convert(
				httpServletRequest.getInputStream());

			messageStorage.store(jsonObjects);
		}
		catch (Exception e) {
			httpServletResponse.setStatus(
				HttpServletResponse.SC_EXPECTATION_FAILED);

			PrintWriter writer = httpServletResponse.getWriter();

			writer.println("Liferay Support BI");

			writer.println(StackTraceUtil.getStackTrace(e));

			if (_log.isWarnEnabled()) {
				_log.warn("Could not parse: ", e);
			}
		}
	}

	@Reference(
		target = "(model=com.liferay.support.bi.data.binding.stubs.SupportTicket)"
	)
	protected JSONObjectMapper<?> jsonObjectMapper;

	@Reference
	protected MessageStorage messageStorage;

	private static final Log _log = LogFactoryUtil.getLog(
		SupportBIDataLoaderServlet.class);

}