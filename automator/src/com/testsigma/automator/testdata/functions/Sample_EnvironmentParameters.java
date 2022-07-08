package com.testsigma.automator.testdata.functions;

import io.github.serpro69.kfaker.Faker;
import io.github.serpro69.kfaker.provider.Name;

public class Sample_EnvironmentParameters {

  Name name = null;

  public Sample_EnvironmentParameters() {
    name = new Faker().getName();
  }

  public String uniqueUsername() {
    return name.name().toLowerCase().replace(" ", ".") + System.currentTimeMillis();
  }

}
