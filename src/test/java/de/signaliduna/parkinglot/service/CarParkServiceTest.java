package de.signaliduna.parkinglot.service;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import de.signaliduna.parkinglot.model.exception.ErrorCode;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

/**
 * @author U094915
 */
@RunWith(CdiTestRunner.class)
public class CarParkServiceTest {

  @Inject
  private CarParkService carParkService;
  private Person owner;

  @Before
  public void setUp() {
    owner =  Person.builder().withName("Meister").build();
  }

  @Test
  public void createParkingLot() {
    CarPark carpark = carParkService.createCarpark("JUnit", owner, 100);

    assertThat(carpark.getOwner().getName(), is("Meister"));
  }

  @Test
  public void rent_parking_lot() {
    Person owner = Person.builder().withName("Meister").build();
    CarPark carpark = carParkService.createCarpark("JUnit", owner, 100);

    Person rentee = Person.builder().withName("Mieter").build();

    Rental rental = carParkService.rentParkingLot(carpark, rentee, 1, 1);
    assertThat(rental.getRentee().getName(), is("Mieter"));
    assertThat(rental.getParkingLot().getNumber(), is(greaterThan(0)));
 }

  @Test
  public void getNextFreeParkingLot()  {
    CarPark carpark = carParkService.createCarpark("JUnit", owner, 100);

    ParkingLot nextFreeParkingLot = carParkService.getNextFreeParkingLot(carpark, 1);
    assertThat(nextFreeParkingLot.getNumber(), is(1));
  }

  @Test
  public void rentParkingLot() {
    CarPark carpark = carParkService.createCarpark("JUnit", owner, 100);
    ParkingLot nextFreeParkingLot = carParkService.getNextFreeParkingLot(carpark, 1);
    Person rentee = Person.builder().withName("Rentee").build();
    Rental rental = Rental
            .builder()
            .withParkingLot(nextFreeParkingLot)
            .withRentee(rentee)
            .build();
    carParkService.rentParkingLot(carpark, rental);

    assertThat(carpark.getRentals(), hasSize(1));

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
  public void getAmountOfRentableParkingLots() {
    String name = "JUnit";
    carParkService.createCarpark(name, owner, 100);
    int amountOfRentableParkingLots = carParkService.getAmountOfRentableParkingLots(name);

    assertThat(amountOfRentableParkingLots, is(100));
  }
}