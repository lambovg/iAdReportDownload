package com.apple.iad.ingestion.step;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

/**
 * Sequence steps for iAd reports download.
 * 
 * @author Georgi Lambov
 * 
 */
interface IngestionStep {

	/**
	 * Executes concreate strategy in order to retrieve specific values required to download iAd report.
	 * 
	 * @return
	 * 
	 * @throws URISyntaxException
	 * @throws UnsupportedEncodingException
	 */
	void execute() throws URISyntaxException, UnsupportedEncodingException, ClientProtocolException, IOException;

}
