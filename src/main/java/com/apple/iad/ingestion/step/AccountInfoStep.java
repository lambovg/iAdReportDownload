package com.apple.iad.ingestion.step;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;

import com.apple.iad.beans.ReportRequestBean;

/**
 * STEP3 - login to itunesconnect to get account info cookie and other additional cookie information.
 * 
 * Critical information is myacinfo parameter.
 * 
 * @author Georgi Lambov
 * 
 */
public class AccountInfoStep extends RetrieveStep implements IngestionStep {

	@Getter
	@Setter
	private String loginFormAction;

	@Setter
	private ReportRequestBean reportRequest;

	@Setter
	private BasicHeaderElementIterator it;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.iad.ingtestion.IngestionStrategy#execute()
	 */
	@Override
	public void execute() throws URISyntaxException, UnsupportedEncodingException {
		if (loginFormAction == null) {
			throw new IllegalArgumentException("iadLoginAction is required");
		}

		if (reportRequest == null) {
			throw new IllegalArgumentException("reportRequest is required");
		}

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("itunesconnect.apple.com").setPath(loginFormAction);
		URI uri = builder.build();
		uri = builder.build();

		String accountName = reportRequest.getUsername();
		String accountPassword = reportRequest.getPassword();

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("theAccountName", accountName));
		formparams.add(new BasicNameValuePair("theAccountPW", accountPassword));
		formparams.add(new BasicNameValuePair("1.Continue.x", "0"));
		formparams.add(new BasicNameValuePair("1.Continue.y", "0"));
		formparams.add(new BasicNameValuePair("theAuxValue", ""));

		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, "UTF-8");

		httpPost.setURI(uri);
		httpPost.setHeader("User-Agent", USER_AGENT_HEADER);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

		httpPost.setEntity(formEntity);
		HttpResponse response = null;
		try {
			response = client.execute(httpPost, localContext);
		} catch (ClientProtocolException e) {
			// logging
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO logging
			e.printStackTrace();
			return;
		}

		it = (it == null) ? new BasicHeaderElementIterator(response.headerIterator("Set-Cookie")) : it;
		
		int neededElements = 2;
		int findedElements = 0;
		while (it.hasNext()) {
			if (neededElements == findedElements) {
				break;
			}
			HeaderElement elem = it.nextElement();
			if (elem.getName().equals("myacinfo")) {
				cookieBuilder.setParameter("myacinfo", elem.getValue());
				findedElements++;
			}
			if (elem.getName().equals("ds01")) {
				cookieBuilder.setParameter("ds01", elem.getValue());
				findedElements++;
			}
		}

		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_MOVED_TEMPORARILY) {
			// log possible problem
			// TODO logging
		}
	}

}
