package com.apple.iad.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
		
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(new File("src/main/resources/META-INF/mapping.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		mapper.addMapping(stream);
		List<IAdReportByName> reports = new ArrayList<IAdReportByName>();
		for (IAdByNameBean reportContent : parserByName.getReportContent()) {
			reports.add(mapper.map(reportContent, IAdReportByName.class));
		}

		return reports;
	}

}
