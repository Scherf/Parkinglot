package de.signaliduna.parkinglot.validation;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Rental;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * @author U094915
 */
public class ParkingLotValidatorTest {

  private ParkingLotValidator validator;

  @Before
  public void setUp() {
    validator = new ParkingLotValidator();
  }

  @Test
  public void validate() {
    ParkingLot parkingLot = ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(666)
            .build();

    Set<ConstraintViolation<ParkingLot>> constraintViolations = validator.validate(parkingLot);
    assertThat(constraintViolations, hasSize(0));
  }

  @Test
  public void validate_number_size() {
    ParkingLot parkingLot = ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(10000)
            .build();
    Set<ConstraintViolation<ParkingLot>> constraintViolations = validator.validate(parkingLot);
    assertThat(constraintViolations, hasSize(1));
  }

  @Test
  public void jqTest() {
    // TODO
  }
}