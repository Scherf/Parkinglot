package de.signaliduna.parkinglot.model.comparison;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import org.apache.commons.lang3.SerializationUtils;
import org.javers.core.diff.Diff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author U094915
 */
class ParkingLotAuditTest {

  private ParkingLotAudit parkingLotAudit;

  @BeforeEach
  void setUp() {
    parkingLotAudit = new ParkingLotAudit();
  }

  @Test
  void compare() {

    ParkingLot parkingLotSource = ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(666)
            .build();

    Person person = Person
            .builder()
            .withName("Max")
            .withSurname("Muster")
            .build();

    Rental
        .builder()
        .withParkingLot(parkingLotSource)
        .withRentee(person)
        .build();


    ParkingLot parkingLotTarget = SerializationUtils.clone(parkingLotSource);
    parkingLotTarget.setLevel(2);
    parkingLotTarget.getRental().getRentee().setName("Anders");

    Diff diff = parkingLotAudit.compare(parkingLotSource, parkingLotTarget);

    assertThat(diff.getChanges(), hasSize(2));
  }

}