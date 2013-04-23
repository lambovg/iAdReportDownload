package com.apple.iad.parser;

import com.apple.iad.ingestion.IAdReportInfoDirector;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public abstract class IAdReportParser {

	protected IAdReportInfoDirector director;

	public IAdReportParser(IAdReportInfoDirector director) {
		this.director = director;
	}

}
