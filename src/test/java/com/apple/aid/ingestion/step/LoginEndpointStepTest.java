package com.apple.aid.ingestion.step;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.http.RawCookieBuilder;
import com.apple.iad.ingestion.step.LoginEndpointStep;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class LoginEndpointStepTest {

	private LoginEndpointStep endpointStep;

	@Mock
	private HttpGet httpget;
	@Mock
	private HttpClient client;
	@Mock
	private HttpContext localContext;
	@Mock
	private HttpResponse response;
	@Mock
	private StatusLine statusLine;
	@Mock
	private HttpEntity entity;

	@Before
	public void setupBeforeMethod() throws Exception {
		MockitoAnnotations.initMocks(this);
		endpointStep = new LoginEndpointStep();
		endpointStep.setCookieBuilder(new RawCookieBuilder());
		
	}

	// FIXME create fully unit test
	public void getFormActionFromPageContent() throws Exception {
		endpointStep.setClient(client);
		endpointStep.setHttpget(httpget);
		endpointStep.setLocalContext(localContext);
		
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(client.execute(httpget, localContext)).thenReturn(response);
		when(response.getEntity()).thenReturn(entity);
		InputStream is = new ByteArrayInputStream("reponse-stream".getBytes("UTF-8"));
		when(response.getEntity().getContent()).thenReturn(is);
		endpointStep.execute();
		assertEquals("/WebObjects/iTunesConnect.woa/wo/0.1.9.3.5.2.1.1.3.1.1", endpointStep.getLoginFormAction());
	}

	@Test
	public void fallBackWithHardcodedFormAction() throws Exception {
		endpointStep.setClient(client);
		endpointStep.setHttpget(httpget);
		endpointStep.setLocalContext(localContext);

		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(client.execute(httpget, localContext)).thenReturn(response);
		when(response.getEntity()).thenReturn(entity);

		InputStream is = new ByteArrayInputStream("reponse-stream".getBytes("UTF-8"));
		when(response.getEntity().getContent()).thenReturn(is);

		endpointStep.execute();
		assertEquals("/WebObjects/iTunesConnect.woa", endpointStep.getLoginFormAction());
	}

	@Test
	public void failAccessItunesConnectPage() throws Exception {
		endpointStep.setClient(client);
		endpointStep.setHttpget(httpget);
		endpointStep.setLocalContext(localContext);

		when(response.getStatusLine()).thenReturn(statusLine);
		when(client.execute(httpget, localContext)).thenReturn(response);

		endpointStep.execute();
		verify(response).getStatusLine();		
	}

}
