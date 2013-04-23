package com.apple.iad.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class ReportByName {

	@Getter
	@Setter
	private String name; //
	@Getter
	@Setter
	private BigDecimal revenue; // $12.13
	@Getter
	@Setter
	private BigDecimal ecpm; // $12.34
	@Getter
	@Setter
	private BigDecimal requests;
	@Getter
	@Setter
	private BigDecimal impressions;
	@Getter
	@Setter
	private BigDecimal fillRate; // percents
	@Getter
	@Setter
	private BigDecimal iAdFillRate; // percents
	@Getter
	@Setter
	private BigDecimal ttr; // percents

}
