package de.signaliduna.parkinglot.model;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.CoreMatchers;

import static org.junit.Assert.assertThat;

/**
 * @author U094915
 */
abstract class AbstractEntityTest {

  private static final Logger LOGGER = LogManager.getLogger(AbstractEntityTest.class);

  /**
   * Check whether all attributes of the given class are contained in the toString method of the entity.
   */
  void checkPropertiesInToStringOutput(Object instance) throws Exception {
    String toStringOutput = (String) MethodUtils.invokeExactMethod(instance, "toString");
    BeanInfo beanInfo = Introspector.getBeanInfo(instance.getClass());
    for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
      if (propertyDescriptor.getReadMethod() == null) {
        LOGGER.warn("Found get method with parameter {}", propertyDescriptor);
      } else {
        String name = StringUtils.remove(propertyDescriptor.getReadMethod().getName(), "get");
        if (!name.equalsIgnoreCase("Class")) {
          assertThat(toStringOutput.toLowerCase(), CoreMatchers.containsString(name.toLowerCase()));
        }
      }
    }

  }

  void checkPropertiesInToStringOutput(Class<? extends Entity> clazz) throws Exception {
    checkPropertiesInToStringOutput(clazz.newInstance());
  }
}
