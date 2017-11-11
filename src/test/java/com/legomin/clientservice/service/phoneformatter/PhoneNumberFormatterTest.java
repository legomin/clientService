package com.legomin.clientservice.service.phoneformatter;

import static org.junit.Assert.assertEquals;

import com.legomin.clientservice.service.phoneformatter.exception.PhoneFormatException;

import org.junit.Test;

public class PhoneNumberFormatterTest {

  private PhoneNumberFormatter formatter = new PhoneNumberFormatter();

  @Test
  public void testCorrectNumber() throws Exception {
    final String input = "+12356789012";
    assertEquals("Unexpected result", input, formatter.apply(input));
  }

  @Test
  public void test10digitsNumber() throws Exception {
    assertEquals("Unexpected result", "+71235678901", formatter.apply("1235678901"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("123 567 89-01"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("+1235  678-90 1"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("+1(235)67-89-01"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("+123f5f6f7f8901"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("+123@567r890s1"));
  }

  @Test
  public void test11digitsNumber() throws Exception {
    assertEquals("Unexpected result", "+71235678901", formatter.apply("71235678901"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("7123 567 89-01"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("7+1235  678-90 1"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("7+1(235)67-89-01"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("+7123f5f6f7f8901"));
    assertEquals("Unexpected result", "+71235678901", formatter.apply("7+123@567r890s1"));
  }

  @Test(expected = PhoneFormatException.class)
  public void testFail() throws Exception {
    final String input = "+1235678901200";
    formatter.apply(input);
  }

}
