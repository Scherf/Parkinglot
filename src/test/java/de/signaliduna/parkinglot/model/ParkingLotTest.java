package de.signaliduna.parkinglot.model;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author U094915
 */
class ParkingLotTest {

  private ParkingLot parkingLot;

  @Test
  void create() {
    ParkingLot parkingLot = ParkingLot
                                .builder()
                                .withLevel(1)
                                .withNumber(666)
                                .build();
    Rental
        .builder()
        .withRentee(Person
                        .builder()
                        .withName("JUnit")
                        .build())
        .withParkingLot(parkingLot)
        .build();

    assertThat(parkingLot.getRental().getRentee(), notNullValue());
  }
}