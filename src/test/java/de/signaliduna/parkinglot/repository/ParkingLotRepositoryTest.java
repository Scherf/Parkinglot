package de.signaliduna.parkinglot.repository;

import java.util.List;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Rental;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author u094915
 */
public class ParkingLotRepositoryTest {

  @Before
  public void setUp() {
    repository = new ParkingLotRepository();
  }

  private ParkingLotRepository repository;


  @Test
  public void findAllParkingLots() {
    List<ParkingLot> parkingLots = repository.findAllParkingLots();
    assertThat(parkingLots, hasSize(1));
  }

  @Test
  public void persist() {
    repository.clear();
    ParkingLot parkingLot = repository.persist(ParkingLot
            .builder()
            .withLevel(1)
            .withNumber(111)
            .withRental(new Rental())
            .build());
    assertThat(parkingLot.getId(), is(notNullValue()));
    List<ParkingLot> parkingLots = repository.findAllParkingLots();
    assertThat(parkingLots, hasSize(2));
  }

}