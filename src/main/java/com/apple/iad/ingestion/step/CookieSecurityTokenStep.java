package com.apple.iad.ingestion.step;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeaderElementIterator;

/**
 * STEP2 - Retrieves security token cookie. This is non-critical step and its possible to be skipped.
 * 
 * @author Georgi Lambov
 * 
 */
public class CookieSecurityTokenStep extends RetrieveStep implements IngestionStep {

	@Getter
	@Setter
	private BasicHeaderElementIterator it;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.iad.ingtestion.IngestionStrategy#execute()
	 */
	@Override
	public void execute() throws URISyntaxException  {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost("metrics.apple.com").setPath("/b/ss/appleitmsna/1/H.24--NS/0");
		URI uri = builder.build();

		httpget.setURI(uri);

		HttpResponse response = null;
		try {
			response = client.execute(httpget, localContext);
			response = client.execute(httpget, localContext);
		} catch (ClientProtocolException e) {
			// TODO logging
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO logging
			e.printStackTrace();
			return;
		}

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			// TODO logging
			return;
		}

		it = (it == null) ? new BasicHeaderElementIterator(response.headerIterator("Set-Cookie")) : it;
		while (it.hasNext()) {
			HeaderElement elem = it.nextElement();
			if (elem.getName().equals("s_vi")) {
				cookieBuilder.setParameter("s_vi", elem.getValue());
				break;
			}
		}
	}

}
