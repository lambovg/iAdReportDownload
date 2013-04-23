package com.apple.iad.beans;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdByNameBean {

	@Getter
	@Setter
	private String name; //
	@Getter
	@Setter
	private String revenue; // $12.13
	@Getter
	@Setter
	private String ecpm; // $12.34
	@Getter
	@Setter
	private String requests;
	@Getter
	@Setter
	private String impressions;
	@Getter
	@Setter
	private String fillRate; // percents
	@Getter
	@Setter
	private String iAdFillRate; // percents
	@Getter
	@Setter
	private String ttr; // percents

}
