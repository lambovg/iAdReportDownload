package com.apple.aid.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.api.DownloadIAdReport;
import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.ingestion.IAdReportInfoDirector;
import com.apple.iad.parser.ParserByName;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class DownloadIAdReportTest {

	private DownloadIAdReport downloadIAdReport;

	private ReportRequestBean reportRequest;
	@Mock
	private IAdReportInfoDirector director;

	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);

		downloadIAdReport = new DownloadIAdReport();
		reportRequest = new ReportRequestBean();

		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		Date d = format.parse("04/04/13");

		reportRequest.setFromDate(d);
		downloadIAdReport.setReportRequest(reportRequest);
		downloadIAdReport.setDirector(director);
	}
	
	@Test
	public void fetchReportsByNameForXDaysBack() throws IOException {
		reportRequest.setDaysGoBack(2);
		
		String content = IOUtils.toString(ClassLoader.getSystemResourceAsStream("report.csv"), ParserByName.REPORT_ENCODING);
		when(director.getReportContent()).thenReturn(content);
		downloadIAdReport.fetchDailyReportsByName();

		verify(director, times(4)).getReportContent();
	}

	
	public void fetchReportsByNameForSimpleDate() throws IOException {
		String content = IOUtils.toString(ClassLoader.getSystemResourceAsStream("report.csv"), ParserByName.REPORT_ENCODING);
		reportRequest.setDaysGoBack(1);
		when(director.getReportContent()).thenReturn(content);
		downloadIAdReport.fetchDailyReportsByName();

		verify(director, times(2)).getReportContent();
	}

}
