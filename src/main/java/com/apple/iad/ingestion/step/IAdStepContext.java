package com.apple.iad.ingestion.step;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SchemeRegistryFactory;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.apple.iad.http.RawCookieBuilder;

/**
 * Container to execute trategies
 * 
 * @author Georgi Lambov
 * 
 */
public class IAdStepContext {

	@Getter
	@Setter
	private RetrieveStep retrieveStep;
	@Getter
	@Setter
	private RawCookieBuilder cookieBuilder;

	private HttpClient client;
	private HttpPost httpPost;
	private HttpGet httpGet;
	private HttpContext localContext;

	public IAdStepContext() {
		if (cookieBuilder == null) {
			cookieBuilder = new RawCookieBuilder();
		}
		postConstructSetup();

	}

	public IAdStepContext(RetrieveStep retrieveStep) {
		this.retrieveStep = retrieveStep;
		if (cookieBuilder == null) {
			cookieBuilder = new RawCookieBuilder();
		}
		postConstructSetup();
	}

	/**
	 * Runs concreate strategy.
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public void execute() throws UnsupportedEncodingException, ClientProtocolException, URISyntaxException, IOException {
		retrieveStep.setCookieBuilder(cookieBuilder);
		retrieveStep.setClient(client);
		retrieveStep.setHttpget(httpGet);
		retrieveStep.setHttpPost(httpPost);
		retrieveStep.setLocalContext(localContext);

		retrieveStep.execute();
	}

	private void postConstructSetup() {
		PoolingClientConnectionManager cxMgr = new PoolingClientConnectionManager(SchemeRegistryFactory.createDefault());
		cxMgr.setMaxTotal(5);

		client = new DefaultHttpClient(cxMgr);
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		client.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, "UTF-8");
		client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, RetrieveStep.USER_AGENT_HEADER);
		client.getParams().setParameter(CoreProtocolPNames.WAIT_FOR_CONTINUE, Boolean.TRUE);

		httpGet = new HttpGet();
		httpPost = new HttpPost();
		localContext = new BasicHttpContext();
	}

}
