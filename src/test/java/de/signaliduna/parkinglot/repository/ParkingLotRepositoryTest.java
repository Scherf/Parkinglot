package de.signaliduna.parkinglot.repository;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author u094915
 */
@QuarkusTest
class ParkingLotRepositoryTest {

  @Inject
  private ParkingLotRepository repository;

  @Test
  void findAllParkingLots() {
    // prepare
    repository.persist(createParkingLot());

    // act
    List<ParkingLot> parkingLots = repository.findAllParkingLots();

    // assert
    assertThat(parkingLots, hasSize(greaterThan(0)));
  }

  @Test
  void persist() {
    ParkingLot parkingLot = repository.persist(createParkingLot());
    assertThat(parkingLot.getId(), is(notNullValue()));
    List<ParkingLot> parkingLots = repository.findAllParkingLots();
    assertThat(parkingLots, hasSize(greaterThan(0)));
  }

  private ParkingLot createParkingLot() {
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
    return parkingLot;
  }
}