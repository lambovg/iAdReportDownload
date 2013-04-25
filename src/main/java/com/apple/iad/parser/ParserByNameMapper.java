package com.apple.iad.parser;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

import com.apple.iad.beans.IAdByNameBean;
import com.apple.iad.domain.IAdReportByName;
import com.apple.iad.ingestion.IAdReprotInfo;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class ParserByNameMapper implements IAdReprotInfo<List<IAdReportByName>> {

	private ParserByName parserByName;

	public ParserByNameMapper(ParserByName parserByName) {
		this.parserByName = parserByName;
	}

	/*
	 * (non-Javadoc)
	 * @see com.apple.iad.ingestion.IAdReprotInfo#getReportContent()
	 */
	@Override
	public List<IAdReportByName> getReportContent() {
		DozerBeanMapper mapper = new DozerBeanMapper();
		mapper.addMapping(ClassLoader.getSystemResourceAsStream("mapping.xml"));
		List<IAdReportByName> reports = new ArrayList<IAdReportByName>();
		for (IAdByNameBean reportContent : parserByName.getReportContent()) {
			reports.add(mapper.map(reportContent, IAdReportByName.class));
		}

		return reports;
	}

}
