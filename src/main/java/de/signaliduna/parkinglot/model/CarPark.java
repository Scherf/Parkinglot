package de.signaliduna.parkinglot.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import de.signaliduna.parkinglot.quality.cobertura.CoberturaIgnoreMethod;

/**
 * Repräsentiert das komplette Parkhaus.
 */
public class CarPark extends Entity {

  public CarPark(String name) {
    this.name = name;
  }

  @Valid
  private Map<Integer, Floor> floors= new HashMap<>();

  @Valid
  private List<Rental> rentals= new ArrayList<>();


  private Person owner;
  private String name;

  @CoberturaIgnoreMethod
  public List<Floor> getFloors() {
    return new ArrayList<>(floors.values());
  }

  @CoberturaIgnoreMethod
  public void addFloor(Floor floor, int level) {
    this.floors.put(level, floor);
  }

  @CoberturaIgnoreMethod
  public void addFloor(Floor floor) {
    Objects.requireNonNull(floor, "Floor mustnot be null");
    this.floors.put(floor.getLevel(), floor);
  }

  public List<Rental> getRentals() {
    return rentals;
  }

  public void setRentals(List<Rental> rentals) {
    this.rentals = rentals;
  }

  public void addRental(Rental rental) {
    this.rentals.add(rental);
  }

  @CoberturaIgnoreMethod
  public Person getOwner() {
    return owner;
  }

  @CoberturaIgnoreMethod
  public void setOwner(Person owner) {
    this.owner = owner;
  }

  @CoberturaIgnoreMethod
  public String getName() {
    return name;
  }

  @CoberturaIgnoreMethod
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return super.toString() +
            "CarPark{" +
            "floors=" + floors +
            ", rentals=" + rentals +
            ", owner=" + owner +
            ", name='" + name + '\'' +
            '}';
  }

  public List<ParkingLot> calculateRentedParkinglots(int level) {
    return rentals
            .stream()
            .filter(rental -> rental.getParkingLot().getLevel() == level)
            .map(Rental::getParkingLot)
            .collect(Collectors.toList());
  }

  public Rental calculateRental(int parkingLotNumber) {
    return rentals
            .stream()
            .filter(rental -> rental.getParkingLot().getNumber() == parkingLotNumber)
            .findFirst()
            .orElse(null);
  }

  public Floor getFloor(int level) {
    return floors.get(level);
  }
}
