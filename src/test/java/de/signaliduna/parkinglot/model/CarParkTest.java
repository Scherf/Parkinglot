package de.signaliduna.parkinglot.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author U094915
 */
class CarParkTest {

  private CarPark carPark;

  @BeforeEach
  void setUp() {
    carPark = new CarPark("JUnit");
  }

  @Test
  void getFreeParkinglots() {
    // TODO
  }

  @Test
  void calculateAmountOfRentableParkingLots() {
    carPark.addFloor(Floor.builder().withLevel(1).withCapacity(100).build());
    carPark.addFloor(Floor.builder().withLevel(2).withCapacity(50).build());

    int amountOfRentableParkingLots = carPark.calculateAmountOfRentableParkingLots();

    assertThat(amountOfRentableParkingLots, is(150));
  }

  @Test
  void calculateAmountOfRentableParkingLots_with_rental() {
    carPark.addFloor(Floor.builder().withLevel(1).withCapacity(100).build());
    carPark.addFloor(Floor.builder().withLevel(2).withCapacity(50).build());

    carPark.addRental(Rental
                          .builder()
                          .withRentee(new Person())
                          .withParkingLot(new ParkingLot())
                          .build());

    int amountOfRentableParkingLots = carPark.calculateAmountOfRentableParkingLots();

    assertThat(amountOfRentableParkingLots, is(149));
  }

}