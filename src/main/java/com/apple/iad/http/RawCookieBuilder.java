package com.apple.iad.http;

import lombok.Getter;
import lombok.Setter;

import org.apache.http.client.utils.URIBuilder;

/**
 * Adapter for URI to string conversion with necessary formating required by the HTTP cookie header.
 * <p>
 * cookie_name=cookie_value; cookie_auth=cookie_auth_value
 * </p>
 * 
 * @author Georgi Lambov
 * 
 */
public class RawCookieBuilder {

	@Getter
	@Setter
	private URIBuilder uriBuilder;

	public RawCookieBuilder() {
		uriBuilder = new URIBuilder();
	}

	/**
	 * Adapting setParameter method of {@link URIBuilder}.
	 * 
	 * @param param
	 * @param value
	 * @return
	 */
	public RawCookieBuilder setParameter(final String param, final String value) {
		uriBuilder.setParameter(param, value);
		return this;
	}

	/**
	 * Adapting addParameter method of {@link URIBuilder}.
	 * 
	 * @param param
	 * @param value
	 * @return
	 */
	public RawCookieBuilder addParameter(final String param, final String value) {
		uriBuilder.addParameter(param, value);
		return this;
	}

	@Override
	public String toString() {
		return uriBuilder.toString().replace("&", "; ").replaceFirst("\\?", "");
	}

}
