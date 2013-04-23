package com.apple.iad.ingestion;

import java.io.IOException;

import lombok.Setter;

import org.apache.commons.io.IOUtils;

import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.ingestion.builder.IAdReportBuilder;
import com.apple.iad.ingestion.builder.IAdReportInfo;
import com.apple.iad.ingestion.step.ReportContentStep;
import com.apple.iad.parser.ParserByName;

/**
 * Construct different report types.
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdReportInfoDirector implements IAdReprotInfo<String> {

	@Setter
	private IAdReportBuilder reportBuilder;
	@Setter
	private ReportContentStep contentStep = new ReportContentStep();

	public IAdReportInfo getReportInfo() {
		return reportBuilder.getReportInfo();
	}

	/**
	 * Creates report for the specific builder.
	 * 
	 * @return
	 */
	public void constructReport(ReportRequestBean reportRequest) {
		reportBuilder.createNewReport(reportRequest);
		reportBuilder.buildReportStream();
		reportBuilder.buildReportUrl();

		IAdReportInfo reportInfo = reportBuilder.getReportInfo();
		contentStep.setReportDownloadUrl(reportInfo.getReportUrl());
		try {
			reportInfo.getStepContext().setRetrieveStep(contentStep);
			reportInfo.getStepContext().execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getReportContent() {
		String content = null;
		
		try {
			content = IOUtils.toString(contentStep.getReportContent(), ParserByName.REPORT_ENCODING);
			contentStep.getReportContent().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content;
	}

}
