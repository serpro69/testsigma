package com.testsigma.automator.testdata.functions;

import io.github.serpro69.kfaker.Faker;
import io.github.serpro69.kfaker.provider.Name;

public class DomainFunctions {

  Name name = null;

  public DomainFunctions() {
    name = new Faker().getName();
  }

  public String domainName(String domain) {

    return new StringBuffer().append(name.firstName()).append("@").append(domain).toString();

  }

}
