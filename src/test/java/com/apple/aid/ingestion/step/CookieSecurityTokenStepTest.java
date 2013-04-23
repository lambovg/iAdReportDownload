package com.apple.aid.ingestion.step;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicHeaderElement;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HttpContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apple.iad.http.RawCookieBuilder;
import com.apple.iad.ingestion.step.CookieSecurityTokenStep;

/**
 * 
 * @author Georgi Lambov
 *
 */
public class CookieSecurityTokenStepTest {
	
	private CookieSecurityTokenStep tokenStep;
	
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
	private BasicHeaderElementIterator it;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tokenStep = new CookieSecurityTokenStep();
		tokenStep.setCookieBuilder(new RawCookieBuilder());
	}
	
	@Test
	public void retrieveResponseCookie() throws Exception {
		tokenStep.setClient(client);
		tokenStep.setHttpget(httpget);
		tokenStep.setLocalContext(localContext);
		tokenStep.setIt(it);
		
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_OK);
		when(client.execute(httpget, localContext)).thenReturn(response);
		when(it.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		
		BasicHeaderElement myAcInfo = new BasicHeaderElement("myacinfo", "account-info");
		BasicHeaderElement ds01 = new BasicHeaderElement("s_vi", "security token");
		when(it.nextElement()).thenReturn(myAcInfo).thenReturn(ds01);
		
		tokenStep.execute();
		
		Assert.assertEquals("s_vi=security+token", tokenStep.getCookieBuilder().toString());
		
		verify(response.getStatusLine()).getStatusCode();
	}
	
	@Test
	public void failToRetrieveResopnseCookie() throws Exception {
		tokenStep.setClient(client);
		tokenStep.setHttpget(httpget);
		tokenStep.setLocalContext(localContext);
		tokenStep.setIt(it);
		
		when(response.getStatusLine()).thenReturn(statusLine);
		when(response.getStatusLine().getStatusCode()).thenReturn(HttpStatus.SC_BAD_GATEWAY);
		when(client.execute(httpget, localContext)).thenReturn(response);
		when(it.hasNext()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		
		BasicHeaderElement myAcInfo = new BasicHeaderElement("myacinfo", "account-info");
		BasicHeaderElement ds01 = new BasicHeaderElement("s_vi", "security token");
		when(it.nextElement()).thenReturn(myAcInfo).thenReturn(ds01);
		
		tokenStep.execute();
		
		Assert.assertEquals("", tokenStep.getCookieBuilder().toString());
		
		verify(response.getStatusLine()).getStatusCode();
	}
	
}
