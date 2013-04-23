package com.apple.aid.ingestion;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.ingestion.IAdReportInfoDirector;
import com.apple.iad.ingestion.builder.IAdReportByNameBuilder;
import com.apple.iad.ingestion.builder.IAdReportInfo;
import com.apple.iad.ingestion.step.ReportContentStep;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdReportInfoDirectorTest {

	private IAdReportInfoDirector director;
	@Mock
	private IAdReportByNameBuilder byNameBuilder;
	@Mock
	private ReportContentStep contentStep;
	@Mock
	private IAdReportInfo reportInfo;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		director = new IAdReportInfoDirector();
		director.setReportBuilder(byNameBuilder);
		director.setContentStep(contentStep);
	}

	@Test
	public void retrieveReportOk() throws URISyntaxException {
		when(byNameBuilder.getReportInfo()).thenReturn(reportInfo);
		when(byNameBuilder.getReportInfo().getReportUrl()).thenReturn(new URI("test-uri"));
		when(contentStep.getReportContent()).thenReturn(ClassLoader.getSystemResourceAsStream("report.csv"));

		//FIXME null pointer execption in the test
		director.constructReport(new ReportRequestBean());
		//TODO decorator
		String stream = director.getReportContent();
		Assert.assertNotNull(stream); // expected
		
		verify(byNameBuilder.getReportInfo()).getReportUrl();
	}

}
