package com.apple.aid.parser;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.beans.IAdByNameBean;
import com.apple.iad.domain.ReportByName;
import com.apple.iad.ingestion.IAdReportInfoDirector;
import com.apple.iad.parser.ParserByName;

;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class ParserByNameTest {

	private ParserByName reportByName;
	@Mock
	private IAdReportInfoDirector director;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		reportByName = new ParserByName(director);
	}

	@Test
	public void mappingCSVToBeanClass() throws IOException {
		String content = IOUtils.toString(ClassLoader.getSystemResourceAsStream("report.csv"), ParserByName.REPORT_ENCODING);
		when(director.getReportContent()).thenReturn(content);

		IAdByNameBean jewel = reportByName.getReportContent().get(0);
		Assert.assertEquals("Blue Game", jewel.getName());
		Assert.assertEquals("$0.66", jewel.getEcpm());
		Assert.assertEquals("44.66%", jewel.getFillRate());
		Assert.assertEquals("65.87%", jewel.getIAdFillRate());
		Assert.assertEquals("567,570.00", jewel.getImpressions());
		Assert.assertEquals("1,270,840.00", jewel.getRequests());
		Assert.assertEquals("$373.96", jewel.getRevenue());
		Assert.assertEquals("0.31%", jewel.getTtr());

		verify(director).getReportContent();
	}

	@Test
	public void mappingCSVBeanClassToDomainModel() throws IOException {
		String content = IOUtils.toString(ClassLoader.getSystemResourceAsStream("report.csv"), ParserByName.REPORT_ENCODING);
		when(director.getReportContent()).thenReturn(content);

		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(ClassLoader.getSystemResourceAsStream("mapping.xml"));

		List<ReportByName> reports = new ArrayList<ReportByName>();
		for (IAdByNameBean reportContent : reportByName.getReportContent()) {
			reports.add(mapper.map(reportContent, ReportByName.class));
		}
		ReportByName jewel = reports.get(0);
		Assert.assertEquals("Blue Game", jewel.getName());
		Assert.assertEquals("0.66", jewel.getEcpm().toString());
		Assert.assertEquals("373.96", jewel.getRevenue().toString());
		Assert.assertEquals("44.66", jewel.getFillRate().toString());
		Assert.assertEquals("65.87", jewel.getIAdFillRate().toString());
		Assert.assertEquals("0.31", jewel.getTtr().toString());
		Assert.assertEquals("1270840.00", jewel.getRequests().toString());
		Assert.assertEquals("567570.00", jewel.getImpressions().toString());
		verify(director).getReportContent();
	}

}
