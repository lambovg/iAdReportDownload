package com.apple.aid.ingestion.builder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.ingestion.builder.IAdReportByNameBuilder;
import com.apple.iad.ingestion.builder.IAdReportInfo;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdReportByNameBuilderTest {

	private IAdReportByNameBuilder byNameBuilder;
	private IAdReportInfo reportInfo;
	@Mock
	private ReportRequestBean reportRequest;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		byNameBuilder = new IAdReportByNameBuilder();
		reportInfo = new IAdReportInfo();
		byNameBuilder.setReportInfo(reportInfo);
		
	}

	@Test
	public void buildReportUrlWithFromAndToDates() {
		reportInfo.setReportRequest(reportRequest);
		when(reportRequest.getFromDate()).thenReturn(new Date());
		when(reportRequest.getToDate()).thenReturn(new Date());

		byNameBuilder.buildReportUrl();
		//TODO compare query strings

		verify(reportRequest).getFromDate();
		verify(reportRequest).getToDate();
	}
	
	@Test
	public void buildReportUrlOnlyWithFromDate() {
		reportInfo.setReportRequest(reportRequest);
		when(reportRequest.getFromDate()).thenReturn(new Date());
		when(reportRequest.getDateRange()).thenReturn(ReportRequestBean.DATE_RANGE_CUSTOM);
		
		byNameBuilder.buildReportUrl();
		//TODO compare query string
		
		verify(reportRequest).getFromDate();
	}

	@Test
	public void initWithoutMock() throws ParseException {
		IAdReportInfo info = new IAdReportInfo();
		ReportRequestBean r = new ReportRequestBean();
		
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
		Date d = format.parse("04/04/13");
		
		r.setFromDate(d);
		r.setToDate(d);
		r.setProviderId(Long.valueOf(1));
		info.setReportRequest(r);

		IAdReportByNameBuilder b = new IAdReportByNameBuilder();
		b.setReportInfo(info);
		b.buildReportUrl();

		Assert.assertEquals(
				"https://iad.apple.com/itcportal/generatecsv?pageName=app_homepage&dashboardType=publisher&publisherId=1&dateRange=customDateRange&searchTerms=Search%2520Apps&adminFlag=false&fromDate=04%2F04%2F13&toDate=04%2F04%2F13&dataType=byName",
				info.getReportUrl().toString());
	}

}
