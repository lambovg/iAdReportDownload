package com.apple.aid.parser.converter;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.apple.iad.parser.converter.MoneyConverter;

/**
 * 
 * @author Georgi Lambov
 * 
 */
public class MoneyConverterTest {

	private MoneyConverter converter;

	@Before
	public void setUp() {
		converter = new MoneyConverter();
	}

	@Test
	public void convertToStringFromBigDecimal() {
		String v = converter.convertTo(BigDecimal.ZERO);
		Assert.assertEquals("0", v);
	}
	
	@Test
	public void convertFromStringToBigDecimal() {
		BigDecimal v = converter.convertFrom("$12.66");
		Assert.assertEquals("12.66", v.toString());
	}
	
	@Test
	public void convertFromStringToBigDecimalUSNumberFormat(){
		BigDecimal v = converter.convertFrom("$2,582.28");
		Assert.assertEquals("2582.28", v.toString());
	}
	
}
