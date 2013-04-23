package com.apple.iad.ingestion.builder;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.utils.URIBuilder;

import com.apple.iad.beans.ReportRequestBean;

/**
 * Create necessary build options for iAd reports by date.
 * 
 * <p>
 * For now the only difference is reportUrl.
 * </p>
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdReportByNameBuilder extends IAdReportBuilder {

	@Override
	public void buildReportUrl() {
		ReportRequestBean request = reportInfo.getReportRequest();

		String publisherId = Long.toString(request.getProviderId());
		DateFormat d = new SimpleDateFormat("MM/dd/yy");
		Date from = request.getFromDate();
		Date to = request.getToDate();
		String fromDate = d.format(from);
		String toDate = fromDate;
		String dateRange = request.getDateRange();
		
		if (to == null && dateRange.equals(ReportRequestBean.DATE_RANGE_FOURTEEN_DAYS)) {
			toDate = "";
		} else {
			toDate = d.format(from);
		}

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("iad.apple.com").setPath("/itcportal/generatecsv").setParameter("pageName", "app_homepage")
				.setParameter("dashboardType", "publisher").setParameter("publisherId", publisherId).setParameter("dateRange", dateRange)
				.setParameter("searchTerms", "Search%20Apps").setParameter("adminFlag", "false").setParameter("fromDate", fromDate)
				.setParameter("toDate", toDate).setParameter("dataType", ReportRequestBean.REPORT_TYPE_BY_NAME);

		URI uri;
		try {
			uri = builder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return;
		}

		reportInfo.setReportUrl(uri);
	}

}
