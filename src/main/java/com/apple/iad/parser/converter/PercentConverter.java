package com.apple.iad.parser.converter;

import java.math.BigDecimal;

import org.dozer.DozerConverter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class PercentConverter extends DozerConverter<BigDecimal, String> {

	public PercentConverter() {
		super(BigDecimal.class, String.class);
	}

	@Override
	public String convertTo(BigDecimal source, String destination) {
		return source.toString();
	}

	@Override
	public BigDecimal convertFrom(String source, BigDecimal destination) {
		return new BigDecimal(source.replace("%", ""));
	}
}
