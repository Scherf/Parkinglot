package de.signaliduna.parkinglot.validation;

import de.signaliduna.parkinglot.model.ParkingLot;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author U094915
 */
@QuarkusTest
class ParkingLotValidatorTest {

  @Inject
  private ParkingLotValidator validator;

  @Test
  void validate() {
    ParkingLot parkingLot = ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(666)
            .build();

    Set<ConstraintViolation<ParkingLot>> constraintViolations = validator.validate(parkingLot);
    assertThat(constraintViolations, hasSize(0));
  }

  @Test
  void validate_number_size() {
    ParkingLot parkingLot = ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(10000)
            .build();
    Set<ConstraintViolation<ParkingLot>> constraintViolations = validator.validate(parkingLot);
    assertThat(constraintViolations, hasSize(1));
  }

  @Test
  void jqTest() {
    // TODO
  }
}