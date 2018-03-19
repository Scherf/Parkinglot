package de.signaliduna.parkinglot.model;

import org.junit.Before;
import org.junit.Test;

/**
 * @author U094915
 */
public class ParkingLotTest extends AbstractEntityTest {

  @Test
  public void toStringCoverage() throws Exception {
    super.checkPropertiesInToStringOutput(ParkingLot.class);
  }
}