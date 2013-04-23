package com.apple.iad.ingestion.step;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;

/**
 * STEP4 - retrieve iAd reports. Critical step.
 * 
 * @author Georgi Lambov
 * 
 */
public class ReportContentStep extends RetrieveStep implements IngestionStep {

	@Setter
	private URI reportDownloadUrl;

	@Getter
	private InputStream reportContent;

	@Override
	public void execute() throws ClientProtocolException, IOException {
		if (reportDownloadUrl == null) {
			throw new IllegalArgumentException("reportDonwloadUrl is required");
		}

		httpget.setURI(reportDownloadUrl);

		cookieBuilder.setParameter("s_cc", "true");
		cookieBuilder
				.setParameter("s_sq",
						"pplesuperglobal%3D%2526pid%253DiTC%252520Home%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fiad.apple.com%25252Fitcportal%2526ot%253DA");

		httpget.setHeader("Cookie", cookieBuilder.toString());
		
		HttpResponse response = client.execute(httpget, localContext);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new IOException("Report cannot be downloaded " + response.getStatusLine().getStatusCode());		
		}

		// set report content
		HttpEntity entity = response.getEntity();
		reportContent = entity.getContent();
	}

}
