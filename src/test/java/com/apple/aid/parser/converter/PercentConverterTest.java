package com.apple.aid.parser.converter;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.apple.iad.parser.converter.PercentConverter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class PercentConverterTest {

	private PercentConverter converter;

	@Before
	public void setUp() {
		converter = new PercentConverter();
	}

	@Test
	public void convertToStringFromBigDecimal() {
		String v = converter.convertTo(BigDecimal.ZERO);
		Assert.assertEquals("0", v);
	}

	@Test
	public void convertFromStringToBigDecimal() {
		BigDecimal v = converter.convertFrom("14.58%");
		Assert.assertEquals("14.58", v.toString());
	}

}
