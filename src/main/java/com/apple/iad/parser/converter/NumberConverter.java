package com.apple.iad.parser.converter;

import java.math.BigInteger;

import org.dozer.DozerConverter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class NumberConverter extends DozerConverter<BigInteger, String> {

	public NumberConverter() {
		super(BigInteger.class, String.class);
	}

	@Override
	public String convertTo(BigInteger source, String destination) {
		return source.toString();
	}

	@Override
	public BigInteger convertFrom(String source, BigInteger destination) {
		return new BigInteger(source);
	}
}
