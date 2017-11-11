package com.legomin.clientservice.service.phoneformatter;

import java.util.function.Function;
import java.util.regex.Pattern;

import com.legomin.clientservice.service.phoneformatter.exception.PhoneFormatException;

import org.springframework.stereotype.Component;

@Component
public class PhoneNumberFormatter implements Function<String, String> {

  private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[+]{1}[0-9]{11}$");
  private static final String DEFAULT_REGION_CODE = "7"; //Let it be Russia

  private static boolean isMatch(final String input) {
    return PHONE_NUMBER_PATTERN.matcher(input).matches();
  }

  private static String tryToFormat(final String input) {
    String result = input.replaceAll("\\D", "");

    switch (result.length()) {
      case 11:
        return "+" + result;
      case 10:
        return "+" + DEFAULT_REGION_CODE + result;
      default:
        throw new PhoneFormatException("Can't format phone number:" + input);
    }
  }

  @Override
  public String apply(String input) {
    if (isMatch(input)) {
      return input;
    }
    return tryToFormat(input);
  }

}
