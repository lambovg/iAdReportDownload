package com.apple.iad.domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Collection of reports with their content and meta data.
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdReportByNameStory {

	@Getter
	@Setter
	private List<IAdReportByName> reports;
	@Getter
	@Setter
	private Date fromDate;
	@Getter
	@Setter
	private Date toDate;
	@Getter
	@Setter
	private String content;
	@Getter
	@Setter
	private String reportCurrency = "USD";

}
