package com.liferay.support.bi.data.binding.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.support.bi.data.binding.JSONObjectMapper;
import com.liferay.support.bi.data.binding.stubs.SupportTicket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"model=com.liferay.support.bi.data.binding.stubs.SupportTicket"
	},
	service = JSONObjectMapper.class
)
public class SupportTicketJSONObjectMapper
	implements JSONObjectMapper<SupportTicket> {

	@Override
	public Collection<String> convert(InputStream inputStream)
		throws IOException {

		Collection<String> jsonObjects = new ArrayList<>();

		ObjectMapper objectMapper = createObjectMapper();

		try (BufferedReader bufferedReader =
				new BufferedReader(new InputStreamReader(inputStream))) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				//ticketNumber,subject,description,status,ticketAssignee,envLFR,

				// accountCode,supportRegion,component,ticketCreateDate,
				// ticketClosedDate,LPP Ticket,LPP components,LPP resolution

				String[] tokens = StringUtil.split(line);

				SupportTicket supportTicket = new SupportTicket();

				supportTicket.setTicketNumber(tokens[0]);
				supportTicket.setSubject(tokens[1]);
				supportTicket.setDescription(tokens[2]);
				supportTicket.setStatus(tokens[3]);
				supportTicket.setTicketAssignment(tokens[4]);
				supportTicket.setLiferayVersion(tokens[5]);
				supportTicket.setAccountCode(tokens[6]);
				supportTicket.setSupportRegion(tokens[7]);
				supportTicket.setComponent(tokens[8]);
				supportTicket.setTicketCreateDate(tokens[9]);
				supportTicket.setTicketClosedDate(tokens[10]);
				supportTicket.setLppTicket(tokens[11]);

				String lppComponentsString = tokens[12];

				if (lppComponentsString.startsWith(StringPool.QUOTE)) {
					lppComponentsString = lppComponentsString.substring(
						1, lppComponentsString.length() - 1);

					String[] lppComponents = StringUtil.split(
						lppComponentsString, StringPool.COMMA);

					supportTicket.setLppComponents(
						Arrays.asList(lppComponents));
				}

				supportTicket.setLppResolution(tokens[13]);

				try (StringWriter stringWriter = new StringWriter()) {
					objectMapper.writeValue(stringWriter, supportTicket);

					stringWriter.flush();

					jsonObjects.add(stringWriter.toString());
				}
			}
		}

		return jsonObjects;
	}

	protected ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();

		objectMapper.configure(
			MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return objectMapper;
	}

}