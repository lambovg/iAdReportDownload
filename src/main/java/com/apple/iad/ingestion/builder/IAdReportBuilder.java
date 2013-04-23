package com.apple.iad.ingestion.builder;

import lombok.Getter;
import lombok.Setter;

import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.ingestion.step.AccountInfoStep;
import com.apple.iad.ingestion.step.CookieSecurityTokenStep;
import com.apple.iad.ingestion.step.IAdStepContext;
import com.apple.iad.ingestion.step.LoginEndpointStep;

/**
 * Report builder abstraction used by differnt report builders- byName, byCountry, byDates.
 * 
 * @author Georgi Lambov
 * 
 */
abstract public class IAdReportBuilder {

	@Getter
	@Setter
	protected IAdReportInfo reportInfo;

	private IAdStepContext stepContext;

	/**
	 * 
	 */
	public void createNewReport(ReportRequestBean reportRequest) {
		reportInfo = new IAdReportInfo();
		reportInfo.setReportRequest(reportRequest);
		stepContext = new IAdStepContext();
		reportInfo.setStepContext(stepContext);

	}

	/**
	 * Builds specific urls for different report types
	 */
	public abstract void buildReportUrl();

	/**
	 * Creates reportStream necessary for reportUrl to conitnue.
	 */
	public void buildReportStream() {
		LoginEndpointStep endpointStrategy = new LoginEndpointStep();
		CookieSecurityTokenStep tokenStep = new CookieSecurityTokenStep();
		AccountInfoStep infoStrategy = new AccountInfoStep();

		try {
			// step1
			stepContext.setRetrieveStep(endpointStrategy);
			stepContext.execute();

			// step2
			stepContext.setRetrieveStep(tokenStep);
			stepContext.execute();

			// step3
			infoStrategy.setLoginFormAction(endpointStrategy.getLoginFormAction());
			infoStrategy.setReportRequest(reportInfo.getReportRequest());
			stepContext.setRetrieveStep(infoStrategy);
			stepContext.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
