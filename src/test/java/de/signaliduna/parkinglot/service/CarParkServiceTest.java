package de.signaliduna.parkinglot.service;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import de.signaliduna.parkinglot.model.exception.ErrorCode;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author U094915
 */
@QuarkusTest
class CarParkServiceTest {

  @Inject
  private CarParkService carParkService;
  private Person owner;

  @BeforeEach
  void setUp() {
    owner =  Person.builder().withName("Meister").build();
  }

  @AfterEach
  void cleanUp() {
    carParkService.removeCarpark("JUnit_CarPark");
  }

  @Test
  void createParkingLot() {
    CarPark carpark = carParkService.createCarpark("JUnit_CP", owner, 100);

    assertThat(carpark.getOwner().getName(), is("Meister"));
  }

  @Test
  void rent_parking_lot() {
    Person owner = Person.builder().withName("Meister").build();
    CarPark carpark = carParkService.createCarpark("JUnit_Rent_Lot", owner, 100);

    Person rentee = Person.builder().withName("Mieter").build();

    Rental rental = carParkService.rentParkingLot(carpark, rentee, 1, 1);
    assertThat(rental.getRentee().getName(), is("Mieter"));
    assertThat(rental.getParkingLot().getNumber(), is(greaterThan(0)));
 }

  @Test
  void getNextFreeParkingLot()  {
    CarPark carpark = carParkService.createCarpark("JUnit_Next", owner, 100);

    ParkingLot nextFreeParkingLot = carParkService.getNextFreeParkingLot(carpark, 1);
    assertThat(nextFreeParkingLot.getNumber(), is(1));
  }

  @Test
  void rentParkingLot() {
    CarPark carpark = carParkService.createCarpark("JUnit_Rent", owner, 100);
    ParkingLot nextFreeParkingLot = carParkService.getNextFreeParkingLot(carpark, 1);
    Person rentee = Person.builder().withName("Rentee").build();
    Rental rental = Rental
            .builder()
            .withParkingLot(nextFreeParkingLot)
            .withRentee(rentee)
            .build();
    carParkService.rentParkingLot(carpark, rental);

    assertThat(carpark.getRentalList(), hasSize(1));

    ParkingLot nextFreeParkingLotAfterInsert = carParkService.getNextFreeParkingLot(carpark, 1);
    assertThat(nextFreeParkingLotAfterInsert.getNumber(), is(2));

    try {
      Rental occupiedRental = Rental
              .builder()
              .withRentee(rentee)
              .withParkingLot(nextFreeParkingLot)
              .build();
      carParkService.rentParkingLot(carpark, occupiedRental);
      fail("BusinessException expected");
    } catch (BusinessException e) {
      // expected
      assertThat(e.getErrorCode(), is(ErrorCode.F1000));
    }
  }

  @Test
  void getAmountOfRentableParkingLots() {
    String name = "JUnit_Amount";
    CarPark carpark = carParkService.createCarpark(name, owner, 100);
    int amountOfRentableParkingLots = carParkService.getAmountOfRentableParkingLots(name);

    assertThat(amountOfRentableParkingLots, is(100));
  }
}