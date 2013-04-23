package com.apple.iad.parser;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.apple.iad.beans.IAdByNameBean;
import com.apple.iad.domain.ReportByName;
import com.apple.iad.ingestion.IAdReprotInfo;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class ParserByNameMapper implements IAdReprotInfo<List<ReportByName>> {

	private ParserByName parserByName;

	public ParserByNameMapper(ParserByName parserByName) {
		this.parserByName = parserByName;
	}

	@Override
	public List<ReportByName> getReportContent() {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(ClassLoader.getSystemResourceAsStream("mapping.xml"));
		List<ReportByName> reports = new ArrayList<ReportByName>();
		for (IAdByNameBean reportContent : parserByName.getReportContent()) {
			reports.add(mapper.map(reportContent, ReportByName.class));
		}

		return reports;
	}

}
