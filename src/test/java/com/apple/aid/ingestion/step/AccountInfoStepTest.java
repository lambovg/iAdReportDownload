package com.apple.aid.ingestion.step;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.beans.ReportRequestBean;
import com.apple.iad.http.RawCookieBuilder;
import com.apple.iad.ingestion.step.AccountInfoStep;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class AccountInfoStepTest {

	private AccountInfoStep infoStep;

	@Mock
	private HttpPost httpPost;
	@Mock
	private HttpClient client;
	@Mock
	private HttpContext localContext;
	@Mock
	private StatusLine statusLine;
	@Mock
	private HttpResponse response;
	@Mock
	private HttpEntity entity;
	@Mock
	private HeaderIterator headerIterator;
	@Mock
	private BasicHeaderElementIterator it;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		infoStep = new AccountInfoStep();
		ReportRequestBean reportRequest = new ReportRequestBean();
		reportRequest.setUsername("username");
		reportRequest.setPassword("password");

		infoStep.setReportRequest(reportRequest);
		infoStep.setLoginFormAction("http://fake-login.form.action");
		infoStep.setCookieBuilder(new RawCookieBuilder());

		infoStep.setClient(client);
		infoStep.setHttpPost(httpPost);
		infoStep.setLocalContext(localContext);
	}

	@Test(expected = IllegalArgumentException.class)
	public void missingReportRequest() throws Exception {
		infoStep.setReportRequest(null);
		infoStep.execute();
	}

	@Test(expected = IllegalArgumentException.class)
	public void missingLoginFormAction() throws Exception {
		infoStep.setLoginFormAction(null);
		infoStep.execute();
	}

	@Test(expected = IllegalArgumentException.class)
	public void missingLoginFormActionAndReportRequest() throws Exception {
		infoStep.setLoginFormAction(null);
		infoStep.setReportRequest(null);
		infoStep.execute();
	}

	@Test
	public void exstingAccountInfo() throws Exception {
		infoStep.setIt(it);
		
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_MOVED_TEMPORARILY);
		when(client.execute(httpPost, localContext)).thenReturn(response);
		when(response.getEntity()).thenReturn(entity);

		InputStream is = new ByteArrayInputStream("reponse-stream".getBytes("UTF-8"));
		when(response.getEntity().getContent()).thenReturn(is);
		
		// try with 3 iterations instead of 2 to test break statement
		when(it.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		BasicHeaderElement myAcInfo = new BasicHeaderElement("myacinfo", "account-info");
		BasicHeaderElement ds01 = new BasicHeaderElement("ds01", "data-source");
		when(it.nextElement()).thenReturn(myAcInfo).thenReturn(ds01);
		
		infoStep.execute();
		
		Assert.assertEquals("myacinfo=account-info; ds01=data-source", infoStep.getCookieBuilder().toString());

		verify(response).getEntity();
		verify(response.getStatusLine()).getStatusCode();
	}

}
