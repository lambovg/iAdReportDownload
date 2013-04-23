package com.apple.iad.ingestion;


/**
 * 
 * @author Georgi Lambov
 * 
 */
public interface IAdReprotInfo<T> {

	/**
	 * Returns iAd report as stream.
	 */
	// TODO use void method and remove function argument.
	T getReportContent();
}