package com.apple.iad.ingestion.step;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.protocol.HttpContext;

import com.apple.iad.http.RawCookieBuilder;

/**
 * Avoid duplication is concrete strategy implementations.
 * 
 * @author Georgi Lambov
 * 
 */
abstract class RetrieveStep implements IngestionStep {

	@Getter
	@Setter
	protected HttpClient client;
	@Getter
	@Setter
	protected HttpContext localContext;
	@Getter
	@Setter
	protected HttpGet httpget;
	@Getter
	@Setter
	protected HttpPost httpPost;
	@Getter
	@Setter
	protected RawCookieBuilder cookieBuilder;

	protected static final String USER_AGENT_HEADER = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.7; rv:16.0) Gecko/20100101 Firefox/16.0";
	
}