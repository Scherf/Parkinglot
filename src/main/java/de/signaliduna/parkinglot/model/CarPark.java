package de.signaliduna.parkinglot.model;

import de.signaliduna.parkinglot.quality.cobertura.CoberturaIgnoreMethod;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Repräsentiert das komplette Parkhaus.
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarPark implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @OneToOne(cascade = CascadeType.ALL)
  private Person owner;

  @NotNull
  @Size(min = 1, max = 50)
  private String name;

  public CarPark(String name) {
    this.name = name;
  }

  @Valid
  @OneToMany(cascade = CascadeType.ALL)
  private List<Floor> floorList = new ArrayList<>();

  @Valid
  @OneToMany(cascade = CascadeType.ALL)
  private List<Rental> rentalList = new ArrayList<>();


  @CoberturaIgnoreMethod
  public void addFloor(Floor floor) {
    Objects.requireNonNull(floor, "Floor mustnot be null");
    this.floorList.add(floor);
  }

  public void addRental(Rental rental) {
    this.rentalList.add(rental);
  }

  public int calculateAmountOfRentableParkingLots() {
    int maximumCapacity = this.getFloorList()
                              .stream()
                              .map(Floor::getCapacity)
                              .reduce(0, Integer::sum);
    int rentals = this.getRentalList().size();
    return maximumCapacity - rentals;
  }

  public List<ParkingLot> calculateRentedParkinglots(int level) {
    return rentalList
               .stream()
               .filter(rental -> rental.getParkingLot().getLevel() == level)
               .map(Rental::getParkingLot)
               .collect(Collectors.toList());
  }

  public Rental calculateRental(int parkingLotNumber) {
    return rentalList
               .stream()
               .filter(rental -> rental.getParkingLot().getNumber() == parkingLotNumber)
               .findFirst()
               .orElse(null);
  }

  public Floor getFloor(int level) {
    return floorList
               .stream()
               .filter(floor -> floor.getLevel() == level)
               .findFirst()
               .orElse(null);
  }

  public static CarParkBuilder builder() {
    return new CarParkBuilder();
  }

  public static class CarParkBuilder {

    private CarParkBuilder() {
    }

    private String name;
    private Person owner;

    public CarParkBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public CarParkBuilder withOwner(Person owner) {
      this.owner = owner;
      return this;
    }

    public CarPark build() {
      Objects.requireNonNull(name, "Name must not be null.");
      Objects.requireNonNull(owner, "Cannot create carpark without owner.");
      CarPark carPark = new CarPark();
      carPark.setOwner(this.owner);
      carPark.setName(this.name);
      return carPark;
    }
  }
}