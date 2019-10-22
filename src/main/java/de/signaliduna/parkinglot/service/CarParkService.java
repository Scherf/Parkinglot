package de.signaliduna.parkinglot.service;

import de.signaliduna.parkinglot.model.CarPark;
import de.signaliduna.parkinglot.model.Floor;
import de.signaliduna.parkinglot.model.ParkingLot;
import de.signaliduna.parkinglot.model.Person;
import de.signaliduna.parkinglot.model.Rental;
import de.signaliduna.parkinglot.model.exception.BusinessException;
import de.signaliduna.parkinglot.model.exception.ErrorCode;
import de.signaliduna.parkinglot.repository.CarParkRepository;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import java.util.Objects;

/**
 * Verwaltung eines {@link de.signaliduna.parkinglot.model.CarPark}.
 *
 * @author U094915
 */
@RequestScoped
public class CarParkService {

  @Inject
  private CarParkRepository carParkRepository;

  public CarParkService() {
    //
  }

  CarPark createCarpark(String name, Person owner, int... amountOfParkingLotsOnFloor) {
    CarPark carPark = new CarPark(name);

    int level = 1;
    for (int amount : amountOfParkingLotsOnFloor) {
      Floor floor = Floor.builder()
              .withCapacity(amount)
              .withLevel(level)
              .build();
      carPark.addFloor(floor);
      level++;
    }

    carPark.setOwner(owner);
    return carParkRepository.persist(carPark);
  }

  void removeCarpark(String name) {
    Objects.requireNonNull(name, "Name has to be set.");
    carParkRepository.remove(name);
  }

  Rental rentParkingLot(CarPark carpark, Person rentee, int level, int parkingLotNumber) {
    ParkingLot parkingLot = ParkingLot
            .builder()
            .withLevel(level)
            .withNumber(parkingLotNumber)
            .build();

    Rental rental = Rental
            .builder()
            .withRentee(rentee)
            .withParkingLot(parkingLot)
            .build();

    carpark.addRental(rental);

    return rental;
  }

  ParkingLot getNextFreeParkingLot (CarPark carPark, int level) {
    Floor floor = carPark.getFloor(level);
    long rentalsOnLevel = carPark
            .getRentalList()
            .stream()
            .filter(rental -> rental.getParkingLot().getLevel() == level)
            .count();
    int floorCapacity = floor.getCapacity();
    int parkingLotNumber;
    if (floorCapacity > rentalsOnLevel) {
      parkingLotNumber = 1;
      while (carPark.calculateRental(parkingLotNumber) != null && parkingLotNumber <= floorCapacity ) {
        parkingLotNumber++;
      }
      if (0 < parkingLotNumber && parkingLotNumber <= floorCapacity) {
        return ParkingLot.builder().withLevel(level).withNumber(parkingLotNumber).build();
      }
    }
    return null;
  }

  void rentParkingLot(CarPark carPark, Rental rental) {
    if (carPark.calculateRental(rental.getParkingLot().getNumber()) != null) {
      throw new BusinessException(ErrorCode.F1000);
    }
    carPark.addRental(rental);
  }

  int getAmountOfRentableParkingLots(String carParkName) {
    CarPark carPark = carParkRepository.find(carParkName);
    Objects.requireNonNull(carPark, "Carpark with name '"+ carParkName+ "' not found in repository");
    return  carPark.calculateAmountOfRentableParkingLots();
  }

  public CarPark findByName(String name) {
    return carParkRepository.find(name);
  }

}
