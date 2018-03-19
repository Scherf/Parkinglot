package de.signaliduna.parkinglot.model;

import org.junit.Before;
import org.junit.Test;

/**
 * @author U094915
 */
public class CarParkTest extends AbstractEntityTest {

  private CarPark carPark;

  @Before
  public void setUp() {
    carPark = new CarPark("JUnit");
  }

  @Test
  public void toStringCoverage() throws Exception {
    super.checkPropertiesInToStringOutput(carPark);
  }

  @Test
  public void getFreeParkinglots() {
    // TODO
  }
}