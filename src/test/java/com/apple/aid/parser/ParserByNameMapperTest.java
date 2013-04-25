package com.apple.aid.parser;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.domain.IAdReportByName;
import com.apple.iad.ingestion.IAdReportInfoDirector;
import com.apple.iad.ingestion.IAdReprotInfo;
import com.apple.iad.parser.ParserByName;
import com.apple.iad.parser.ParserByNameMapper;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class ParserByNameMapperTest {

	private IAdReprotInfo<?> mapper;
	private ParserByName parserByName;
	@Mock
	private IAdReportInfoDirector director;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		parserByName = new ParserByName(director);
		mapper = new ParserByNameMapper(parserByName);
	}

	@Test
	public void mapBeans() throws IOException {
		String content = IOUtils.toString(ClassLoader.getSystemResourceAsStream("report.csv"), ParserByName.REPORT_ENCODING);
		
		when(director.getReportContent()).thenReturn(content);
		@SuppressWarnings("unchecked")
		List<IAdReportByName> reportContent = (List<IAdReportByName>) mapper.getReportContent();
		IAdReportByName name = reportContent.get(0);
		Assert.assertEquals("Blue Game", name.getName());
		Assert.assertEquals("373.96", name.getRevenue().toString());
		verify(director).getReportContent();
	}

}
