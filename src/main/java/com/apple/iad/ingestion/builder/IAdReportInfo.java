package com.apple.iad.ingestion.builder;

import java.net.URI;

import lombok.Getter;
import lombok.Setter;

import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.ingestion.step.IAdStepContext;

/**
 * Actual product that is managed by the different builder initilizations.
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdReportInfo {

	@Setter
	@Getter
	private ReportRequestBean reportRequest;

	@Setter
	@Getter
	private URI reportUrl;

	@Getter
	@Setter
	private IAdStepContext stepContext;

}
