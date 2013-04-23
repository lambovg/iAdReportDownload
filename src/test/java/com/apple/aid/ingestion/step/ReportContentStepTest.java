package com.apple.aid.ingestion.step;

import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.http.RawCookieBuilder;
import com.apple.iad.ingestion.step.ReportContentStep;

public class ReportContentStepTest {

	private ReportContentStep contentStep;

	@Mock
	private HttpGet httpget;
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

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		contentStep = new ReportContentStep();

		URIBuilder builder = new URIBuilder();
		builder.setScheme("https").setHost("itunes-nonexisting-report").setPath("/download-reports");

		contentStep.setReportDownloadUrl(builder.build());
		contentStep.setClient(client);
		contentStep.setHttpget(httpget);
		contentStep.setLocalContext(localContext);
		contentStep.setCookieBuilder(new RawCookieBuilder());
	}

	@Test
	public void downloadReportOk() throws Exception {
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(client.execute(httpget, localContext)).thenReturn(response);
		when(response.getEntity()).thenReturn(entity);

		InputStream is = new ByteArrayInputStream("reponse-stream".getBytes("UTF-8"));
		when(response.getEntity().getContent()).thenReturn(is);

		contentStep.execute();
	}

	@Test(expected = IOException.class)
	public void downloadReportFauildDueWrongStatusCode() throws Exception {
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_BAD_GATEWAY);
		when(client.execute(httpget, localContext)).thenReturn(response);
		when(response.getEntity()).thenReturn(entity);

		InputStream is = new ByteArrayInputStream("reponse-stream".getBytes("UTF-8"));
		when(response.getEntity().getContent()).thenReturn(is);

		contentStep.execute();
	}

}
