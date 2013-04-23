package com.apple.iad.domain;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class ReportByNameStory {

	@Getter
	@Setter
	private List<ReportByName> report;
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
