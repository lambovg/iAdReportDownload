package com.apple.iad.ingestion.step;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * STEP1 - loading itunes connect page and retrieve login form action.
 * 
 * @author Georgi Lambov
 * 
 */
public class LoginEndpointStep extends RetrieveStep implements IngestionStep {

	@Getter
	@Setter
	private String loginFormAction;

	public static final String LOGIN_FORM_ACTION = "/WebObjects/iTunesConnect.woa";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.apple.iad.ingtestion.IngestionStrategy#execute()
	 */
	@Override
	public void execute() throws URISyntaxException, IOException {
		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("itunesconnect.apple.com").setPath("/");
		URI uri = builder.build();

		httpget.setURI(uri);

		HttpResponse response = null;
		try {
			response = client.execute(httpget, localContext);
		} catch (IOException e) {
			// TODO logging
			e.printStackTrace();
			loginFormAction = LOGIN_FORM_ACTION;
			return;
		}
		if (HttpStatus.SC_OK != response.getStatusLine().getStatusCode()) {
			// TODO logging
			loginFormAction = LOGIN_FORM_ACTION;
			return;
		}

		HttpEntity entity = response.getEntity();
		InputStream instream = null;
		Document loginPageContent = null;
		try {
			instream = entity.getContent();
			loginPageContent = Jsoup.parse(IOUtils.toString(instream, "UTF-8"));
		} catch (IllegalStateException e) {
			// fallback to hard-coded url action.
			loginFormAction = LOGIN_FORM_ACTION;
			e.printStackTrace();
		} catch (IOException e) {
			// fallback to hard-coded url action.
			loginFormAction = LOGIN_FORM_ACTION;
			e.printStackTrace();
		} finally {
			instream.close();
		}
		
		Elements loginForm = loginPageContent.select("div#ds_container > form");

		loginFormAction = loginForm.attr("action");
		if (loginFormAction == null || (loginFormAction != null && loginFormAction.isEmpty())) {
			// fallback to hard-coded url action.
			loginFormAction = LOGIN_FORM_ACTION;
		}
	}

}
