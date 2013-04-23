package com.apple.iad.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Public iAd report parameters that are required for iAd report downloading.
 * 
 * @author Georgi Lambov
 * 
 */
public class ReportRequestBean {

	@Getter
	@Setter
	private String username;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private long providerId;
	@Getter
	@Setter
	private String reportType = REPORT_TYPE_BY_NAME;
	
	/**
	 * Extreme date (greater date) when repots will begin to fetch.
	 */
	@Getter
	@Setter
	private Date fromDate;
	
	/**
	 * Optional parameter.
	 */
	@Getter
	@Setter
	private Date toDate;
	
	/**
	 * Days go back from fromDate field.
	 */
	@Getter
	@Setter
	private int daysGoBack = 14;
	@Getter
	@Setter
	private String dateRange = DATE_RANGE_CUSTOM;

	public static final String REPORT_TYPE_BY_NAME = "byName";
	public static final String REPORT_TYPE_BY_COUNTRY = "byCountry";
	public static final String REPORT_TYPE_BY_DATES = "byDates";
	public static final String DATE_RANGE_CUSTOM = "customDate";
	public static final String DATE_RANGE_FOURTEEN_DAYS = "fourteenDays";

}
