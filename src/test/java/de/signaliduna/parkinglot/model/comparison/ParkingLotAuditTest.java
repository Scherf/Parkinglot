package de.signaliduna.parkinglot.model.comparison;

import org.apache.commons.lang3.SerializationUtils;
import org.javers.core.diff.Diff;
import org.junit.Before;
import org.junit.Test;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * @author U094915
 */
public class ParkingLotAuditTest {

  private ParkingLotAudit parkingLotAudit;

  @Before
  public void setUp() {
    parkingLotAudit = new ParkingLotAudit();
  }

  @Test
  public void compare() {

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