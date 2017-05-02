package com.liferay.support.bi.data.binding.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.support.bi.data.binding.JSONObjectMapper;
import com.liferay.support.bi.data.binding.stubs.SupportTicket;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

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

		try (Workbook workbook = WorkbookFactory.create(inputStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			sheet.forEach(row -> {
				SupportTicket supportTicket = new SupportTicket();

				supportTicket.setTicketNumber(getCellValue(row, 0));

				supportTicket.setSubject(getCellValue(row, 1));
				supportTicket.setDescription(getCellValue(row, 2));
				supportTicket.setStatus(getCellValue(row, 3));
				supportTicket.setTicketAssignment(getCellValue(row, 4));
				supportTicket.setLiferayVersion(getCellValue(row, 5));
				supportTicket.setAccountCode(getCellValue(row, 6));
				supportTicket.setSupportRegion(getCellValue(row, 7));
				supportTicket.setComponent(getCellValue(row, 8));
				supportTicket.setTicketCreateDate(getCellValue(row, 9));
				supportTicket.setTicketClosedDate(getCellValue(row, 10));
				supportTicket.setLppTicket(getCellValue(row, 11));

				String lppComponentsString = getCellValue(row, 12);

				if (lppComponentsString.startsWith(StringPool.QUOTE)) {
					lppComponentsString = lppComponentsString.substring(
						1, lppComponentsString.length() - 1);

					String[] lppComponents = StringUtil.split(
						lppComponentsString, StringPool.COMMA);

					supportTicket.setLppComponents(
						Arrays.asList(lppComponents));
				}

				supportTicket.setLppResolution(getCellValue(row, 13));

				try (StringWriter stringWriter = new StringWriter()) {
					objectMapper.writeValue(stringWriter, supportTicket);

					stringWriter.flush();

					jsonObjects.add(stringWriter.toString());
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			});
		}
		catch (InvalidFormatException ife) {
			ife.printStackTrace();
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

	protected String getCellValue(Row row, int index) {
		Cell cell = row.getCell(index);

		if (cell != null) {
			return cell.getStringCellValue();
		}

		return StringPool.BLANK;
	}

}