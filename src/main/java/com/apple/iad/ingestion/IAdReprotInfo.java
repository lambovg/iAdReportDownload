package com.apple.iad.ingestion;

/**
 * iAd report content.
 * 
 * @author Georgi Lambov
 * 
 */
public interface IAdReprotInfo<T> {

	/**
	 * Returns iAd report content.
	 */
	// TODO use void method and remove function argument.
	T getReportContent();
}