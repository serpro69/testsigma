package com.testsigma.automator.runners;

import com.testsigma.automator.constants.ErrorCodes;
import com.testsigma.automator.constants.AutomatorMessages;
import com.testsigma.automator.entity.TestCaseResult;
import com.testsigma.automator.entity.DefaultDataGeneratorsEntity;
import com.testsigma.automator.exceptions.AutomatorException;
import com.testsigma.automator.exceptions.TestsigmaInvalidClassException;
import com.testsigma.automator.exceptions.TestsigmaInvalidParameterDataException;
import com.testsigma.automator.testdata.functions.*;
import io.github.serpro69.kfaker.Faker;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Log4j2
@Data
public class DefaultDataGeneratorsExecutor extends FunctionExecutor {
  private TestCaseResult testCaseResult;
  private Map<String, String> settings;
  private DefaultDataGeneratorsEntity defaultDataGeneratorsEntity;

  public String generate() throws AutomatorException {
    try {
      Object result = getDefaultTestDataFunctionResult();
      return (result != null) ? result.toString() : null;
    } catch (ClassNotFoundException e) {
      log.error(e.getMessage(), e);
      throw new AutomatorException("Test Data Function class \"" + defaultDataGeneratorsEntity.getClassName() + "\" not found while executing " +
        "test data custom function \"" + defaultDataGeneratorsEntity.getFunctionName() + "\"");
    } catch (NoSuchMethodException | SecurityException e) {
      log.error(e.getMessage(), e);
      throw new AutomatorException("Test data function \"" + defaultDataGeneratorsEntity.getFunctionName() + "\" not found in class \""
        + defaultDataGeneratorsEntity.getClassName() + "\"");
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      log.error(e.getMessage(), e);
      Exception ex = (Exception) e.getCause();
      throw new AutomatorException("Exception occurred while executing Test data function \"" + defaultDataGeneratorsEntity.getFunctionName()
        + "\" in the class \"" + defaultDataGeneratorsEntity.getClassName() + "\"" + ex.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new AutomatorException("Exception occurred while executing Test data function \"" + defaultDataGeneratorsEntity.getFunctionName()
        + "\" in the class \"" + defaultDataGeneratorsEntity.getClassName() + "\"" + e.getMessage());
    }
  }

  private Object getDefaultTestDataFunctionResult() throws IllegalAccessException, IllegalArgumentException,
    InvocationTargetException, ClassNotFoundException, TestsigmaInvalidParameterDataException, NoSuchMethodException,
    SecurityException, TestsigmaInvalidClassException {

    log.info("Executing Default Test Data Function With Details - " + defaultDataGeneratorsEntity);
    Class<?> testDataFunctionClass = loadClass(defaultDataGeneratorsEntity.getClassName(),
      defaultDataGeneratorsEntity.getClassPackage());
    List<Class<?>> argumentClasses = getArgumentClasses(defaultDataGeneratorsEntity.getArgumentTypes());
    List<Object> argumentObjects = getArgumentObjects(argumentClasses, defaultDataGeneratorsEntity.getArguments());
    Method method = testDataFunctionClass.getDeclaredMethod(defaultDataGeneratorsEntity.getFunctionName(),
      argumentClasses.toArray(new Class<?>[0]));
    Object testDataFunctionInstance = getDefaultTestDataFunctionInstance(defaultDataGeneratorsEntity.getClassName());
    return method.invoke(testDataFunctionInstance, argumentObjects.toArray());
  }

  public Object getDefaultTestDataFunctionInstance(String className)
    throws TestsigmaInvalidClassException {
    Faker faker = new Faker();
    switch (className) {
      case "Number":
        return faker.number();
      case "Name":
        return faker.getName();
      case "PhoneNumber":
        return faker.getPhoneNumber();
      case "DateAndTime":
        return faker.date();
      case "Internet":
        return faker.getInternet();
      case "File":
        return faker.getFile();
      case "Friends":
        return faker.getFriends();
      case "IdNumber":
        return faker.getIdNumber();
      case "Address":
        return faker.getAddress();
      case "Company":
        return faker.getCompany();
      case "DateFunctions":
        return new DateFunctions();
      case "DomainFunctions":
        return new DomainFunctions();
      case "EmailFunctions":
        return new EmailFunctions();
      case "NameFunctions":
        return new Sample_EnvironmentParameters();
      case "RandomStringFunctions":
        return new RandomStringFunctions();
      case "CustomFriends":
        return new CustomFriends();
      default:
        throw new TestsigmaInvalidClassException(ErrorCodes.INVALID_CLASS,
          AutomatorMessages.getMessage(AutomatorMessages.EXCEPTION_INVALID_CLASS_NAME, className));
    }
  }
}
