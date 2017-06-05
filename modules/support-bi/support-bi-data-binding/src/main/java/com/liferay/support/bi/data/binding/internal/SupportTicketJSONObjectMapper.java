package com.liferay.support.bi.data.binding.internal;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslator;
import com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.support.bi.data.binding.JSONObjectMapper;
import com.liferay.support.bi.data.binding.stubs.SupportTicket;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.tika.langdetect.OptimaizeLangDetector;

import org.osgi.service.component.annotations.Activate;
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

	@Activate
	public void activate() throws Exception {
		_translator = MicrosoftTranslatorFactoryUtil.getMicrosoftTranslator();
		_detector = new OptimaizeLangDetector();
		_detector.loadModels();
	}

	@Override
	public Collection<String> convert(InputStream inputStream)
		throws IOException {

		Collection<String> jsonObjects = new ArrayList<>();

		ObjectMapper objectMapper = createObjectMapper();

		try (Workbook workbook = WorkbookFactory.create(inputStream)) {
			Sheet sheet = workbook.getSheetAt(0);

			sheet.forEach(row -> {
				SupportTicket supportTicket = new SupportTicket();

				supportTicket.setAccountCode(getCellValue(row, 0, false));
				supportTicket.setComponent(getCellValue(row, 1, false));
				supportTicket.setDescription(
					translateTo(getCellValue(row, 2, false), "en"));
				supportTicket.setLiferayVersion(getCellValue(row, 6, false));
				supportTicket.setLppTicket(getCellValue(row, 7, false));
				supportTicket.setStatus(getCellValue(row, 8, false));
				supportTicket.setSubject(getCellValue(row, 9, false));
				supportTicket.setSupportRegion(getCellValue(row, 10, false));
				supportTicket.setTicketAssignment(getCellValue(row, 11, false));
				supportTicket.setTicketCreateDate(getCellValue(row, 13, true));
				supportTicket.setTicketNumber(getCellValue(row, 14, false));
				supportTicket.setLppResolution(getCellValue(row, 16, false));

				String closedDate = getCellValue(row, 12, true);

				if (closedDate.isEmpty()) {
					supportTicket.setTicketClosedDate(null);
				}
				else {
					supportTicket.setTicketClosedDate(closedDate);
				}

				String lppComponentsString = getCellValue(row, 15, false);

				if (lppComponentsString.startsWith(StringPool.QUOTE)) {
					lppComponentsString = lppComponentsString.substring(
						1, lppComponentsString.length() - 1);

					String[] lppComponents = StringUtil.split(
						lppComponentsString, StringPool.COMMA);

					supportTicket.setLppComponents(
						Arrays.asList(lppComponents));
				}

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

	protected String getCellValue(Row row, int index, boolean date) {
		Cell cell = row.getCell(index);

		if (cell == null) {
			return StringPool.BLANK;
		}

		CellType cellType = cell.getCellTypeEnum();

		if (cellType.equals(CellType.NUMERIC)) {
			if (date) {
				DateFormat dateFormat = new SimpleDateFormat(
					"MM/dd/yy HH:mm:ss a");

				return dateFormat.format(cell.getDateCellValue());
			}

			return String.valueOf(cell.getNumericCellValue());
		}
		else if (cellType.equals(CellType.BOOLEAN)) {
			return String.valueOf(cell.getBooleanCellValue());
		}
		else if (cellType.equals(CellType.STRING)) {
			return String.valueOf(cell.getStringCellValue());
		}

		return StringPool.BLANK;
	}

	private String translateTo(String text, String desiredLanguage) {
		String curLanguage = _detector.detect(text).getLanguage();

		if (!curLanguage.equals(desiredLanguage)) {
			try {
				return _translator.translate(
					curLanguage, desiredLanguage, text);
			}
			catch (Exception e) {
				System.out.print(
					"Failed to translate document, returning original text.\n");
			}
		}

		return text;
	}

	private OptimaizeLangDetector _detector;
	private MicrosoftTranslator _translator;

}