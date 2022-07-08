package com.testsigma.automator.testdata.functions;

import io.github.serpro69.kfaker.Faker;
import io.github.serpro69.kfaker.provider.Internet;
import org.apache.commons.lang3.RandomStringUtils;

public class EmailFunctions {

  Internet domain = null;

  public EmailFunctions() {
    domain = new Faker().getInternet();
  }

  public String userName(int length) {
    return RandomStringUtils.randomAlphabetic(length).toLowerCase();

  }

  public String randomAlphanumaricEmail(int length) {
    String generateString = RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    return domain.safeEmail(generateString);

  }

  public String randomAlphanumaricEmail(int length, String domain) {
    String generateString = RandomStringUtils.randomAlphanumeric(length).toLowerCase();
    return new StringBuffer().append(generateString).append("@").append(domain).toString();

  }

  public String randomEmail(int length, String domain) {
    return new StringBuffer().append(userName(length)).append("@").append(domain).toString();

  }

  public String randomEmail(int length) {
    String generateString = RandomStringUtils.randomAlphabetic(length).toLowerCase();
    return domain.safeEmail(generateString);
  }

}
