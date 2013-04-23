package com.apple.aid.parser.converter;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.apple.iad.parser.converter.NumberConverter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class NumberConverterTest {

	private NumberConverter converter;

	@Before
	public void setUp() {
		converter = new NumberConverter();
	}

	@Test
	public void convertToStringFromBigInteger() {
		String v = converter.convertTo(BigInteger.ZERO);
		Assert.assertEquals("0", v);
	}

	@Test
	public void convertFromStringToBigInteger() {
		BigInteger v = converter.convertFrom("12");
		Assert.assertEquals("12", v.toString());
	}

}
